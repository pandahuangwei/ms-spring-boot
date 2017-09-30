package com.ml.service.sys;

import com.ml.entity.Page;
import com.ml.entity.Query;
import com.ml.entity.sys.Log;
import com.ml.mapper.sys.LogDao;
import com.ml.utils.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-25 20:17.
 */
@Service("logService")
public class LogService {
    @Autowired
    private LogDao logDao;

    public Page findPage(Map<String, Object> params) {
        Query query = new Query(params);
        return Pages.findPage(logDao.findList(query),query);
    }

    public void save(Log log){
        logDao.save(log);
    }

}
