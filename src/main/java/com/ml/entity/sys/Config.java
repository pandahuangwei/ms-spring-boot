package com.ml.entity.sys;

import com.ml.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author panda.
 * @since 2017-09-12 16:23.
 */
public class Config extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long configId;
    @NotBlank(message = "参数名不能为空")
    private String configKey;
    @NotBlank(message = "参数值不能为空")
    private String configValue;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

}
