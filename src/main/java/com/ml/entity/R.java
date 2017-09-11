package com.ml.entity;

import com.ml.enums.SimpleResEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回给前端的结果集.
 *
 * @author panda.
 * @since 2017-08-04 23:48.
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private static String CODE = "code";
    private static String MESSAGE = "msg";

    public R() {
        put(CODE, 0);
    }

    /**
     * 返回指定的状态以及提示信息
     *
     * @param msg 提示信息
     * @return e
     */
    public static R fail(String msg) {
        return r(SimpleResEnum.Fail.value(), msg);
    }

    /**
     * 返回指定的状态以及提示信息
     *
     * @param code 代码
     * @param msg  提示信息
     * @return e
     */
    public static R fail(int code, String msg) {
        return r(code, msg);
    }

    /**
     * 返回成功
     *
     * @return 带成功状态的返回
     */
    public static R success() {
        return r(SimpleResEnum.Success.value(), null);
    }

    /**
     * 返回指定的状态以及提示信息
     *
     * @param msg 提示信息
     * @return e
     */
    public static R success(String msg) {
        return r(SimpleResEnum.Success.value(), msg);
    }

    /**
     * 返回提示信息以及结果集
     *
     * @param msg 提示信息
     * @param map 结果集
     * @return e
     */
    public static R success(String msg, Map<String, Object> map) {
        R r = r(SimpleResEnum.Success.value(), msg);
        r.putAll(map);
        return r;
    }

    private static R r(int code, String msg) {
        R r = new R();
        r.put(CODE, code);
        r.put(MESSAGE, msg);
        return r;
    }

}
