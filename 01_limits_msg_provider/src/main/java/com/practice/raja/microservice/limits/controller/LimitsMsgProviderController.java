package com.practice.raja.microservice.limits.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsMsgProviderController {
	
	
	@Value("${CF_INSTANCE_IP:DUMMY}")
	private String instanceIpAddress;
	
	@Value("${server.port}")
	private String localPort;
	
	@GetMapping("/getmsg")
	public String getMsg() {	
		instanceIpAddress = instanceIpAddress.equals("DUMMY")? localPort :instanceIpAddress;
		return "HELLO FROM MESSAGE PROVIDER ::"+instanceIpAddress;
	}
}

final class LimitsMsgProviderConstants{
	
	
	public LimitsMsgProviderConstants() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static final String PCF_IP = "CF_INSTANCE_IP";

	public static final String LOCAL_PORT;
	
	static{
		LOCAL_PORT = ""+System.getenv("server.port")+"";
	}
	
}
