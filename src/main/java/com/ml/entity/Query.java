package com.ml.entity;

import com.ml.conf.SqlFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询参数.
 *
 * @author panda.
 * @since 2017-07-20 19:50.
 */
public class Query extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private static final String SIDX = "sidx";
    private static final String ORDER = "order";
    private static final String PAGE_NUM = "pageNum";
    private static final String PAGE_SIZE = "pageSize";
    //当前页码
    private int pageNum;
    //每页条数
    private int pageSize;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.pageNum = Integer.parseInt(params.get(PAGE_NUM).toString());
        this.pageSize = Integer.parseInt(params.get(PAGE_SIZE).toString());
        this.put("page", pageNum);
        this.put("rows", pageSize);

        String sidx = params.get(SIDX).toString();
        String order = params.get(ORDER).toString();
        this.put(SIDX, SqlFilter.filter(sidx));
        this.put(ORDER, SqlFilter.filter(order));
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}