package com.stocksip.shared.infrastructure.cloudstorage.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {


    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        try {

            if (file == null || file.isEmpty()) {
                return null;
            }

            List<String> allowedContentTypes = List.of(
                    "image/jpeg",
                    "image/jpg",
                    "image/png",
                    "image/avif",
                    "image/webp"
            );

            String contentType = file.getContentType();
            if (!allowedContentTypes.contains(contentType)) {
                throw new IllegalArgumentException("Unsupported file type: " + contentType);
            }

            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", "careguides",
                    "public_id", UUID.randomUUID().toString(),
                    "resource_type", "auto",
                    "quality", "auto:good"
            );

            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Cloudinary", e);
        }
    }
}