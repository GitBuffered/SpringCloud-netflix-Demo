package org.springcloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {
	@Value("${tesw}")
	private String profile;
	    
	@RequestMapping("/")
    public String home() {
        return this.profile;
    }
}
