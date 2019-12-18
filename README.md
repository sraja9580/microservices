

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
	--------------------------------------------------------------------------
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

2. Eureka Service Registory
---------------------------

	Create Eureka Naming Server (04_limits-eureka-netflix-naming-server)
	--------------------------------------------------------------------
		1. Create boot app with following starter
		   Eureka Server	   	

		2. Make this app as Eureka server by doing following
		       *application.properties
			       spring.application.name=limits-eureka-netflix-naming-server
			       eureka.client.register-with-eureka=false
			       eureka.client.fetch-registry=false

			*Application.java
				@EnableEurekaServer

		* We need this Naming Server application only in local. In PCF we can get this as service.
		* We have to find the service with both 05_limits_msg_provider_eureka and 06_limits_Service_eureka
		* Other configurations in application.property file will be reconfigured using AutoReconfiguration future.

	Create Provider (05_limits_msg_provider_eureka)
	----------------------------------------
		1. Create service with following starter
		   Spring Web
		   Service Registry PCF
		
		2. Provide end point /getmsg which returns 
			Welcome message with ip address in PCF and port in local
			
		3. Registoring service with Eureke
		        Defile app-name in application.props and define Eureke server url
				spring.application.name=limits-msg-provider
				eureka.client.service-url.defaultZone=http://localhost:8761/eureka
			Make Service discoverable using anotation
				@EnableDiscoveryClient -->Application.java
				
		4. Now go and check in Eureka Server localhost:8761 you will be able to see the app
		
	Create Client (06_limits_Service_eureka)
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

		4. Enable Feign clients in Boot App java file
			@EnableFeignClients(basePackages ="com.practice.raja.microservice.limits.proxyclient" )

		5. Autowire proxy client in controller

		6. Get message from 01_limits_msg_provider using proxy client

		7. Registoring service with Eureke
				Defile app-name in application.props and define Eureke server url
					spring.application.name=limits-msg-service
					eureka.client.service-url.defaultZone=http://localhost:8761/eureka
				Make Service discoverable using anotation
					@EnableDiscoveryClient -->Application.java

		8. Now go and check in Eureka Server localhost:8761 you will be able to see the app

		9. Run more than one instance of 05_limits_msg_provider_eureka and call http://localhost:8300/limits 
			now ribben dinamically get the list of instance from Eureka we dont need to hardcode.
			
3. Resilience4j
---------------
	https://resilience4j.readme.io/docs/getting-started-3
