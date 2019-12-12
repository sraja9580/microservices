

1. Accessing Service using Febin client proxy
----------------------------------------------

	Create Provider (01_limits_msg_provider)
	----------------------------------------
		1. Create service with following starter
		   Spring Web
		   Service Registry PCF
		
		2. Provide end point /getmsg which returns 
			Welcome message with ip address in PCF and port in local
			
		3. Define in app name it is as part of froxy client in consumer side
			IT IS REQUIRED ONLY INCASE OF FEIGN CLIEN OR EUREKA REGISTORY
			spring.application.name=limits-msg-provider

	FEIGN CLIENT : Create Client (02_limits_Service_feign)
	----------------------------------------
		1. Create service with following starter
		   Spring Web
		   Feign client		 
		   lombok
		   Service Registry PCF
		   
		2. Provide endpoint for /limits
			return min,max,dummy msg
		
		3. Write proxy client for 01_limits_msg_provider 
			
			@FeignClient(name = "limits-msg-provider",url = "localhost:8000")
			public interface LimitsMsgProviderFeignProxyClient {
				@GetMapping("/getmsg")
				public String getMsg();
			}

		4. Enable Feign clients in Boot App java file
			@EnableFeignClients(basePackages ="com.practice.raja.microservice.limits.proxyclient" )
			
		5. Autowire proxy client in controller
		
		6. Get message from 01_limits_msg_provider using proxy client
		
		7. Define in app name it is as part of froxy client in consumer side
			IT IS REQUIRED ONLY INCASE OF FEIGN CLIEN OR EUREKA REGISTORY
			spring.application.name=limits-msg-provider
			
	RIBBON CLIENT SIDE LOAD BALANCING: Create Client (03_limits_Service_ribbon)
	----------------------------------------
		1. Create service with following starter
		   Spring Web
		   Feign client	
		   RIBBON
		   lombok
		   Service Registry PCF
		   
		2. Provide endpoint for /limits
			return min,max,dummy msg
		
		3. RIBBON LOAD BALANCING: Write proxy client for 01_limits_msg_provider with 
			
			@FeignClient(name = "limits-msg-provider")
			@RibbonClient(name = "limits-msg-provider")
			public interface LimitsMsgProviderFeignProxyClient {
				@GetMapping("/getmsg")
				public String getMsg();
			}
			
		4.	Configure list of client instanc in application.properties
			limits-msg-provider.ribbon.listOfServers=http://localhost:8000,http://localhost:8001
			
		5. Enable Feign clients in Boot App java file
			@EnableFeignClients(basePackages ="com.practice.raja.microservice.limits.proxyclient" )
			
		6. Autowire proxy client in controller
		
		7. Get message from 01_limits_msg_provider using proxy client
		
		8. Define in app name it is as part of froxy client in consumer side
			IT IS REQUIRED ONLY INCASE OF EUREKA REGISTORY
			spring.application.name=limits-service
