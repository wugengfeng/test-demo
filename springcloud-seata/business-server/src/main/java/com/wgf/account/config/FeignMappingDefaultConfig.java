package com.wgf.account.config;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author: wgf
 * @create: 2019-06-09 16:39
 * @description: 防止spring扫描到Feign定义的路由(java interface), 把它解析为本服务的路由
 **/

@ConditionalOnClass({Feign.class})
public class FeignMappingDefaultConfig {

    @Bean
    public WebMvcRegistrations feignWebRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new FeignFilterRequestMappingHandlerMapping();
            }
        };
    }

    private static class FeignFilterRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        @Override
        protected boolean isHandler(Class<?> beanType) {
            return super.isHandler(beanType) && ((AnnotationUtils.findAnnotation(beanType, RestController.class) != null)
                    || (AnnotationUtils.findAnnotation(beanType, Controller.class) != null));
        }
    }
}
