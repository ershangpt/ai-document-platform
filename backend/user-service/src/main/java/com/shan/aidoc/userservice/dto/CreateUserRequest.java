package com.shan.aidoc.userservice.dto;

public record CreateUserRequest (
        String firstName,
        String lastName,
        String email
){
}
