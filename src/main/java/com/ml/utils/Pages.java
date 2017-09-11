package com.ml.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ml.entity.BaseEntity;

import java.util.List;

/**
 * @author panda.
 * @since 2017-06-07 10:11.
 */
public class Pages {

    public static <T extends BaseEntity> PageInfo<T> findByPage(List<T> mapperSearch, T searchEntity) {
        if (searchEntity != null && searchEntity.getPage() != null && searchEntity.getRows() != null) {
            PageHelper.startPage(searchEntity.getPage(), searchEntity.getRows());
        }
        return new PageInfo<T>(mapperSearch);
    }
}
