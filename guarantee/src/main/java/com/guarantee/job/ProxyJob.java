package com.guarantee.job;

import com.guarantee.mapper.ProxyMapper;
import com.guarantee.proxy.FastIpProxyManager;
import com.guarantee.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.Proxy;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: wgf
 * @create: 2019-07-26 19:31
 * @description:
 **/
@Component
public class ProxyJob {

    @Autowired
    private ProxyMapper proxyMapper;

    @Scheduled(initialDelay = 10 * 1000L, fixedDelay = 1000L * 60 * 20)
    public void run() {
        ProxyManager proxyManager = new FastIpProxyManager("905063077378711", "fupsfrrqoyf0o2ggf49c8jgpi40dldwj");
        Set<Proxy> proxySet = proxyManager.getProxy();

        if (!CollectionUtils.isEmpty(proxySet)) {
            List<String> proxyList = proxySet.stream()
                    .map(item -> item.address().toString().replace("/", ""))
                    .collect(Collectors.toList());

            List<com.guarantee.entity.Proxy> list = proxyList.stream()
                    .map(item -> {
                        com.guarantee.entity.Proxy proxy = new com.guarantee.entity.Proxy();
                        proxy.setStatus(0);
                        proxy.setProxyip(item);
                        long time = System.currentTimeMillis() / 1000;
                        proxy.setLasttime((int) time);
                        return proxy;
                    }).collect(Collectors.toList());

            proxyMapper.delete(null);
            proxyMapper.insertList(list);
        }
    }

}
