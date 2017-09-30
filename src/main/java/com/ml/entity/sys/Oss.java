package com.ml.entity.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * @author panda.
 * @since 2017-09-30 10:34.
 */
public class Oss implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //URL地址
    private String url;
    //创建时间
    private Date createDt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }
}
