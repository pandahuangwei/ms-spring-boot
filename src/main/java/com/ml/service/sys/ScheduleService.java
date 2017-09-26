package com.ml.service.sys;

import com.ml.entity.Page;
import com.ml.entity.Query;
import com.ml.entity.sys.Schedule;
import com.ml.entity.sys.ScheduleLog;
import com.ml.enums.SysEnums.ScheduleStatus;
import com.ml.mapper.sys.ScheduleDao;
import com.ml.utils.Pages;
import com.ml.utils.Schedules;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-06 15:20.
 */
@Service("scheduleService")
public class ScheduleService {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleDao scheduleDao;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<Schedule> scheduleJobList = scheduleDao.findList(new HashMap<>());
        for (Schedule schedule : scheduleJobList) {
            CronTrigger cronTrigger = Schedules.getCronTrigger(scheduler, schedule.getTaskId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                Schedules.createScheduleJob(scheduler, schedule);
            } else {
                Schedules.updateScheduleJob(scheduler, schedule);
            }
        }
    }

    public Page findPage(Map<String, Object> params) {
        Query query = new Query(params);
        return Pages.findPage(scheduleDao.findList(query), query);
    }

    public Schedule get(Long taskId) {
        return scheduleDao.get(taskId);
    }

    @Transactional
    public void save(Schedule schedule) {
        schedule.setCreateDt(new Date());
        schedule.setStatus(ScheduleStatus.NORMAL.getKey());
        scheduleDao.save(schedule);
        Schedules.createScheduleJob(scheduler, schedule);
    }

    @Transactional
    public void update(Schedule schedule) {
        Schedules.updateScheduleJob(scheduler, schedule);
        scheduleDao.update(schedule);
    }

    @Transactional
    public void deleteBatch(Long[] taskIds) {
        for (Long taskId : taskIds) {
            Schedules.deleteScheduleJob(scheduler, taskId);
        }
        //删除数据
        scheduleDao.deleteBatch(taskIds);
    }

    @Transactional
    public void run(Long[] taskIds) {
        for (Long taskId : taskIds) {
            Schedules.run(scheduler, get(taskId));
        }
    }

    @Transactional
    public void pause(Long[] taskIds) {
        for (Long taskId : taskIds) {
            Schedules.pauseJob(scheduler, taskId);
        }

        updateBatch(taskIds, ScheduleStatus.PAUSE.getKey());
    }

    @Transactional
    public void resume(Long[] taskIds) {
        for (Long taskId : taskIds) {
            Schedules.resumeJob(scheduler, taskId);
        }
        updateBatch(taskIds, ScheduleStatus.NORMAL.getKey());
    }

    public int updateBatch(Long[] taskIds, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", taskIds);
        map.put("status", status);
        return scheduleDao.updateBatch(map);
    }

    public Page findLogPage(Map<String, Object> params) {
        Query query = new Query(params);
        return Pages.findPage(scheduleDao.findList(query), query);
    }

    public ScheduleLog getScheduleLog(Long taskId){
        return scheduleDao.getScheduleLog(taskId);
    }

    public void saveLog(ScheduleLog log) {
        scheduleDao.saveLog(log);
    }
}
