package com.ml.utils;

import com.alibaba.fastjson.JSON;
import com.ml.entity.sys.Schedule;
import com.ml.enums.SysEnums.ScheduleStatus;
import com.ml.exception.RtException;
import com.ml.schedule.ScheduleTask;
import org.quartz.*;

/**
 * 定时任务工具类
 *
 * @author panda.
 * @since 2017-09-26 0:33.
 */
public class Schedules {
    private final static String JOB_NAME = "TASK_";

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new RtException("获取定时任务CronTrigger出现异常", e);
        }
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, Schedule schedule) {
        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleTask.class).withIdentity(getJobKey(schedule.getTaskId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedule.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(schedule.getTaskId())).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(Schedule.TASK_PARAM_KEY, JSON.toJSONString(schedule));

            scheduler.scheduleJob(jobDetail, trigger);

            //暂停任务
            if (ScheduleStatus.PAUSE.eq(schedule.getStatus())) {
                pauseJob(scheduler, schedule.getTaskId());
            }
        } catch (SchedulerException e) {
            throw new RtException("创建定时任务失败", e);
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, Schedule schedule) {
        try {
            TriggerKey triggerKey = getTriggerKey(schedule.getTaskId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedule.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, schedule.getTaskId());

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //参数
            trigger.getJobDataMap().put(Schedule.TASK_PARAM_KEY, JSON.toJSONString(schedule));

            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停任务
            if (ScheduleStatus.PAUSE.eq(schedule.getStatus())) {
                pauseJob(scheduler, schedule.getTaskId());
            }

        } catch (SchedulerException e) {
            throw new RtException("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, Schedule schedule) {
        try {
            //参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(Schedule.TASK_PARAM_KEY, JSON.toJSONString(schedule));

            scheduler.triggerJob(getJobKey(schedule.getTaskId()), dataMap);
        } catch (SchedulerException e) {
            throw new RtException("立即执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RtException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RtException("暂停定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RtException("删除定时任务失败", e);
        }
    }
}
