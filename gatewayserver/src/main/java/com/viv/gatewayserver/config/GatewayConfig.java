package com.viv.gatewayserver.config;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Configuration
public class GatewayConfig {

        @Bean
        public RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder) {
                return routeLocatorBuilder.routes()
                                .route(p -> p
                                                .path("/viv/accounts/**")
                                                .filters(f -> f.rewritePath("/viv/accounts/(?<segment>.*)",
                                                                "/${segment}")
                                                                .addResponseHeader("X-Response-Time",
                                                                                LocalDateTime.now().toString())
                                                                .circuitBreaker(config -> config
                                                                                .setName("accountsCB")
                                                                                .setFallbackUri("forward:/contactSupport")))
                                                .uri("lb://ACCOUNTS"))
                                .route(p -> p
                                                .path("/viv/loans/**")
                                                .filters(f -> f.rewritePath("/viv/loans/(?<segment>.*)", "/${segment}")
                                                                .addResponseHeader("X-Response-Time",
                                                                                LocalDateTime.now().toString())
                                                                .retry(retryConfig -> retryConfig
                                                                                .setRetries(3)
                                                                                .setMethods(HttpMethod.GET)
                                                                                .setBackoff(Duration.ofMillis(1000),
                                                                                                Duration.ofMillis(2000), 2,
                                                                                                false)
                                                                                .setStatuses(HttpStatus.SERVICE_UNAVAILABLE)

                                                                )

                                                )
                                                .uri("lb://LOANS"))
                                .route(p -> p
                                                .path("/viv/cards/**")
                                                .filters(f -> f.rewritePath("/viv/cards/(?<segment>.*)", "/${segment}")
                                                                .addResponseHeader("X-Response-Time",
                                                                                LocalDateTime.now().toString()))
                                                .uri("lb://CARDS"))
                                .build();
        }

}
