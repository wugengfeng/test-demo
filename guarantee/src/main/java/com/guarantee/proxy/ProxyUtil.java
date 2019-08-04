package com.guarantee.proxy;

import com.alibaba.fastjson.JSON;
import com.guarantee.util.HttpClientUtil;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by acer on 2019/8/4.
 */
public class ProxyUtil {
    public static String getProxy() throws URISyntaxException {
        long time = System.currentTimeMillis() / 1000;
        Map<String, String> param = new HashMap<>();
        String url = "http://183.129.244.16:88/open";
        param.put("user_name", "china123ap1");
        param.put("timestamp", String.valueOf(time));
        param.put("md5", "64FB8933D1A8566DE321507ABEDC08E3");
        param.put("pattern", "json");
        param.put("number", "1");

        String json = HttpClientUtil.getRequest(url, param);
        ProxyBean proxyBean = JSON.parseObject(json, ProxyBean.class);

        return String.format("%s:%s", proxyBean.getDomain(), proxyBean.getPort().get(0));
    }
}
