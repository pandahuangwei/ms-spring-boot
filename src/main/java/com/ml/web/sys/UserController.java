package com.ml.web.sys;

import com.ml.Constants.Constant;
import com.ml.entity.Query;
import com.ml.entity.R;
import com.ml.service.sys.UserService;
import com.ml.utils.Pages;
import com.ml.utils.Shiros;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.ml.utils.Shiros.getUserId;

/**
 * @author panda.
 * @since 2017-09-18 15:31.
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
    @RequestMapping("/password")
    public R password(String password, String newPassword) {
        return R.success();
    }

}
