package com.shan.aidoc.userservice.controller;

import com.shan.aidoc.userservice.dto.CreateUserRequest;
import com.shan.aidoc.userservice.dto.DocumentResponse;
import com.shan.aidoc.userservice.dto.UpdateUserRequest;
import com.shan.aidoc.userservice.dto.UserResponse;
import com.shan.aidoc.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/users")
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {

        UserResponse user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
        UserResponse user = userService.updateUser(id, request);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/search/email")
    public UserResponse searchByEmail(@RequestParam String email) {
        return userService.searchByEmail(email);
    }

    @GetMapping("/users/search/name")
    public Page<UserResponse> searchByName(@RequestParam String firstName, Pageable pageable) {
        return userService.searchByFirstName(firstName, pageable);
    }

    @GetMapping("/users/{id}/documents")
    public ResponseEntity<List<DocumentResponse>> getUserDocuments(@PathVariable UUID id) {

        List<DocumentResponse> docs = userService.getUserDocumentsById(id);

        return ResponseEntity.ok(docs);
    }
}
