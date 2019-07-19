package com.guarantee;

import com.guarantee.entity.Proxy;
import com.guarantee.service.ProxyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: wgf
 * @create: 2019-07-19 17:12
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GuaranteeApplication.class})
public class ProxyTest {
    @Autowired
    private ProxyService proxyService;

    @Test
    public void test() {
        Proxy proxy = proxyService.getProxy();
        System.out.println(proxy);
    }

}
