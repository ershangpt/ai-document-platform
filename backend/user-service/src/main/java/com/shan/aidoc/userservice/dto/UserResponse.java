package com.shan.aidoc.userservice.dto;

import com.shan.aidoc.userservice.entity.Document;

import java.util.List;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
}
