package com.practice.raja.microservice.limits.proxyclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "limits-msg-provider",url = "localhost:8000")
public interface LimitsMsgProviderFeignProxyClient {
	@GetMapping("/getmsg")
	public String getMsg();
}
