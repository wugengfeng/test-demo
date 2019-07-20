package com.guarantee.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

    /**
     * dll文件路径
     */
    public static String daYangDistinguishPath;

    /**
     * 驱动路径
     */
    public static String chromedriverPath;

    /**
     * 缓存目录
     */
    public static String cacheDir;

    /**
     * 是否启用缓存
     */
    public static Boolean enableProxy;

    public static String codeUrl;

    @Value("${daYangDistinguishPath}")
    public void setDaYangDistinguishPath(String daYangDistinguishPath) {
        Constant.daYangDistinguishPath = daYangDistinguishPath;
    }

    @Value("${chromedriverPath}")
    public void setChromedriverPath(String chromedriverPath) {
        Constant.chromedriverPath = chromedriverPath;
    }

    @Value("${cacheDir}")
    public void setCacheDir(String cacheDir) {
        Constant.cacheDir = cacheDir;
    }

    @Value("${enableProxy}")
    public void setEnableProxy(Boolean enableProxy) {
        this.enableProxy = enableProxy;
    }

    @Value("${codeUrl}")
    public void setCodeUrl(String codeUrl) {
        Constant.codeUrl = codeUrl;
    }
}
