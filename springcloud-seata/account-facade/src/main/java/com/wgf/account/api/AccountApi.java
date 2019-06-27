package com.wgf.account.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author: wgf
 * @create: 2019-06-25 18:36
 * @description: 用户Api接口
 **/
@FeignClient("account-server")
@RequestMapping("/account/account_api")
public interface AccountApi {

    /**
     * 扣减金额
     * @param userId
     * @param money
     * @return
     */
    @GetMapping("/debit")
    void debit(@RequestParam("userId") String userId, @RequestParam("money") BigDecimal money);

}
