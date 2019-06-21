package com.wgf.order.apiImpl;

import com.wgf.order.api.OrderApi;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wgf
 * @create: 2019-06-09 15:53
 * @description:
 **/
@RestController
public class OrderApiImpl implements OrderApi {

    @Override
    public void create(@RequestParam("userId") String userId,
                       @RequestParam("commodityCode") String commodityCode,
                       @RequestParam("count") Integer count) {
        System.out.println(userId);
        System.out.println(commodityCode);
        System.out.println(count);
    }
}
