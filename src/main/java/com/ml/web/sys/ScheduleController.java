package com.ml.web.sys;

import com.ml.annotation.SysLog;
import com.ml.entity.R;
import com.ml.entity.sys.Schedule;
import com.ml.service.sys.ScheduleService;
import com.ml.utils.validator.Validators;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-26 0:20.
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public R list(@RequestParam Map<String, Object> params){
        return R.success().putPage(scheduleService.findPage(params));
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{taskId}")
    @RequiresPermissions("sys:schedule:info")
    public R info(@PathVariable("taskId") Long taskId){
        Schedule schedule = scheduleService.get(taskId);
        return R.success().put("schedule", schedule);
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public R save(@RequestBody Schedule schedule){
        Validators.validateEntity(schedule);

        scheduleService.save(schedule);

        return R.success();
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public R update(@RequestBody Schedule schedule){
        Validators.validateEntity(schedule);

        scheduleService.update(schedule);

        return R.success();
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public R delete(@RequestBody Long[] taskIds){
        scheduleService.deleteBatch(taskIds);

        return R.success();
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public R run(@RequestBody Long[] taskIds){
        scheduleService.run(taskIds);

        return R.success();
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public R pause(@RequestBody Long[] taskIds){
        scheduleService.pause(taskIds);

        return R.success();
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public R resume(@RequestBody Long[] taskIds){
        scheduleService.resume(taskIds);
        return R.success();
    }


    @RequestMapping("/log/list")
    @RequiresPermissions("sys:schedule:log")
    public R listLog(@RequestParam Map<String, Object> params){
        return R.success().putPage(scheduleService.findLogPage(params));
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/log/info/{taskId}")
    public R infoLog(@PathVariable("taskId") Long taskId){
        return R.success().put("log", scheduleService.getScheduleLog(taskId));
    }

}
