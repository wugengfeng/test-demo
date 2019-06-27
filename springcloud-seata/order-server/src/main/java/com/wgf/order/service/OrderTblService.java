package com.wgf.order.service;

import java.math.BigDecimal;

/**
 * @author: wgf
 * @create: 2019-06-26 10:49
 * @description:
 **/
public interface OrderTblService {

    /**
     * 创建订单
     * @param userId
     * @param commodityCode
     * @param count
     * @param totalPrice
     */
    void create(String userId, String commodityCode, Integer count, BigDecimal totalPrice);
}
