package com.shan.aidoc.documentservice.client;

import com.shan.aidoc.documentservice.dto.UserResponse;
import com.shan.aidoc.documentservice.exception.UserNotFoundException;
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

    public UserResponse getUserById(UUID id) {
        try {
            return webClient
                    .get()
                    .uri("/api/v1/users/{id}", id)
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

}
