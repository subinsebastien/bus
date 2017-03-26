package in.xtel.bus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by napster on 3/16/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    ThreadMode value();
}