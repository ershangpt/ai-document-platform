package com.shan.aidoc.documentservice.client;

import com.shan.aidoc.documentservice.dto.UserResponse;
import com.shan.aidoc.documentservice.exception.UserNotFoundException;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
public class UserServiceClient {

    private final WebClient webClient;

    UserServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://USER-SERVICE").build();
    }

    @Retry(name = "userServiceRetry", fallbackMethod = "fallback")
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
