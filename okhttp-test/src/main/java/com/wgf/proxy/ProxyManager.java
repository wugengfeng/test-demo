package com.wgf.proxy;

import com.wgf.constant.RequestMethod;

import java.io.IOException;

public interface ProxyManager<T> {

    T getProxy(RequestUrl url, RequestMethod method) throws IOException;
}