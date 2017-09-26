package com.ml.mapper.sys;

import com.ml.entity.sys.Role;
import com.ml.mapper.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2017-09-12 14:22.
 */
@Mapper
public interface RoleDao extends BaseDao<Role> {
    List<Long> findMenuIdList(Long roleId);

    void saveRoleMenu(Map<String, Object> map);
   void deleteRoleMenu(Long roleId);
}
