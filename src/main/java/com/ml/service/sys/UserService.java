package com.ml.service.sys;

import com.ml.Constants.Constant;
import com.ml.entity.Page;
import com.ml.entity.Query;
import com.ml.entity.sys.User;
import com.ml.mapper.sys.UserDao;
import com.ml.utils.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

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
        if(Constant.SUPER_ADMIN != getUserId()){
            params.put("creator", getUserId());
        }
        //查询列表数据
        Query query = new Query(params);
        return Pages.findPage(userDao.findList(query), query);
    }

    public User getUserById(long id) {
        return null;
    }

    public User getUserByUsername(String username) {
        return null;
    }

    public Set<String> findUserPermissions(Long userId) {
        return null;
    }


}
