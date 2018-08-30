package com.wgf.proxy.code;

import com.wgf.constant.RequestMethod;
import com.wgf.proxy.ProxyManager;
import com.wgf.proxy.RequestUrl;
import okhttp3.*;

import java.io.IOException;

/**
 * 抽象代理管理器
 * POST请求方式使用 JSON 格式交互
 */
public abstract class JsonProxyManager<T> implements ProxyManager<T> {

    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 获取代理的默认实现
     *
     * @param url
     * @param method
     * @return
     */
    @Override
    public T getProxy(RequestUrl url, RequestMethod method) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Response response;
        if (RequestMethod.GET.equals(method)) {
            response = client.newCall(new Request.Builder().get().url(url.getUrl()).build()).execute();
        } else {
            MediaType mediaType = MediaType.parse(CONTENT_TYPE);
            RequestBody requestBody = RequestBody.create(mediaType, url.getJsonParam());
            response = client.newCall(new Request.Builder().url(url.getUrlPrefix()).post(requestBody).build()).execute();
        }

        return parse(response);
    }

    /**
     * 解析 Response 响应内容，具体实现由子类扩展
     *
     * @param response
     * @return
     */
    protected abstract T parse(Response response) throws IOException;
}
