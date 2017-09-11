package com.ml.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-11 18:04.
 */
public interface BaseDao<T> {
    void save(T t);

    void save(Map<String, Object> map);

    void saveBatch(List<T> list);

    int update(T t);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    T get(Object obj);

    List<T> findList(Map<String, Object> map);

    List<T> findList(Object obj);

}