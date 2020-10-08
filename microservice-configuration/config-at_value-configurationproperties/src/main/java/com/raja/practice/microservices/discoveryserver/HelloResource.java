package com.raja.practice.microservices.discoveryserver;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class HelloResource {
	
	@Value("HelloWorld")
	private String staticHello;
	
	@Value("${app.name}")
	private String appName;
	
	@Value("${concat.msg: from }")
	private String concatMsg;
	
	@Value("${myapp.consumer.lst}")
	private List<String> consumeList;
	
	@Value("#{${DB_BOOTSTRAP_CREDENTIALS}}")
	private Map<String,String> dbCredentials;
	
	@Autowired
	private MyAppClient client;
	
	@Value("${app.welcome.msg}")
	private String appWlcmeMsg;
	
	@GetMapping
	public String hello() {
		return staticHello+concatMsg+appName+" consumers:"+consumeList+" dbCredentials: "+dbCredentials+" clientCredentials: "+client.toString()+"\\n"+appWlcmeMsg;
	}
}
