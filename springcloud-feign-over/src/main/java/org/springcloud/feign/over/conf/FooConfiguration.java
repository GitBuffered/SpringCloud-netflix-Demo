package org.springcloud.feign.over.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class FooConfiguration {
   /* @Bean
    public Contract feignContract() {
        //这将SpringMvc Contract 替换为feign.Contract.Default
        return new feign.Contract.Default();
    }*/
    @Bean
    Logger.Level feignLoggerLevel() {
        //设置日志
        return Logger.Level.FULL;
    }
}
