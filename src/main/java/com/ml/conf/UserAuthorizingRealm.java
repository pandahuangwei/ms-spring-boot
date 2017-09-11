package com.ml.conf;

import com.ml.entity.sys.User;
import com.ml.service.sys.MenuService;
import com.ml.service.sys.UserService;
import com.ml.utils.I18ns;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author panda.
 * @since 2017-09-06 17:09.
 */
@Component
public class UserAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        Long userId = user.getId();

        //用户权限列表
        Set<String> permsSet = userService.findUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        //查询用户信息
        User user = userService.getUserByUsername(username);

        //账号不存在
        if (user == null) {
            throw new UnknownAccountException(I18ns.getMessage("exception.login.user.not"));
        }

        //密码错误
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException(I18ns.getMessage("exception.login.user.pws.not"));
        }

        //账号锁定
        if (0 == user.getStatus()) {
            throw new LockedAccountException(I18ns.getMessage("exception.login.user.lock"));
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
