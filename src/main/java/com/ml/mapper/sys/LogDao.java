package com.ml.mapper.sys;

import com.ml.entity.sys.Log;
import com.ml.entity.sys.Menu;
import com.ml.mapper.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author panda.
 * @since 2017-09-12 14:21.
 */
@Mapper
public interface LogDao  extends BaseDao<Log> {
}
