package com.shan.aidoc.userservice.service;

import com.shan.aidoc.userservice.dto.CreateUserRequest;
import com.shan.aidoc.userservice.dto.UpdateUserRequest;
import com.shan.aidoc.userservice.dto.UserResponse;
import com.shan.aidoc.userservice.entity.User;
import com.shan.aidoc.userservice.exception.DuplicateUserException;
import com.shan.aidoc.userservice.exception.UserNotFoundException;
import com.shan.aidoc.userservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new DuplicateUserException("Email already exists");
        }

        User user = new User(
                UUID.randomUUID(),
                request.firstName(),
                request.lastName(),
                request.email()
        );
        User savedUser =  userRepository.save(user);

        return toResponse(savedUser);
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users.map(this::toResponse);
    }

    public UserResponse getUserById(UUID id) {
        User userResp = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));

        return toResponse(userResp);
    }

    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id " + id + " not found"));

        if(!existingUser.getEmail().equalsIgnoreCase(request.email())) {
            if(userRepository.existsByEmail(request.email())) {
                throw new DuplicateUserException("Email already exists");
            }
        }

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

    public UserResponse searchByEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        return toResponse(user);
    }

    public Page<UserResponse> searchByFirstName(String firstName, Pageable pageable) {
        Page<User> users = userRepository.findByFirstNameContainingIgnoreCase(firstName, pageable);

        return users.map(this::toResponse);
    }
}