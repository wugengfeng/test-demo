package com.wgf.account.apiImpl;

import com.wgf.account.api.AccountApi;
import com.wgf.account.service.AccountTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author: wgf
 * @create: 2019-06-26 10:34
 * @description:
 **/
@RestController
public class AccountApiImpl implements AccountApi {

    @Autowired
    private AccountTblService accountTblService;

    @Override
    public void debit(String userId, BigDecimal money) {
        this.accountTblService.debit(userId, money);
    }
}
