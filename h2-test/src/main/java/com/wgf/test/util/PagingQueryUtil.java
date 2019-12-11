package com.wgf.test.util;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 分页查询帮助类, 用于跑批任务
 *
 * 用法
 *         // 这个查询最好加上id之类的排序
 *         ISelect iSelect = () -> allListingMapper.queryForJob();
 *
 *         PagingQueryUtil<List<AllListing>> pagingQueryUtil = PagingQueryUtil.instance(iSelect, 1000);
 *
 *         for (int i = 1; i <= pagingQueryUtil.getPageCountNum(); i++) {
 *             List<AllListing> allListingList = pagingQueryUtil.selectNext();
 *             ...
 *         }
 **/
public class PagingQueryUtil<T> {
    // 禁止 new 操作
    private PagingQueryUtil() {
    }

    public static PagingQueryUtil instance(ISelect selectPage, Integer pageSize) {
        PagingQueryUtil pagingQueryUtil = new PagingQueryUtil();
        pagingQueryUtil.selectPage = selectPage;
        pagingQueryUtil.pageSize = pageSize;

        // 查询总条数
        Long countNum = PageHelper.count(selectPage);
        if (countNum <= 0) {
            pagingQueryUtil.pageCountNum = 0l;
        } else {
            if (countNum % pageSize == 0) {
                pagingQueryUtil.pageCountNum = countNum / pageSize;
            } else {
                pagingQueryUtil.pageCountNum = countNum / pageSize + 1;
            }
        }

        return pagingQueryUtil;
    }

    /**
     * 分页查询方法
     */
    private ISelect selectPage;

    /**
     * 每页查询条数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Long pageCountNum;

    private AtomicInteger pageNum = new AtomicInteger(1);

    /**
     * 总条数
     */
    private Integer countNum;

    /**
     * 获取下一页数据
     *
     * @param <T>
     * @return
     */
    public <T> T selectNext() {
        Integer nowPageNum = pageNum.getAndIncrement();
        if (nowPageNum > pageCountNum) {
            return null;
        }

        return (T) PageHelper.startPage(nowPageNum, pageSize).doSelectPage(selectPage);
    }

    public ISelect getSelectPage() {
        return selectPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Long getPageCountNum() {
        return pageCountNum;
    }

    public AtomicInteger getPageNum() {
        return pageNum;
    }

    public Integer getCountNum() {
        return countNum;
    }
}
