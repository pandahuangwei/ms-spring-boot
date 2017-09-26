package com.ml.web.sys;

import com.ml.constants.Constant;
import com.ml.annotation.SysLog;
import com.ml.entity.R;
import com.ml.entity.sys.Menu;
import com.ml.enums.SysEnums.MenuType;
import com.ml.exception.RtException;
import com.ml.service.sys.MenuService;
import com.ml.utils.Shiros;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author panda.
 * @since 2017-09-25 20:19.
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public R list(@RequestParam Map<String, Object> params){

        return R.success().put("page", "");
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public R select(){
        //查询列表数据
        List<Menu> menuList = menuService.findNotButtonList();
        return R.success().put("menuList", menuList);
    }

    /**
     * 角色授权菜单
     */
    @RequestMapping("/perms")
    @RequiresPermissions("sys:menu:perms")
    public R perms(){
        //查询列表数据
        List<Menu> menuList = null;

        //只有超级管理员，才能查看所有管理员列表
        if(getUserId() == Constant.SUPER_ADMIN){
            menuList = menuService.findList(new HashMap<String, Object>());
        }else{
            menuList = menuService.findUserList(getUserId());
        }

        return R.success().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId){
        Menu menu = menuService.get(menuId);
        return R.success().put("menu", menu);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody Menu menu){
        //数据校验
        verifyForm(menu);
        menuService.save(menu);
        return R.success();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody Menu menu){
        //数据校验
        verifyForm(menu);
        menuService.update(menu);
        return R.success();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public R delete(@RequestBody Long[] menuIds){
        for(Long menuId : menuIds){
            if(menuId.longValue() <= 30){
                return R.fail("系统菜单，不能删除");
            }
        }
        menuService.deleteBatch(menuIds);

        return R.success();
    }

    /**
     * 用户菜单列表
     */
    @RequestMapping("/user")
    public R user(){
        List<Menu> menuList = menuService.findUserMenuList(getUserId());
        Set<String> permissions = menuService.findUserPermissions(getUserId());
        return R.success().put("menuList", menuList).put("permissions", permissions);
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(Menu menu){
        if(StringUtils.isBlank(menu.getName())){
            throw new RtException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new RtException("上级菜单不能为空");
        }

        //菜单
        if(MenuType.MENU.eq(menu.getType())){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new RtException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = MenuType.CATALOG.getKey();
        if(menu.getParentId() != 0){
            Menu parentMenu = menuService.get(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == MenuType.CATALOG.getKey() ||
                menu.getType() == MenuType.MENU.getKey()){
            if(parentType != MenuType.CATALOG.getKey()){
                throw new RtException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == MenuType.BUTTON.getKey()){
            if(parentType != MenuType.MENU.getKey()){
                throw new RtException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }

    private Long getUserId() {
        return Shiros.getUserId();
    }
}
