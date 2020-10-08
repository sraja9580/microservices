package com.raja.practice.microservices.discoveryserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("myapp")
public class MyAppClient {
	
	private String clientId;
	private String clientSecret;
	private String tokenURL;
	
	public MyAppClient() {
		super();
	}
	public MyAppClient(String clientId, String clientSecret, String tokenURL) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.tokenURL = tokenURL;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getTokenURL() {
		return tokenURL;
	}
	public void setTokenURL(String tokenURL) {
		this.tokenURL = tokenURL;
	}
	
	@Override
	public String toString() {
		return "MyAppClient [clientId=" + clientId + ", clientSecret=" + clientSecret + ", tokenURL=" + tokenURL + "]";
	}
		
}
