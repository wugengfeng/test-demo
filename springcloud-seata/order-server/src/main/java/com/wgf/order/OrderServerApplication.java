package com.wgf.order;

import com.wgf.order.config.FeignMappingDefaultConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

// 启动eureka
@EnableEurekaClient
// 启动feign
@EnableFeignClients
// 启动熔断器
@EnableCircuitBreaker
@SpringBootApplication
@Import(value = {FeignMappingDefaultConfig.class})
public class OrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }

}
