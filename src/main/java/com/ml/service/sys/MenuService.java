package com.ml.service.sys;

import com.ml.entity.sys.Menu;
import com.ml.enums.SysEnums.MenuType;
import com.ml.mapper.sys.MenuDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 功能菜单
 *
 * @author panda.
 * @since 2017-09-05 3:28.
 */
@Service("menuService")
public class MenuService {
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserService userService;

    public List<Menu> findNotButtonList() {
        List<Menu> menuList = menuDao.findNotButtonList();

        //添加顶级菜单
        Menu root = new Menu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return menuList;
    }

    public Menu get(Long menuId) {
        return menuDao.get(menuId);
    }

    public void save(Menu menu) {
        menuDao.save(menu);
    }

    public void update(Menu menu) {
        menuDao.update(menu);
    }

    public void deleteBatch(Long[] menuIds) {
        menuDao.deleteBatch(menuIds);
    }

    public List<Menu> findList(Map<String, Object> map) {
        return menuDao.findList(map);
    }

    public List<Menu> findUserList(Long userId) {
        return menuDao.findUserList(userId);
    }

    public Set<String> findUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == 1){
            List<Menu> menuList = findList(new HashMap<>());
            permsList = new ArrayList<>(menuList.size());
            for(Menu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = userService.findAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<String>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }


    public List<Menu> findUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == 1){
            return findAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = userService.findAllMenuId(userId);
        return findAllMenuList(menuIdList);
    }


    /**
     * 获取所有菜单列表
     */
    private List<Menu> findAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<Menu> menuList = findListParentId(0L, menuIdList);
        //递归获取子菜单
        findMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    public List<Menu> findListParentId(Long parentId, List<Long> menuIdList) {
        List<Menu> menuList = menuDao.findListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<Menu> userMenuList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    /**
     * 递归
     */
    private List<Menu> findMenuTreeList(List<Menu> menuList, List<Long> menuIdList) {
        List<Menu> subMenuList = new ArrayList<>();

        for (Menu entity : menuList) {
            if (MenuType.CATALOG.eq(entity.getType())) {//目录
                entity.setList(findMenuTreeList(findListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

}
