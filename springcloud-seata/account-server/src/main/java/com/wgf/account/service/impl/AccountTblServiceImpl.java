package com.wgf.account.service.impl;

import com.wgf.account.entity.AccountTbl;
import com.wgf.account.mapper.AccountTblMapper;
import com.wgf.account.service.AccountTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author: wgf
 * @create: 2019-06-26 10:37
 * @description:
 **/
@Service
public class AccountTblServiceImpl implements AccountTblService {

    @Autowired
    private AccountTblMapper accountTblMapper;

    private static final String ERROR_USER_ID= "1002";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void debit(String userId, BigDecimal money) {
        AccountTbl query = new AccountTbl();
        query.setUserId(userId);
        AccountTbl accountTbl = this.accountTblMapper.selectOne(query);

        accountTbl.setMoney(accountTbl.getMoney().subtract(money));
        this.accountTblMapper.updateByPrimaryKeySelective(accountTbl);

        // 抛出异常，让事务回滚
        if (ERROR_USER_ID.equals(userId)){
            throw new RuntimeException("account branch exception");
        }
    }
}
