package com.wgf;

import com.wgf.account.AccountServerApplication;
import com.wgf.account.entity.AccountTbl;
import com.wgf.account.mapper.AccountTblMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author: wgf
 * @create: 2019-06-25 16:46
 * @description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AccountServerApplication.class)
public class MapperTest {

    @Autowired
    private AccountTblMapper accountTblMapper;

    @Test
    public void selectAll() {
        List<AccountTbl> orderTblList = this.accountTblMapper.selectAll();
        System.out.println(orderTblList);
    }
}
