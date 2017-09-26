package com.ml.web.sys;

import com.ml.annotation.SysLog;
import com.ml.entity.R;
import com.ml.entity.sys.Config;
import com.ml.service.sys.ConfigService;
import com.ml.utils.validator.Validators;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-25 21:37.
 */
@RestController
@RequestMapping("/sys/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.success().putPage(configService.findPage(params));
    }

    /**
     * 配置信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public R info(@PathVariable("id") Long id) {
        Config config = configService.get(id);
        return R.success().put("config", config);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public R save(@RequestBody Config config) {
        Validators.validateEntity(config);
        configService.save(config);
        return R.success();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public R update(@RequestBody Config config) {
        Validators.validateEntity(config);
        configService.update(config);
        return R.success();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public R delete(@RequestBody Long[] ids) {
        configService.deleteBatch(ids);
        return R.success();
    }
}
