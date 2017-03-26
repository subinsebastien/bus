package in.xtel.bus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by napster on 3/16/2017.
 * APIs should be easy to use and hard to misuse. It should be easy to do simple things;
 * possible to do complex things; and impossible, or at least difficult, to do wrong things.
 */
public class Bus {

    private static final String TAG = Bus.class.getSimpleName();
    private static Bus instance;

    private ArrayList<Object> subscribers;

    private Bus() {
        subscribers = new ArrayList<>();
    }

    public static Bus getInstance() {
        if (instance == null) instance = new Bus();
        return instance;
    }

    public void subscribe(Object object) {
        if (!subscribers.contains(object)) subscribers.add(object);
    }

    public void unsubscribe(Object object) {
        subscribers.remove(object);
    }

    public void post(final Object event) {
        if (event == null) {
            Log.e(TAG, "Events cannot be null. Ignoring");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    postEvent(event);
                } catch (Exception e) {
                    Log.e(TAG, "Exception in posting event");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void postEvent(Object event) throws Exception {
        ArrayList<Subscription> subscriptions = new ArrayList<>();
        for (Object subscriber : subscribers) {
            Method toBeInvoked = null;
            Method[] methods = subscriber.getClass().getDeclaredMethods();
            for (Method m : methods) {
                Class<?>[] parameterTypes = m.getParameterTypes();
                if (parameterTypes.length == 1) {
                    if (parameterTypes[0].equals(event.getClass())) {
                        toBeInvoked = m;
                    }
                }
            }
            if (toBeInvoked != null) {
                subscriptions.add(new Subscription(subscriber, toBeInvoked, null));
            } else {
                Log.e(TAG, "No subscribers registered for this event");
            }
        }
        for (Subscription subscription : subscriptions) {
            postEventOnMethod(subscription.getMethod(), subscription.getSubscriber(), event);
        }
    }

    private void postEventOnMethod(final Method method, final Object subscriber, final Object event) {
        Subscribe a = method.getAnnotation(Subscribe.class);
        if (a != null && a.value() == ThreadMode.UI_THREAD) {
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    invokeMethod(method, subscriber, event);
                }
            });
        } else {
            invokeMethod(method, subscriber, event);
        }
    }

    private synchronized void invokeMethod(Method method, Object subscriber, Object event) {
        if (subscribers.contains(subscriber)) {
            try {
                method.invoke(subscriber, event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}