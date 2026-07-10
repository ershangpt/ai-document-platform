package com.shan.aidoc.userservice.service;

import com.shan.aidoc.userservice.dto.CreateUserRequest;
import com.shan.aidoc.userservice.entity.User;
import com.shan.aidoc.userservice.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public User createUser(CreateUserRequest request) {

        User user = new User(
                UUID.randomUUID(),
                request.firstName(),
                request.lastName(),
                request.email()
        );

        users.add(user);

        return user;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> getUserById(UUID id) {
        return users.stream()
                .filter(user -> user.id().equals(id))
                .findFirst();
    }

    public User updateUser(UUID id, CreateUserRequest request) {
        User existingUser = getUserById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));

        User user = new User(
                id,
                request.firstName(),
                request.lastName(),
                request.email()
        );
        int index = users.indexOf(existingUser);
        users.set(index, user);
        return user;
    }

    public void deleteUser(UUID id) {
        User existingUser = getUserById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));
        users.remove(existingUser);
    }
}