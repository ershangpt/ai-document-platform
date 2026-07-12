package com.shan.aidoc.userservice.service;

import com.shan.aidoc.userservice.dto.CreateUserRequest;
import com.shan.aidoc.userservice.dto.UserResponse;
import com.shan.aidoc.userservice.entity.User;
import com.shan.aidoc.userservice.exception.UserNotFoundException;
import com.shan.aidoc.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest request) {
        User user = new User(
                UUID.randomUUID(),
                request.firstName(),
                request.lastName(),
                request.email()
        );
        User savedUser =  userRepository.save(user);

        return toResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(this::toResponse).toList();
    }

    public UserResponse getUserById(UUID id) {
        User userResp = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));

        return toResponse(userResp);
    }

    public UserResponse updateUser(UUID id, CreateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id " + id + " not found"));

        existingUser.setFirstName(request.firstName());
        existingUser.setLastName(request.lastName());
        existingUser.setEmail(request.email());

        User updatedUser = userRepository.save(existingUser);

        return toResponse(updatedUser);
    }

    public void deleteUser(UUID id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id " + id + " not found"));

        userRepository.delete(existingUser);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}