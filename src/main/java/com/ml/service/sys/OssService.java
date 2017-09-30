package com.ml.service.sys;

import com.ml.entity.Page;
import com.ml.entity.Query;
import com.ml.entity.sys.Oss;
import com.ml.mapper.sys.OssDao;
import com.ml.utils.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Panda.HuangWei313.
 * @since 2017-09-30 10:33.
 */
@Service("ossService")
public class OssService  {
    @Autowired
    private OssDao ossDao;

    public Page findPage(Map<String, Object> params) {
        Query query = new Query(params);
        return Pages.findPage(ossDao.findList(query),query);
    }

    public void save(Oss oss) {
        ossDao.save(oss);
    }

    public void deleteBatch(Long[] ids){
        ossDao.deleteBatch(ids);
    }
}
