package com.ml.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ml.entity.BaseEntity;
import com.ml.entity.Page;
import com.ml.entity.Query;
import org.apache.ibatis.jdbc.Null;

import java.util.List;

/**
 *  分页工具类.
 * @author panda.
 * @since 2017-06-07 10:11.
 */
public class Pages {

    public static Page findPage(List<?> mapperSearch, Query query) {
        if (query != null) {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
        }
        PageInfo<?> info = new PageInfo<>(mapperSearch);
        return newPage(info);
    }

    /**
     * 分页查询方法
     *
     * @param mapperSearch 对应的map查询方法
     * @param query        查询参数实体
     * @param <T>          T
     * @return 分页list
     */
    public static <T extends BaseEntity> PageInfo<T> findByPage(List<T> mapperSearch, Query query) {
        if (query != null) {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
        }
        return new PageInfo<>(mapperSearch);
    }

    public static <T extends BaseEntity> PageInfo<T> findByPage(List<T> mapperSearch, T searchEntity) {
        if (searchEntity != null && searchEntity.getPage() != null && searchEntity.getRows() != null) {
            PageHelper.startPage(searchEntity.getPage(), searchEntity.getRows());
        }
        return new PageInfo<>(mapperSearch);
    }

    private static Page newPage(PageInfo<?> pageInfo) {
        Page page = new Page();
        if (pageInfo == null) {
            return page;
        }
        page.setTotalCount(pageInfo.getTotal());
        page.setCurrPage(pageInfo.getPageNum());
        page.setPageSize(pageInfo.getPageSize());
        page.setTotalPage((int) Math.ceil((double) pageInfo.getTotal() / pageInfo.getPageSize()));
        page.setList(pageInfo.getList());
        return page;
    }
}

/**
 * PageInfo
 * //当前页
 * private int pageNum;
 * //每页的数量
 * private int pageSize;
 * //当前页的数量
 * private int size;
 * //排序
 * private String orderBy;
 * <p>
 * //由于startRow和endRow不常用，这里说个具体的用法
 * //可以在页面中"显示startRow到endRow 共size条数据"
 * <p>
 * //当前页面第一个元素在数据库中的行号
 * private int startRow;
 * //当前页面最后一个元素在数据库中的行号
 * private int endRow;
 * //总记录数
 * private long total;
 * //总页数
 * private int pages;
 * //结果集
 * private List<T> list;
 * <p>
 * //第一页
 * private int firstPage;
 * //前一页
 * private int prePage;
 * //下一页
 * private int nextPage;
 * //最后一页
 * private int lastPage;
 * <p>
 * //是否为第一页
 * private boolean isFirstPage = false;
 * //是否为最后一页
 * private boolean isLastPage = false;
 * //是否有前一页
 * private boolean hasPreviousPage = false;
 * //是否有下一页
 * private boolean hasNextPage = false;
 * //导航页码数
 * private int navigatePages;
 * //所有导航页号
 * private int[] navigatepageNums;
 */