package com.stocksip.inventorymanagement.infrastructure.storage.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String uploadImage(MultipartFile file) throws IOException;
}
