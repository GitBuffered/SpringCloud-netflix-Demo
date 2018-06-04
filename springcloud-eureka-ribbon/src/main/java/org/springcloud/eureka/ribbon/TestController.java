package org.springcloud.eureka.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class TestController {
	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 
	 * @param login
	 * @return
	 */
	@GetMapping("/login/{login}")
	public String login(@PathVariable String login) {
		//vip  virtual ip
		return this.restTemplate.getForObject("http://client:1212/test/login/"+login, String.class);
	}
	
}
