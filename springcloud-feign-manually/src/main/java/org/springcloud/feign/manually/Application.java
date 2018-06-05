package org.springcloud.feign.manually;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
@EnableFeignClients
@RestController
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Autowired
	TestClient client;
	@RequestMapping(value="/eureka",method=RequestMethod.GET)
	public String getMethodName() {
		return client.serviceUrl();
	}
	@RequestMapping(value="/hi",method=RequestMethod.GET)
	public String hi() {
		return client.hi();
	}
	
	

}