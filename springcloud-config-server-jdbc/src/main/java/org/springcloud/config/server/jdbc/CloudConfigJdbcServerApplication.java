package org.springcloud.config.server.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class CloudConfigJdbcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudConfigJdbcServerApplication.class, args);
    }
}