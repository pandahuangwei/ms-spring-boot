package com.ml.web.sys;

import com.ml.annotation.SysLog;
import com.ml.entity.R;
import com.ml.entity.sys.User;
import com.ml.service.sys.UserService;
import com.ml.utils.Asserts;
import com.ml.utils.Shiros;
import com.ml.utils.validator.InsertGroup;
import com.ml.utils.validator.UpdateGroup;
import com.ml.utils.validator.Validators;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-18 18:31.
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.success().putPage(userService.findPage(params));
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public R info() {
        return R.success().put("user", Shiros.getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public R password(String password, String newPassword) {
        Asserts.isBlank(newPassword, "新密码不为能空");
        //更新密码
        int count = userService.updatePassword(Shiros.getUser(), password, newPassword);
        if (count == 0) {
            return R.fail("原密码不正确");
        }
        Shiros.logout();
        return R.success();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);
        return R.success().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody User user) {
        Validators.validateEntity(user, InsertGroup.class);
        user.setCreator(Shiros.getUserId());
        user.setCreateDt(new Date());
        userService.saveUser(user);
        return R.success();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody User user) {
        Validators.validateEntity(user, UpdateGroup.class);
        userService.updateUser(user);
        return R.success();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.fail("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, Shiros.getUserId())) {
            return R.fail("当前用户不能删除");
        }
        userService.deleteUser(userIds);
        return R.success();
    }
}