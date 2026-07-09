package com.shan.aidoc.userservice.dto;

import jakarta.validation.constraints.*;

public record CreateUserRequest (
        @NotBlank(message = "First name is required")
        @Size(min = 2, max=50, message = "First name must be between 2 and 50 characters")
        String firstName,
        @NotBlank(message = "Last name is required")
        @Size(min = 2, max=50, message = "Last name must be between 2 and 50 characters")
        String lastName,
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email
){
}
