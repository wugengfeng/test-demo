package com.guarantee.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: 国外验证码破解
 * @create: 2019-07-19 19:28
 * @description:
 **/
public class CaptchaUtil {
    public static String getAuthCode(String base64) throws InterruptedException, URISyntaxException {
        String result = "";

        if (StringUtils.isBlank(base64)) {
            return result;
        }

        String key = "ceb5441da6b3aec1c2d222c945a92c3e";
        Map<String, String> param = new HashMap<>();
        param.put("method", "base64");
        param.put("key", key);
        param.put("body", String.format("data:image/jpeg;base64,%s", base64));

        String id = HttpClientUtil.postJSON("https://2captcha.com/in.php", JSON.toJSONString(param));
        if (StringUtils.isBlank(id) || !id.contains("OK")) {
            return result;
        }

        id = id.replace("OK|", "");
        TimeUnit.SECONDS.sleep(6);

        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("id", id);
        map.put("action", "get");

        result = HttpClientUtil.getRequest("https://2captcha.com/res.php", map);

        if (StringUtils.isNotBlank(result)) {
            result = result.replace("OK|", "");
        }

        return result;
    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        System.out.println(getAuthCode("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"));
    }
}
