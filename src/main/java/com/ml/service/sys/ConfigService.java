package com.ml.service.sys;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.ml.entity.Page;
import com.ml.entity.Query;
import com.ml.entity.sys.Config;
import com.ml.exception.RtException;
import com.ml.mapper.sys.ConfigDao;
import com.ml.utils.Pages;
import javafx.scene.media.VideoTrack;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-06 15:21.
 */
@Service("configService")
public class ConfigService {
    @Autowired
    private ConfigDao configDao;

    public Page findPage(Map<String, Object> params) {
        Query query = new Query(params);
        return Pages.findPage(configDao.findList(query),query);
    }

    public Config get(Long id) {
        return configDao.get(id);
    }

    public void save(Config config) {
        configDao.save(config);
    }

    public void update(Config config) {
        configDao.update(config);
    }

    public void deleteBatch(Long[] ids) {
        configDao.deleteBatch(ids);
    }

    public void updateValueByKey(String key, String value) {
        configDao.updateValueByKey(key, value);
    }

    public String getValue(String key, String defaultValue) {
        String value = configDao.getByKey(key);
        if(StringUtils.isBlank(value)){
            return defaultValue;
        }
        return value;
    }

    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key, null);
        if(StringUtils.isNotBlank(value)){
            return JSON.parseObject(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RtException("获取参数失败");
        }
    }
}
