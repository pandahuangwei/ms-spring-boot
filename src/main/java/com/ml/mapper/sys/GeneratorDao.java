package com.ml.mapper.sys;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-12 14:20.
 */
@Mapper
public interface GeneratorDao {

    List<Map<String, Object>> findList(Map<String, Object> map);

    Map<String, String> findTable(String tableName);

    List<Map<String, String>> findColumns(String tableName);
}
