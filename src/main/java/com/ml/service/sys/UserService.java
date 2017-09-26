package com.ml.service.sys;

import com.ml.constants.Constant;
import com.ml.entity.Page;
import com.ml.entity.Query;
import com.ml.entity.sys.User;
import com.ml.exception.RtException;
import com.ml.mapper.sys.UserDao;
import com.ml.utils.Pages;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ml.utils.Shiros.getUserId;

/**
 * @author panda.
 * @since 2017-09-05 3:27.
 */
@Service("userService")
public class UserService {
    @Autowired
    private UserDao userDao;

    public Page findPage(Map<String, Object> params) {
        if (Constant.SUPER_ADMIN != getUserId()) {
            params.put("creator", getUserId());
        }
        //查询列表数据
        Query query = new Query(params);
        return Pages.findPage(userDao.findList(query), query);
    }

    /**
     * 修改密码
     *
     * @param user      用户
     * @param password    原密码
     * @param newPassword 新密码
     */
    @Transactional
    public int updatePassword(User user, String password, String newPassword) {
        //sha256加密
        password = new Md5Hash(user.getUsername()+password).toHex();
        //sha256加密
        newPassword = new Md5Hash(user.getUsername()+newPassword).toHex();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("password", password);
        map.put("newPassword", newPassword);
        return userDao.updatePassword(map);
    }

    public User getUserById(long id) {
        User user = userDao.get(id);
        List<Long> roleIdList = userDao.findRoleIdList(id);
        user.setRoleIdList(roleIdList);
        return user;
    }

    @Transactional
    public void saveUser(User user) {
        //sha256加密
        user.setPassword(new Md5Hash(user.getUsername()+user.getPassword()).toHex());
        userDao.save(user);
        checkRole(user);
        saveOrUpdateUserRole(user.getId(), user.getRoleIdList());
    }

    @Transactional
    public void updateUser(User user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Md5Hash(user.getUsername()+user.getPassword()).toHex());
        }
        userDao.update(user);
        checkRole(user);
        saveOrUpdateUserRole(user.getId(), user.getRoleIdList());
    }

    @Transactional
    public void deleteUser(Long[] userIds) {
        userDao.deleteBatch(userIds);
    }

    public User getByUsername(String username) {
        return userDao.getByUserName(username);
    }

    public List<String> findAllPerms(Long userId) {
        return userDao.findAllPerms(userId);
    }

    public List<Long> findAllMenuId(Long userId) {
        return userDao.findAllMenuId(userId);
    }



    public void saveOrUpdateUserRole(Long userId, List<Long> roleIdList) {
        if (roleIdList.size() == 0) {
            return;
        }

        //先删除用户与角色关系
        userDao.deleteUserRole(userId);

        //保存用户与角色关系
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIdList", roleIdList);
        userDao.saveUserRole(map);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(User user) {
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.getCreator() == Constant.SUPER_ADMIN) {
            return;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = userDao.findRoleIdList(user.getCreator());

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new RtException("新增用户所选角色，不是本人创建");
        }
    }


}
