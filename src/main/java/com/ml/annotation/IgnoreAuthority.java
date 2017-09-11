package com.ml.annotation;

import java.lang.annotation.*;

/**
 * 无需认证注解声明
 *
 * @author panda.
 * @since 2017-06-06 18:02.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuthority {
}
