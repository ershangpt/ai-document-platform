package com.shan.aidoc.userservice.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;

import com.shan.aidoc.userservice.dto.HealthResponse;
import com.shan.aidoc.userservice.dto.InfoResponse;
import com.shan.aidoc.userservice.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/health")
    public HealthResponse health() {
        return healthService.getHealth();
    }

    @GetMapping("/info")
    public InfoResponse info() {
        return healthService.getInfo();
    }

}