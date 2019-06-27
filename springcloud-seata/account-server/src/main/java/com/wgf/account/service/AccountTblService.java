package com.wgf.account.service;

import java.math.BigDecimal;

/**
 * @author: wgf
 * @create: 2019-06-26 10:35
 * @description:
 **/
public interface AccountTblService {

    /**
     * 扣减金额
     * @param userId
     * @param money
     */
    void debit(String userId, BigDecimal money);
}
