package com.ml.service.sys;

import com.ml.constants.Constant;
import com.ml.entity.Page;
import com.ml.entity.Query;
import com.ml.entity.sys.Role;
import com.ml.exception.RtException;
import com.ml.mapper.sys.RoleDao;
import com.ml.utils.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ml.utils.Shiros.getUserId;

/**
 * @author panda.
 * @since 2017-09-06 15:18.
 */
@Service("roleService")
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Page findPage(Map<String, Object> params) {
        if (Constant.SUPER_ADMIN != getUserId()) {
            params.put("creator", getUserId());
        }
        //查询列表数据
        Query query = new Query(params);
        return Pages.findPage(roleDao.findList(query), query);
    }

    public List<Role> findList(Map<String, Object> map) {
        return roleDao.findList(map);
    }

    public Role getRole(Long roleId) {
        Role role = roleDao.get(roleId);
        role.setMenuIdList(roleDao.findMenuIdList(roleId));
        return role;
    }

    public void save(Role role) {
        roleDao.save(role);
        checkPrems(role);
        saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    public void update(Role role) {
        roleDao.update(role);
        checkPrems(role);
        saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    public void deleteBatch(Long[] roleIds) {
        roleDao.deleteBatch(roleIds);
    }

    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        roleDao.deleteRoleMenu(roleId);

        if (menuIdList.size() == 0) {
            return;
        }

        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("menuIdList", menuIdList);
        roleDao.saveRoleMenu(map);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(Role role) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (role.getCreator() == Constant.SUPER_ADMIN) {
            return;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = roleDao.findMenuIdList(role.getCreator());

        //判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())) {
            throw new RtException("新增角色的权限，已超出你的权限范围");
        }
    }


}
