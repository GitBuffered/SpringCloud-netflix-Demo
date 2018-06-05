package org.springcloud.feign.form;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
@SpringBootApplication
@EnableFeignClients
@RestController
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Autowired
	TestFeignClient testFeignClient;
	@GetMapping("/user/{id}")
	public User findById(@PathVariable Long id) {
	  HashMap<String, String> param = Maps.newHashMap();
	  param.put("username","zhangsan");
	  param.put("password","pwd");
	  this.testFeignClient.post(param);
	  return new User();
	}
	
	

}