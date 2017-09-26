package com.ml.service.sys;

import com.ml.entity.Page;
import com.ml.entity.Query;
import com.ml.entity.sys.Config;
import com.ml.mapper.sys.ConfigDao;
import com.ml.utils.Pages;
import javafx.scene.media.VideoTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    }

    public void update(Config config) {

    }

    public void deleteBatch(Long[] ids) {

    }
}
