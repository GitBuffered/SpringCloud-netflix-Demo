package org.springcloud.feign.manually;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("CLIENTB")
public interface TestClient {
    @RequestMapping(method = RequestMethod.GET, value = "/test/eureka-info")
	public String serviceUrl() ;
    @RequestMapping(method = RequestMethod.GET, value = "/test/hi")
	public String hi();


}