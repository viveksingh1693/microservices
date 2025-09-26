package com.viv.gatewayserver.config;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import reactor.core.publisher.Mono;

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
                                                                                                Duration.ofMillis(2000),
                                                                                                2,
                                                                                                false)
                                                                                .setStatuses(HttpStatus.SERVICE_UNAVAILABLE)

                                                                )

                                                )
                                                .uri("lb://LOANS"))
                                .route(p -> p
                                                .path("/viv/cards/**")
                                                .filters(f -> f.rewritePath("/viv/cards/(?<segment>.*)", "/${segment}")
                                                                .addResponseHeader("X-Response-Time",
                                                                                LocalDateTime.now().toString())
                                                                .requestRateLimiter(config -> config
                                                                                .setRateLimiter(redisRateLimiterConfig())
                                                                                .setKeyResolver(userKeyResolver())))

                                                .uri("lb://CARDS"))
                                .build();
        }

        @Bean
        public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
                return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                                .timeLimiterConfig(io.github.resilience4j.timelimiter.TimeLimiterConfig.custom()
                                                .timeoutDuration(Duration.ofSeconds(2)).build())
                                .build());
        }

        @Bean
        public RedisRateLimiter redisRateLimiterConfig() {
                // replenishRate = number of requests added to the bucket per second
                // burstCapacity = maximum number of requests allowed to be accumulated in the
                // bucket
                return new RedisRateLimiter(1, 1, 1);
        }

        @Bean
        KeyResolver userKeyResolver() {
                return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
                                .defaultIfEmpty("anonymous");
        }
}
