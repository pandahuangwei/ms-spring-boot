package com.ml.entity.sys;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author panda.
 * @since 2017-09-12 16:23.
 */
public class Config {

    private Long id;
    @NotBlank(message = "参数名不能为空")
    private String key;
    @NotBlank(message = "参数值不能为空")
    private String value;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
