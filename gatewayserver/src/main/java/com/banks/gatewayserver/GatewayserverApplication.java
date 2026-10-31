package com.banks.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.cloud.gateway.route.RouteLocator;import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator bankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p->p
						.path("/talhabank/accounts/**")
						.filters(f->f.rewritePath("/talhabank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-time", LocalTime.now().toString())
								.circuitBreaker(config->config.setName("acccountsCircuitbreaker")
										.setFallbackUri("forward:/contactSupport")))
						.uri("lb://ACCOUNTS"))
				.route(p->p
				.path("/talhabank/loans/**")
				.filters(f->f.rewritePath("/talhabank/loans/(?<segment>.*)","/${segment}"))
				.uri("lb://LOANS"))
				.route(p->p
						.path("/talhabank/cards/**")
						.filters(f->f.rewritePath("/talhabank/cards/(?<segment>.*)","/${segment}"))
						.uri("lb://CARDS"))
				.build();

	}

}
