package com.samples.microservices.limitsservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	Configuration configuration;
	
	@GetMapping("/limits")  
	public LimitConfiguration retriveLimitsFromConfigurations()  
	{  
	return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());  
	}

	@HystrixCommand(fallbackMethod = "retrieveLimitConfigFallback")
	@GetMapping("/hystrix-test")
    public LimitConfiguration retrieveLimitConfig() {
        throw new RuntimeException("Not Available");
    }

    public LimitConfiguration retrieveLimitConfigFallback() {
        return new LimitConfiguration(999, 9);
    }
}
