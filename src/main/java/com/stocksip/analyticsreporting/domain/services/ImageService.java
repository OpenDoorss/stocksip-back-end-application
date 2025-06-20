package com.stocksip.analyticsreporting.domain.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String generateImage(String nameId);
    String uploadImage(MultipartFile file) throws IOException;
    void deleteImage(String nameId);
}