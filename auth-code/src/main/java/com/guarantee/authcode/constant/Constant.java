package com.guarantee.authcode.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

    /**
     * dll文件路径
     */
    public static String daYangDistinguishPath;


    @Value("${daYangDistinguishPath}")
    public void setDaYangDistinguishPath(String daYangDistinguishPath) {
        Constant.daYangDistinguishPath = daYangDistinguishPath;
    }
}
