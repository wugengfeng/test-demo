package com.wgf.test;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.wgf.H2TestApplication;
import com.wgf.test.entity.base.AllListing;
import com.wgf.test.entity.h2.H2AllListing;
import com.wgf.test.mapper.base.AllListingMapper;
import com.wgf.test.mapper.h2.H2AllListingMapper;
import com.wgf.test.util.PagingQueryUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: wgf
 * @create: 2019-12-06 16:54
 * @description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = H2TestApplication.class)
public class H2Test {

    @Autowired
    private AllListingMapper allListingMapper;

    @Autowired
    private H2AllListingMapper h2AllListingMapper;

    /**
     * 模拟 mysql 数据预热 到 h2
     * 实际情况可以使用spring 监听器
     */
    @Before
    public void init() {

        Long countNum = PageHelper.count(() -> this.h2AllListingMapper.selectAll());

        // 如果数据已预热则跳过
        if (countNum > 0) {
            return;
        }

        ISelect iSelect = () -> this.allListingMapper.selectAll();
        PagingQueryUtil<List<AllListing>> pagingQueryUtil = PagingQueryUtil.instance(iSelect, 1000);

        for (int i = 1; i <= pagingQueryUtil.getPageCountNum(); i++) {
            List<AllListing> allListingList = pagingQueryUtil.selectNext();

            List<H2AllListing> h2AllListingList = allListingList.stream()
                    .map(item -> {
                        H2AllListing h2AllListing = new H2AllListing();
                        BeanUtils.copyProperties(item, h2AllListing);
                        return h2AllListing;
                    }).collect(Collectors.toList());

            this.h2AllListingMapper.insertList(h2AllListingList);
            System.out.println(String.format("处理页数:%s", i));
        }
    }

    /**
     * 注入mapper测试性能
     */
    @Test
    public void select() {

        Long start = System.currentTimeMillis();
        PageHelper.startPage(0,1000);
        this.allListingMapper.select(new AllListing());
        System.out.println(String.format("mysql 耗时 %s 毫秒", System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        PageHelper.startPage(0,1000);
        this.h2AllListingMapper.select(new H2AllListing());
        System.out.println(String.format("h2 耗时 %s 毫秒", System.currentTimeMillis() - start));
    }
}
