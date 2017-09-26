package com.ml.web.sys;

import com.ml.constants.Constant;
import com.ml.annotation.SysLog;
import com.ml.entity.R;
import com.ml.entity.sys.Role;
import com.ml.service.sys.RoleService;
import com.ml.utils.Shiros;
import com.ml.utils.validator.Validators;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理.
 * @author panda.
 * @since 2017-09-25 20:43.
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (Shiros.getUserId() != Constant.SUPER_ADMIN) {
            params.put("creator", Shiros.getUserId());
        }
        return R.success().putPage(roleService.findPage(params));
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select(){
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if(getUserId() != Constant.SUPER_ADMIN){
            map.put("createUserId", getUserId());
        }
        List<Role> list = roleService.findList(map);

        return R.success().put("list", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId){
        Role role = roleService.getRole(roleId);
        return R.success().put("role", role);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody Role role){
        Validators.validateEntity(role);

        role.setCreator(getUserId());
        roleService.save(role);

        return R.success();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody Role role){
        Validators.validateEntity(role);

        role.setCreator(getUserId());
        roleService.update(role);
        return R.success();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds){
        roleService.deleteBatch(roleIds);
        return R.success();
    }

    private Long getUserId() {
        return Shiros.getUserId();
    }
}
