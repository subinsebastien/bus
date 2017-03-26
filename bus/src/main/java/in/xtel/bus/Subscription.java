package in.xtel.bus;

import java.lang.reflect.Method;

/**
 * Created by napster on 3/16/2017.
 */
public class Subscription {
    Object subscriber;
    Method method;
    ThreadMode threadMode;

    public Subscription(Object subscriber, Method method, ThreadMode threadMode) {
        this.subscriber = subscriber;
        this.method = method;
        this.threadMode = threadMode;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }
}