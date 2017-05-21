package in.xtel.bus;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by napster on 3/16/2017.
 */
class Subscription {
    private Object subscriber;
    private ArrayList<Method> methods;

    Subscription(Object subscriber) {
        this.subscriber = subscriber;
        this.methods = new ArrayList<>();
    }

    Object getSubscriber() {
        return subscriber;
    }

    void addMethod(Method m) {
        this.methods.add(m);
    }

    ArrayList<Method> getMethods() {
        return methods;
    }
}