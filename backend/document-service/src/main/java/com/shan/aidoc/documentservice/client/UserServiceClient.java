package com.shan.aidoc.documentservice.client;

import com.shan.aidoc.documentservice.dto.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class UserServiceClient {

    private final WebClient webClient;

    UserServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://USER-SERVICE").build();
    }

    @RateLimiter(name = "userServiceRateLimiter", fallbackMethod = "fallback")
    @Retry(name = "userServiceRetry", fallbackMethod = "fallback")
    @CircuitBreaker(name = "userServiceCircuitBreaker")
    public UserResponse getUserById(UUID id) {
        System.out.println("Calling User Service");

        return webClient.get()
                .uri("/api/v1/users/{id}", id)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
    }

    private UserResponse fallback(UUID id, Exception ex) {
        System.out.println("Fallback executed");
        throw new RuntimeException(ex);
    }

}
