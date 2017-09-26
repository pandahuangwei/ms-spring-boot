package com.ml.mapper.sys;

import com.ml.entity.sys.User;
import com.ml.mapper.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author panda.
 * @since 2017-09-12 10:59.
 */
@Mapper
public interface UserDao extends BaseDao<User> {
    User getByUserName(String username);

    int updatePassword(Map<String, Object> map);

    List<Long> findRoleIdList(long userId);

    List<String> findAllPerms(long userId);

    List<Long> findAllMenuId(long userId);

    void saveUserRole(Map<String, Object> map);

    void deleteUserRole(long userId);

}
