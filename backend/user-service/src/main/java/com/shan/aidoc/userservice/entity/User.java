package com.shan.aidoc.userservice.entity;

import java.util.UUID;

public record User(

        UUID id,

        String firstName,

        String lastName,

        String email

) {}