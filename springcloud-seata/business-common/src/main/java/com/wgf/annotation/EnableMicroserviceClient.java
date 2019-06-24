package com.wgf.annotation;

import com.wgf.config.FeignMappingDefaultConfig;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * 启用微服务客户端基本配置
 */
// 启动eureka
@EnableEurekaClient
// 启动feign
@EnableFeignClients
// 启动熔断器
@EnableCircuitBreaker
@Import(value = {FeignMappingDefaultConfig.class})
public @interface EnableMicroserviceClient {
}
