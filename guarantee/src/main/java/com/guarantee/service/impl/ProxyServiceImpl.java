package com.guarantee.service.impl;

import com.guarantee.entity.Proxy;
import com.guarantee.mapper.ProxyMapper;
import com.guarantee.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: wgf
 * @create: 2019-07-19 17:17
 * @description:
 **/
@Service
public class ProxyServiceImpl implements ProxyService {


    @Autowired
    private ProxyMapper proxyMapper;

    @Override
    public Proxy getProxy() {
        Proxy proxy = this.proxyMapper.getProxy(0);
        if (Objects.isNull(proxy)) {
            proxy = this.proxyMapper.getProxy(1);
        }

        Proxy update = new Proxy();
        update.setId(proxy.getId());
        update.setStatus(1);
        this.proxyMapper.updateByPrimaryKeySelective(update);
        return proxy;
    }

    @Override
    public void freedProxy(Proxy proxy) {
        if (Objects.isNull(proxy) || Objects.isNull(proxy.getId())) {
            return;
        }

        Proxy update = new Proxy();
        update.setId(proxy.getId());
        update.setStatus(0);
        long time = System.currentTimeMillis() / 1000;
        update.setLasttime((int) time);
        this.proxyMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public void deprecated(Proxy proxy) {
        if (Objects.isNull(proxy) || Objects.isNull(proxy.getId())) {
            return;
        }

        Proxy update = new Proxy();
        update.setId(proxy.getId());
        update.setStatus(2);
        long time = System.currentTimeMillis() / 1000;
        update.setLasttime((int) time);
        this.proxyMapper.updateByPrimaryKeySelective(update);
    }
}
