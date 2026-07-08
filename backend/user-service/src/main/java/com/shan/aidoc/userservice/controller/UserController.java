package com.shan.aidoc.userservice.controller;

import com.shan.aidoc.userservice.dto.CreateUserRequest;
import com.shan.aidoc.userservice.entity.User;
import com.shan.aidoc.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest body) {
        User user = userService.createUser(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
