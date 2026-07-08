package com.shan.aidoc.userservice.dto;

public record InfoResponse (
        String application,
        String version,
        String developer,
        String javaVersion,
        String framework
){

}
