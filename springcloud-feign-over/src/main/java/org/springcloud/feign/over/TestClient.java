package org.springcloud.feign.over;


import org.springcloud.feign.over.conf.FooConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="startSnow",url="http://127.0.0.1:1111",configuration=FooConfiguration.class)
public interface TestClient {
 
    @RequestMapping(value="/eureka/apps/{serviceName}")
    public String findEurekaByServiceName(@PathVariable("serviceName") String serviceName);


}