package com.ml.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author panda.
 * @since 2017-09-25 21:25.
 */
public class DateUtils {
    private final static String DATE_PATTERN = "yyyy-MM-dd";
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String formatDateTime(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

}
