package com.wgf.controller.order;

import com.wgf.manager.OrderManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wgf
 * @create: 2019-06-26 10:18
 * @description:
 **/
@RestController
@RequestMapping("/order")
@Api(description = "订单控制器")
public class OrderController {

    @Autowired
    private OrderManager orderManager;

    @ApiOperation(value = "创建订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "userId", value = "用户id 1001：下单成功 1002：事务回滚"),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "commodityCode", value = "商品编码 2001"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "orderCount", value = "购买数量")
    })
    @GetMapping("/purchase")
    public Map<String, Integer> purchase(@ApiIgnore String userId, @ApiIgnore String commodityCode, @ApiIgnore int orderCount) {
        this.orderManager.purchase(userId, commodityCode, orderCount);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        return resultMap;
    }
}
