package org.springcloud.ribbon.with.hystrix.propagation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class RibbonApplication {

	@Bean
	@LoadBalanced // 添加Ribbon
	public RestTemplate restTemple() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(RibbonApplication.class).web(true).run(args);
	}

}