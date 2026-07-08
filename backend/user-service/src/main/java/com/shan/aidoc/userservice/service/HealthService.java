package com.shan.aidoc.userservice.service;

import com.shan.aidoc.userservice.dto.HealthResponse;
import com.shan.aidoc.userservice.dto.InfoResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HealthService {

    public HealthResponse getHealth () {
        return new HealthResponse("User Service", "Running", "1.0.0", "Local", LocalDateTime.now().toString());
    }

    public InfoResponse getInfo() {
        return new InfoResponse("AI Document Platform", "1.0.0", "Shan", "21", "Spring Boot");
    }
}
