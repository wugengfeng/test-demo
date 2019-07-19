package com.guarantee.service;

import com.guarantee.entity.Proxy;

/**
 * @author: wgf
 * @create: 2019-07-19 17:16
 * @description:
 **/
public interface ProxyService {

    /**
     * 获取ip
     * @return
     */
    Proxy getProxy();

    /**
     * 释放ip
     */
    void freedProxy(Integer id);

    /**
     * 废弃ip
     * @param id
     */
    void deprecated(Integer id);
}
