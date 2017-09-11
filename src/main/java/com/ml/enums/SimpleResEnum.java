package com.ml.enums;

/**
 * 返回结果的状态.
 *
 * @author panda.
 * @since 2017-09-05 2:01.
 */
public enum SimpleResEnum {
    Success(2001, "成功"), Fail(5001, "失败");

    private int value;
    private String desc;

    SimpleResEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int value() {
        return this.value;
    }

    public String desc() {
        return this.desc;
    }
}
