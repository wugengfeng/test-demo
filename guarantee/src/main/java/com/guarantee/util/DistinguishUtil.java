package com.guarantee.util;

import com.alibaba.fastjson.JSON;
import com.guarantee.dll.DistinguishDll;
import com.guarantee.entity.AuthCode;

/**
 * Created by acer on 2019/7/18.
 * dll 调用工具
 */
public class DistinguishUtil {
    private DistinguishUtil() {
    }

    public static String getAuthCode(String base64) {
        AuthCode authCode = new AuthCode(base64, null, "image");
        String jsonStr = JSON.toJSONString(authCode);
        return DistinguishDll.instanceDll.DaYangDistinguish(jsonStr);
    }
}
