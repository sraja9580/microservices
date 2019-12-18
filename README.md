

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
	
	Circuit Breaker
	---------------
		CLOSED 		-> All fine service provider is up and running
		OPEN		-> Provider is down
		HALF-OPEN	-> Some hits are success some are failure		
	
	Create Client (07_limits_service_resilience4j)
	----------------------------------------------

		Circuit Breaker
		---------------
			1. Create service with following starter
			   Spring Web
			   Feign client		 
			   lombok
			   Resilience4j spring boot starter (it intenally requires AOP and ACTUATOR)
				io.github.resilience4j:resilience4j-spring-boot2	   


			2. Provide endpoint for /limits
				return min,max,dummy msg
				Verify the flow

			3. Write proxy client for 01_limits_msg_provider 

				@FeignClient(name = "limits-msg-provider",url = "localhost:8000")
				public interface LimitsMsgProviderFeignProxyClient {
					@GetMapping("/getmsg")
					public String getMsg();
				}

			4. Enable Feign clients in Boot App java file
				@EnableFeignClients(basePackages ="com.practice.raja.microservice.limits.proxyclient" )

			5. Create Service Layer to handle service call LimitsResilienceImpl

				*Autowire proxy client in controller
				*create getMsg -> call service and return msg msgProviderProxy.getMsg()
				*create getFallbackMsg(Throwable throwable) -> retun static string
					it will be used in circuit breaker when need to fall back

			6. Autowire LimitsResilienceImpl in controller	

				*Get message from 01_limits_msg_provider by calling LimitsResilienceImpl-> getMsg
				*Verify the flow

			7. Configure CircuitBreaker in Service Layer (LimitsResilienceImpl)

				*add @CircuitBreaker(name = "getMsgCrkBrkr", fallbackMethod = "getFallbackMsg") on top off get Msg method.
				now if the msg provider service is down fallbackMethod method will be called

			8.Custom Circuit Breaker config
			-------------------------------
				Above will work as per default config, we can customize Circuit Breaker configuration as per our need.
			    * it is better to use yaml file instead of .properties so change the file extention

				application.yml
				---------------
				spring:
				  application:
					name: limits-service

				server:
				  port: 8300 

				resilience4j:
				  circuitbreaker:
					instances:
					  getMsgCrkBrkr: # it is the name given in Circuit Breaker annotaion (@CircuitBreaker(name = "getMsgCrkBrkr", fallbackMethod = "getFallbackMsg"))
						register-health-indicator: true
						ring-buffer-size-in-closed-state: 5
						ring-buffer-size-in-half-open-state: 3
						wait-duration-in-open-state: 10s
						failure-rate-threshold: 10          

			9. Actutator CircuitBreaker Configuration
			-----------------------------------------
				*BY DOING BELOW CONFIGURATION WE CAN SEE CIRCUIT BREAKER STATE AND OTHER DETAILS IN ACTUTATOR HEALTH PAGE

				management.endpoint.health.show-details: always
				management.endpoints.web.exposure.include: "*"  
				management.health.circuitbreakers.enabled: true

				check CircuitBreaker status in health url
				http://localhost:8300/actuator/health
