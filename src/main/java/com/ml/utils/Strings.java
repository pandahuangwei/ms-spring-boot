package com.ml.utils;

/**
 * @author panda.
 * @since 2017-07-27 19:29.
 */
public class Strings {
    private static final String SPLIT_LINE = "_";

    /**
     * 将传入的多个字符串组成联合建
     *
     * @param keys 字符串数组
     * @return str
     */
    public static String genKey(String... keys) {
        return genKeyUseChar(SPLIT_LINE, keys);
    }


    /**
     * 构建字符串.
     * <p>
     * Examples:
     * <p>
     * <pre>
     * splitChar="_",key="A","B","C" returns "A_B_C"
     * </pre>
     *
     * @param splitChar 分隔符
     * @param keys      字符串
     * @return
     */
    public static String genKeyUseChar(String splitChar, String... keys) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = keys.length; i < len; i++) {
            sb.append(keys[i]);
            if (i != len - 1) {
                sb.append(splitChar);
            }
        }
        return sb.toString();
    }

    /**
     * 花费的时间字符串如：spent 4h,30m,33s
     *
     * @param startTm 开始时间，以纳秒为单位
     * @return 花费的时间字符串如：spent 4h,30m,33s
     */
    public static String getSpentTm(long startTm) {
        long spentTm = System.nanoTime() - startTm;
        return changeNanoTm2String(spentTm);
    }

    /**
     * 把纳秒时间转换为英文字符串
     *
     * @param nanoTm 纳秒
     * @return 字符串
     */
    public static String changeNanoTm2String(long nanoTm) {
        if (nanoTm == 0) {
            return " spent 0 millSec.";
        }
        long temp = nanoTm;
        long nanoSec = temp % 1000;
        temp = temp / 1000;
        long microSec = temp % 1000;
        temp = temp / 1000;
        long millSec = temp % 1000;
        temp = temp / 1000;
        long sec = temp % 60;
        temp = temp / 60;
        long min = temp % 60;
        temp = temp / 60;
        long hrs = temp % 12;
        temp = temp / 12;
        long days = temp % 365;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" spent ");
        if (days != 0) {
            stringBuilder.append(days + " days ");
        }
        if (hrs != 0) {
            stringBuilder.append(hrs + " hrs ");
        }
        if (min != 0) {
            stringBuilder.append(min + " min ");
        }
        if (sec != 0) {
            stringBuilder.append(sec + " sec ");
        }
        if (millSec != 0) {
            stringBuilder.append(millSec + " millSec ");
        }
        if (microSec != 0) {
            stringBuilder.append(microSec + " microSec ");
        }
        if (nanoSec != 0) {
            stringBuilder.append(nanoSec + " nanoSec");
        }
        stringBuilder.append(".");
        return stringBuilder.toString();
    }
}
