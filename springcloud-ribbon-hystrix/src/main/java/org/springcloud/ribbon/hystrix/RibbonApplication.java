package org.springcloud.ribbon.hystrix;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
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