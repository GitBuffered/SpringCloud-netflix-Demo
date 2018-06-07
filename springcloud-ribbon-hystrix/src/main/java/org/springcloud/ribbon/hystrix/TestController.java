package org.springcloud.ribbon.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

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
	@HystrixCommand(fallbackMethod = "defaultStores")
	public String login(@PathVariable String login) {
		// vip virtual ip
		return this.restTemplate.getForObject("http://client/test/login/" + login, String.class);
	}

	public String defaultStores(String login) {
		return "...................";

	}
}
