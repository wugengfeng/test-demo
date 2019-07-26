package com.guarantee.proxy;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FastIpProxyManager implements ProxyManager {

    /**
     * 快代理的订单id
     */
    private String orderid;

    /**
     * 快代理签名
     */
    private String signature;

    // 快代理API接口
    private final static String API = "http://ent.kuaidaili.com/api/getproxy?orderid=[orderid]&num=25&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=2&method=2&an_an=1&an_ha=1&sp1=1&quality=2&sort=1&sep=1&signature=[signature]";

    private final static String ORDER_ID = "[orderid]";

    private final static String SIGNATURE = "[signature]";

    public FastIpProxyManager(String orderid, String signature) {
        this.orderid = orderid;
        this.signature = signature;
    }

    @Override
    public Set<Proxy> getProxy() {

        Set<Proxy> resultSet = null;

        try {
            String url = API.replace(ORDER_ID, this.orderid).replace(SIGNATURE, this.signature);
            OkHttpClient client = new OkHttpClient.Builder().build();
            Response response = null;
            response = client.newCall(new Request.Builder().get().url(url).build()).execute();
            ResponseBody body = response.body();
            String content = body.string();
            resultSet = this.processProxy(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * 根据返回的数据解析出IP代理
     *
     * @return
     */
    private Set<Proxy> processProxy(String content) {
        Set<Proxy> resultSet = null;

        if (StringUtils.isNotBlank(content)) {
            resultSet = Arrays.stream(content.split("[\r\n]+"))
                    .distinct()
                    .filter(StringUtils::isNotBlank)
                    .map(line -> line.split(":"))
                    .map(split -> {
                        try {
                            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(split[0], Integer.valueOf(split[1])));
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }

        return resultSet;
    }
}
