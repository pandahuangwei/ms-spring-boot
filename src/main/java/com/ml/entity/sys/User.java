package com.ml.entity.sys;

import com.ml.entity.BaseEntity;

import java.util.List;

/**
 * @author panda.
 * @since 2017-09-06 17:14.
 */
public class User extends BaseEntity {
    private static final Long serialVersionUID = 1L;

    private String username;
    private transient String password;
    private int status;
    private String email;
    private String mobile;
    private List<Long> roleIdList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
