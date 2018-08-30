package com.wgf.proxy.code;

import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleProxyManager extends JsonProxyManager<List<Proxy>> {

    // 提取 ip:port 正则表达式
    private static final String RGEX = "\\d+\\.\\d+\\.\\d+\\.\\d+\\:\\d+";

    @Override
    protected List<Proxy> parse(Response response) throws IOException {
        List<Proxy> proxyList = new ArrayList<>();

        if (response.code() == 200) {
            ResponseBody body = response.body();
            String content = body.string();

            Pattern pattern = Pattern.compile(RGEX);
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                String[] ipAddress = matcher.group(0).split(":");
                proxyList.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipAddress[0], Integer.valueOf(ipAddress[1]))));
            }
        }

        return proxyList;
    }
}
