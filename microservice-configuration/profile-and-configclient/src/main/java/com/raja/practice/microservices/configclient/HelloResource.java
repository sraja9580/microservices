package com.raja.practice.microservices.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {
	
	@Value("${welcome.message}")
	private String welcomMsg;
	
	@GetMapping("/hello")
	public String hello() {
		return welcomMsg;
	}
}
