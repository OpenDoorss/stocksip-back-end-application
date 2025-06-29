package com.stocksip.inventorymanagement.application.internal.outboundservices.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    String UploadImage(MultipartFile imageFile);

    boolean DeleteImage(String imageUrl);
}
