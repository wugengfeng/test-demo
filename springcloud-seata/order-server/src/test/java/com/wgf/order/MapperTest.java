package com.wgf.order;

import com.wgf.order.entity.OrderTbl;
import com.wgf.order.mapper.OrderTblMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author: wgf
 * @create: 2019-06-25 16:46
 * @description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderServerApplication.class)
public class MapperTest {

    @Autowired
    private OrderTblMapper orderTblMapper;

    @Test
    public void selectAll() {
        List<OrderTbl> orderTblList = this.orderTblMapper.selectAll();
        System.out.println(orderTblList);
    }
}
