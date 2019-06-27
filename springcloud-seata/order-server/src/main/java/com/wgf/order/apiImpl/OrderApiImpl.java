package com.wgf.order.apiImpl;

import com.wgf.order.api.OrderApi;
import com.wgf.order.service.OrderTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author: wgf
 * @create: 2019-06-09 15:53
 * @description:
 **/
@RestController
public class OrderApiImpl implements OrderApi {

    @Autowired
    private OrderTblService orderTblService;

    @Override
    public void create(String userId, String commodityCode, Integer count, BigDecimal totalPrice) {
        this.orderTblService.create(userId, commodityCode, count, totalPrice);
    }
}
