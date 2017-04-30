package in.xtel.bus;

import java.lang.reflect.Method;

/**
 * Created by napster on 3/16/2017.
 */
public class Subscription {
    Object subscriber;
    Method method;

    public Subscription(Object subscriber, Method method) {
        this.subscriber = subscriber;
        this.method = method;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    public Method getMethod() {
        return method;
    }
}