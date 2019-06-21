package com.wgf.order.controller;

import com.wgf.order.api.OrderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wgf
 * @create: 2019-06-09 16:17
 * @description:
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OrderApi orderApi;

    @GetMapping("/order")
    public void setOrderApi(String userId, String commodityCode, Integer count) {
        this.orderApi.create(userId, commodityCode, count);
    }
}
