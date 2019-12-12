package com.practice.raja.microservice.limits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.raja.microservice.limits.proxyclient.LimitsMsgProviderFeignProxyClient;
import com.practice.raja.microservice.limits.vo.Limits;

@RestController
public class LimitsController {
	
	@Autowired
	LimitsMsgProviderFeignProxyClient msgProviderProxy;
		
	@GetMapping("/limits")
	public Limits getLimits() {		
		return new Limits("100","1000", msgProviderProxy.getMsg());
	}
}
