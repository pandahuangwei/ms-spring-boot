package com.ml.conf;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * allow:IP白名单(没有配置或者为空，则允许所有访问)
 * deny: IP黑名单 (存在共同时，deny优先于allow)
 * resetEnable:禁用HTML页面上的“Reset All”功能
 *
 * @author panda.
 * @since 2017-05-20 21:27.
 */
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow", value = "127.0.0.1,192.168.1.1"),
                @WebInitParam(name = "deny", value = "192.168.1.73"),
                //添加用户名密码的话，则查看druid监控需要登录
                // @WebInitParam(name = "loginUsername", getKey = "admin"),
                // @WebInitParam(name = "loginPassword", getKey = "123456"),
                @WebInitParam(name = "resetEnable", value = "false")
        })
public class DruidStartViewServlet extends StatViewServlet {
    private static final long serialVersionUID = 2L;
}
