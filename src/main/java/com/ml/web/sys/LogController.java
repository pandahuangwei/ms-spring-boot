package com.ml.web.sys;

import com.ml.entity.R;
import com.ml.service.sys.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author panda.
 * @since 2017-08-25 20:17.
 */
@Controller
@RequestMapping("/sys/log")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.success().putPage(logService.findPage(params));
    }

}