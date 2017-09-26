package com.ml.annotation;

import java.lang.annotation.*;

/**
 * @author panda.
 * @since 2017-09-25 10:36.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}