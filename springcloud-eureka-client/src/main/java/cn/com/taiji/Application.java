package cn.com.taiji;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@EnableEurekaClient
@SpringBootApplication
@RestController
public class Application {
	@Autowired
	private EurekaClient discoveryClient;
    @RequestMapping(method = RequestMethod.GET, value = "/eureka-info")
	public String serviceUrl() {
	    InstanceInfo instance = discoveryClient.getNextServerFromEureka("CLIENT", false);
	    return instance.getHomePageUrl();
	}

	@GetMapping("/login/{loginName}")
	public String login(@PathVariable String loginName) {
	return loginName+"恭喜登录成功";
	}
	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

}