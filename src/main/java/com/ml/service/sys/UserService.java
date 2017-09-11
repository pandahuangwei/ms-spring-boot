package com.ml.service.sys;

import com.ml.entity.sys.User;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author panda.
 * @since 2017-09-05 3:27.
 */
@Service("userService")
public class UserService {

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
