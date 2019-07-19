package com.guarantee.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: wgf
 * @create: 2019-07-19 19:28
 * @description:
 **/
public class CaptchaUtil {
    public static String getCode(String base64) throws InterruptedException, URISyntaxException {
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
        TimeUnit.SECONDS.sleep(5);

        List<NameValuePair> parametersBody = new ArrayList();
        parametersBody.add(new BasicNameValuePair("key", key));
        parametersBody.add(new BasicNameValuePair("id", id));
        parametersBody.add(new BasicNameValuePair("action", get));
        result = HttpClientUtil.getRequest("https://2captcha.com/res.php", parametersBody);
        return result;
    }
}
