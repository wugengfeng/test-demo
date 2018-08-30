package com.wgf.proxy;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Http URL，扩展HashMap
 */
public class RequestUrl extends HashMap<Serializable, Serializable> {

    public RequestUrl(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    private String urlPrefix;

    public RequestUrl addParam(Serializable key, Serializable value) {
        super.put(key, value);
        return this;
    }

    /**
     * 拼接成get请求参数
     *
     * @param isFormat 是否需要格式化（拼接问号）
     * @return
     */
    private String getUrlParam(boolean isFormat) {

        StringBuilder sb = new StringBuilder();
        if (isFormat) {
            sb.append("?");
        }

        int index = 0;
        for (Map.Entry<Serializable, Serializable> entry : this.entrySet()) {
            if (index == 0) {
                index = -1;
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return sb.toString();
    }

    /**
     * 根据参数拼接完成的url地址
     *
     * @return
     */
    public String getUrl() {
        return this.urlPrefix + this.getUrlParam(true);
    }

    /**
     * 将请求参数转为JSON格式
     */
    public String getJsonParam() {
        return JSON.toJSONString(this);
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }
}
