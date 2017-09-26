package com.ml.mapper.sys;

import com.ml.entity.sys.Schedule;
import com.ml.entity.sys.ScheduleLog;
import com.ml.mapper.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-12 14:28.
 */
@Mapper
public interface ScheduleDao extends BaseDao<Schedule> {
    int updateBatch(Map<String, Object> map);

    List<ScheduleLog> findLogList(Map<String, Object> map);

    ScheduleLog getScheduleLog(Long taskId);

    void saveLog(ScheduleLog log);
}
