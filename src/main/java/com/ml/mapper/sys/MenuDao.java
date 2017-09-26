package com.ml.mapper.sys;

import com.ml.entity.sys.Menu;
import com.ml.mapper.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author panda.
 * @since 2017-09-12 14:21.
 */
@Mapper
public interface MenuDao extends BaseDao<Menu> {
    List<Menu>  findListParentId(Long parentId);

    List<Menu> findNotButtonList();

    List<Menu>  findUserList(Long userId);
}
