package org.springcloud.ribbon.with.hystrix.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
@RestController
public class TestController {
	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 在同一个线程中执行
	 * @param login
	 * @return
	 */
	@GetMapping("/login/{login}")
	 //@HystrixCommand(fallbackMethod = "defaultStores")
	@HystrixCommand(fallbackMethod = "defaultStores",
    commandProperties = {
      @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE")
    }
)
	public String login(@PathVariable String login) {
		//vip  virtual ip
		return this.restTemplate.getForObject("http://client:1212/test/login/"+login, String.class);
	}
	 public String defaultStores(String login) {
	        return "...................";
	    }
}
