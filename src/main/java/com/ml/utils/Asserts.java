package com.ml.utils;

import com.ml.exception.RtException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author panda.
 * @since 2017-09-25 10:39.
 */
public abstract class Asserts {
    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RtException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RtException(message);
        }
    }
}