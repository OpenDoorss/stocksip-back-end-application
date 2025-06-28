package com.stocksip.inventorymanagement.application.internal.outboundservices.cloudinary;

import org.springframework.web.multipart.MultipartFile;

/**
 * This service is used to interact with Cloudinary for image upload and deletion.
 * It provides methods to upload images and delete images from Cloudinary.
 *
 * @summary
 * Service that provides methods for uploading and deleting images in Cloudinary.
 *
 * @since 1.0
 */
public interface CloudinaryService {

    /**
     * Uploads an image to Cloudinary.
     *
     * @param imageFile The image file to be uploaded.
     * @return The URL of the uploaded image.
     */
    String UploadImage(MultipartFile imageFile);

    /**
     * Deletes an image from Cloudinary.
     *
     * @param imageUrl The URL of the image to be deleted.
     * @return true if the image was successfully deleted, false otherwise.
     */
    Boolean deleteImage(String imageUrl);
}
