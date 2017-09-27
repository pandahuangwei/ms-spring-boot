package com.ml.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author panda.
 * @since 2017-07-25 21:25.
 */
public class Dates {
    private static final int MILLIS_OF_SECOND = 1000;
    private static final int SECOND_OF_MINUTE = 60;
    private static final int MINUTE_OF_HOUR = 60;
    private static final int HOUR_OF_DAY = 24;

    private static final long MILLIS_OF_MINUTE = MILLIS_OF_SECOND * SECOND_OF_MINUTE;
    private static final long MILLIS_OF_HOUR = MILLIS_OF_MINUTE * MINUTE_OF_HOUR;
    private static final long MILLIS_OF_DAY = MILLIS_OF_HOUR * HOUR_OF_DAY;

    private final static String DATE_PATTERN = "yyyy-MM-dd";
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String formatDateTime(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 指定时间减天数
     *
     * @param srcDate 时间
     * @param days    天数
     * @return 新的时间
     */
    public static Date subDays(Date srcDate, int days) {
        return days == 0 ? srcDate : addDays(srcDate, -1 * days);
    }

    /**
     * 指定时间加天数
     *
     * @param srcDate 时间
     * @param days    天数
     * @return 新的时间
     */
    public static Date addDays(Date srcDate, int days) {
        return new Date(srcDate.getTime() + MILLIS_OF_DAY * days);
    }

    /**
     * 指定时间加小时
     *
     * @param srcDate 时间
     * @param hours   小时数
     * @return 新的时间
     */
    public static Date addHours(Date srcDate, int hours) {
        return new Date(srcDate.getTime() + hours * MILLIS_OF_HOUR);
    }

    /**
     * 指定时间加分钟数
     *
     * @param srcDate 时间
     * @param minutes 分钟数
     * @return 新的时间
     */
    public static Date addMinutes(Date srcDate, int minutes) {
        return minutes == 0 ? srcDate : new Date(srcDate.getTime() + minutes * MILLIS_OF_MINUTE);
    }

    /**
     * 指定时间减分钟数
     *
     * @param srcDate 时间
     * @param minutes 分钟数
     * @return 新的时间
     */
    public static Date subMinutes(Date srcDate, int minutes) {
        return minutes == 0 ? srcDate : addMinutes(srcDate, -1 * minutes);
    }

    /**
     * 判断日期是否在两个日期之间(beginTm<=date<=entTm)
     *
     * @param date    判断日期
     * @param beginTm 开始日期
     * @param entTm   结束日期
     * @return true/false
     */
    public static boolean isBetweenTwoDate(Date date, Date beginTm, Date entTm) {
        return !date.before(beginTm) && !date.after(entTm);
    }

    /**
     * 时间是否比在当前时间之前
     *
     * @param date 比较的时间
     * @return boolean
     */
    public static boolean isBeforeNow(Date date) {
        return isFirstBeforeSecond(date, new Date());
    }

    /**
     * 比较第一个时间是否在第二个时间之前
     *
     * @param first  第一个时间
     * @param second 第二个时间
     * @return boolean
     */
    public static boolean isFirstBeforeSecond(Date first, Date second) {
        return (first == null || second == null) ? false : first.before(second);
    }

}
