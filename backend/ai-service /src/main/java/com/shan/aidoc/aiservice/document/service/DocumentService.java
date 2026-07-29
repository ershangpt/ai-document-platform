package com.shan.aidoc.aiservice.document.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    void upload(MultipartFile file);

}