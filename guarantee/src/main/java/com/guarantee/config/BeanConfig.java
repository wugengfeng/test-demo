package com.guarantee.config;

import com.guarantee.agent.DefaultUserAgent;
import com.guarantee.agent.UserAgent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wgf
 * @create: 2019-07-19 16:23
 * @description:
 **/
@Configuration
public class BeanConfig {
    @Bean
    public UserAgent defaultUserAgent() {
        return new DefaultUserAgent();
    }
}
