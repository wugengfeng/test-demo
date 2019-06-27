package com.wgf.order.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author: wgf
 * @create: 2019-06-01 14:41
 * @description:
 **/
@FeignClient("order-server")
@RequestMapping("/order/order_api")
public interface OrderApi {

    /**
     * 创建订单
     * @param userId
     * @param commodityCode
     * @param count
     * @param totalPrice
     */
    @GetMapping("/create")
    void create(@RequestParam("userId") String userId,
                @RequestParam("commodityCode") String commodityCode,
                @RequestParam("count") Integer count,
                @RequestParam("totalPrice") BigDecimal totalPrice);

}
