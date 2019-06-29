package com.wgf.manager;

import com.wgf.account.api.AccountApi;
import com.wgf.order.api.OrderApi;
import com.wgf.storage.api.StorageApi;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: wgf
 * @create: 2019-06-26 10:19
 * @description: 订单管理器
 **/
@Service
public class OrderManager {

    @Autowired
    private OrderApi orderApi;

    @Autowired
    private AccountApi accountApi;

    @Autowired
    private StorageApi storageApi;

    /**
     * 用户下单流程
     *
     * @param userId
     * @param commodityCode
     * @param orderCount
     * @return
     */
    @GlobalTransactional
    public Boolean purchase(String userId, String commodityCode, int orderCount) {
        // 计算订单金额
        BigDecimal price = new BigDecimal("5");
        BigDecimal totalPrice = price.multiply(new BigDecimal(String.valueOf(orderCount)));

        // 扣减库存
        this.storageApi.deduct(commodityCode, orderCount);

        // 创建订单
        this.orderApi.create(userId, commodityCode, orderCount, totalPrice);

        // 扣减金额
        this.accountApi.debit(userId, totalPrice);

        return true;
    }
}
