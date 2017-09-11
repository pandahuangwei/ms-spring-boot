package com.ml.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author panda.
 * @since 2017-06-06 15:11.
 */
@Component
public class I18ns {

    private static MessageSource messageSource;

    @Resource
    public void setMessageSource(MessageSource messageSource) {
        I18ns.messageSource = messageSource;
    }


    /**
     * @param key ：对应messages配置的key.
     * @return value
     */
    public static String getMessage(String key) {
        return getMessage(key, new Object[]{});
    }

    public static String getMessage(String key, String lang) {
        Locale locale = matchLang(lang);
        return getMessage(key, null, "", locale);
    }

    public static String getMessage(String key, Object[] args, String lang) {
        Locale locale = matchLang(lang);
        return getMessage(key, args, "", locale);
    }

    /**
     * @param key            对应messages配置的key.
     * @param args           数组参数.
     * @param defaultMessage 没有设置key的时候的默认值.
     * @return value
     */

   /* public static String getMessage(String key, Object[] args, String defaultMessage) {
        //这里使用比较方便的方法，不依赖request.
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(key, args, defaultMessage, locale);
    }*/

    /**
     * 指定语言.
     *
     * @param key            属性文件中对应的key
     * @param args           参数数组
     * @param defaultMessage 默认填充
     * @param locale         国际化指定
     * @return value
     */
    public static String getMessage(String key, Object[] args, String defaultMessage, Locale locale) {
        return getMessageSource().getMessage(key, args, defaultMessage, locale);

    }


    public static String getMessage(String key, String defaultMessage, Locale locale) {
        return getMessage(key, null, defaultMessage, locale);
    }


    public static String getMessage(String key, Locale locale) {
        return getMessage(key, null, "", locale);
    }


    /**
     * @param key  对应messages配置的key.
     * @param args 数组参数.
     * @return value
     */

    public static String getMessage(String key, Object[] args) {
        return getMessage(key, args, "");
    }


    public static String getMessage(String key, Object[] args, Locale locale) {
        return getMessage(key, args, "", locale);
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }

    private static Locale matchLang(String lang) {
        return StringUtils.isBlank(lang) ? LocaleContextHolder.getLocale() :
                lang.equals("us") ? Locale.US : lang.equals("cn") ? Locale.CHINA : lang.equals("tw") ? Locale.TRADITIONAL_CHINESE
                        : lang.equals("jp") ? Locale.JAPAN : LocaleContextHolder.getLocale();
    }
}
