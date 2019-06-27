package com.wgf.order.service.impl;

import com.wgf.order.entity.OrderTbl;
import com.wgf.order.mapper.OrderTblMapper;
import com.wgf.order.service.OrderTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author: wgf
 * @create: 2019-06-26 10:50
 * @description:
 **/
@Service
public class OrderTblServiceImpl implements OrderTblService {

    @Autowired
    private OrderTblMapper orderTblMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(String userId, String commodityCode, Integer count, BigDecimal totalPrice) {

        OrderTbl orderTbl = new OrderTbl();
        orderTbl.setUserId(userId);
        orderTbl.setCommodityCode(commodityCode);
        orderTbl.setCount(count);
        orderTbl.setMoney(totalPrice);

        this.orderTblMapper.insertSelective(orderTbl);
    }
}
