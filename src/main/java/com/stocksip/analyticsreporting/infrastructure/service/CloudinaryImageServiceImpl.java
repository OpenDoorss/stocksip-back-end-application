package com.stocksip.analyticsreporting.infrastructure.service;

import com.cloudinary.Cloudinary;
import com.stocksip.analyticsreporting.domain.services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageServiceImpl implements ImageService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryImageServiceImpl.class);
    private final Cloudinary cloudinary;

    public CloudinaryImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    @Override
    public String generateImage(String nameId){
        return cloudinary.url().generate(nameId);
    }
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        try {
            // Convert MultipartFile to byte array
            byte[] fileBytes = file.getBytes();
            
            // Upload to Cloudinary
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                fileBytes,
                ObjectUtils.asMap(
                    "resource_type", "auto"
                )
            );
            
            // Return the secure URL of the uploaded file
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            logger.error("Error uploading file to Cloudinary", e);
            throw new IOException("Error uploading file to Cloudinary: " + e.getMessage(), e);
        }
    }
    @Override
    public void deleteImage(String nameId) {
        try {
            cloudinary.uploader().destroy(nameId, null);
        } catch (IOException e) {
            // Log the error and rethrow as a RuntimeException to maintain the method signature
            throw new RuntimeException("Failed to delete image with id: " + nameId, e);
        }
    }
}
