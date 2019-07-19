package com.guarantee.agent;

import java.util.List;

/**
 * @description:
 **/
public class DefaultUserAgent implements UserAgent {

    @Override
    public String getUserAgent() {
        List<String> userAgent = UserAgentConf.USER_AGENT;
        int min = 0;
        int max = userAgent.size() - 1;
        int index = min + (int) (Math.random() * (max - min + 1));
        return userAgent.get(index);
    }
}
