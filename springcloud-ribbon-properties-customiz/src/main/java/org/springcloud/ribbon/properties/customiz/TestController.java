package org.springcloud.ribbon.properties.customiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class TestController {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	LoadBalancerClient loadBalancerClient;
	/**
	 * 
	 * @param login
	 * @return
	 */
	@GetMapping("/login/{login}")
	public String login(@PathVariable String login) {
		//vip  virtual ip
		return this.restTemplate.getForObject("http://client/test/login/"+login, String.class);
	}
	
	
	@GetMapping(value = "/test")
	public String  getMethodName() {
		ServiceInstance intstace=loadBalancerClient.choose("client");
		System.out.println(intstace.getPort()+"---"+intstace.getHost()+""+intstace.getUri());
		return "1";
	}

}
