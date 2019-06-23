package com.wgf.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// 启动eureka
@EnableEurekaClient
// 启动feign
@EnableFeignClients
// 启动熔断器
@EnableCircuitBreaker
@SpringBootApplication
public class AccountServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServerApplication.class, args);
	}

}
