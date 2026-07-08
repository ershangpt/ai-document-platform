package com.shan.aidoc.userservice.dto;

public record HealthResponse(
        String service,
        String status,
        String version,
        String environment,
        String timestamp
) {
}