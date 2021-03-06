package com.ml.conf;

import com.ml.exception.RtException;
import com.ml.utils.I18ns;
import org.apache.commons.lang3.StringUtils;

/**
 * SQL过滤
 *
 * @author panda.
 * @since 2017-09-06 15:47.
 */
public class SqlFilter {

    //非法字符
    private static String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String filter(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //判断是否包含非法字符
        for (String keyword : keywords) {
            if (str.indexOf(keyword) != -1) {
                throw new RtException(I18ns.getMessage("exception.contain.illegal.String"));
            }
        }

        return str;
    }
}
