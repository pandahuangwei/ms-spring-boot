package com.ml.conf;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * allow:IP白名单(没有配置或者为空，则允许所有访问)
 * deny: IP黑名单 (存在共同时，deny优先于allow)
 * resetEnable:禁用HTML页面上的“Reset All”功能
 *
 * @author panda.
 * @since 2017-05-20 21:16.
 */

@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源
        }
)
public class DruidStartFilter extends WebStatFilter {
}
