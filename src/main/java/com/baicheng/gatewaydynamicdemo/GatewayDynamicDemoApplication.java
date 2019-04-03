package com.baicheng.gatewaydynamicdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayDynamicDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayDynamicDemoApplication.class, args);
	}

	private static final String HTTPBIN_URI = "http://localhost:8888";
//
//	@Bean
//	public RouteLocator routeLocator(RouteLocatorBuilder builder){
//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//						.uri(HTTPBIN_URI)
//				)
//				.build();
//	}

}
