package com.stocksip.inventorymanagement.infrastructure.filestorage.cloudinary.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.stocksip.inventorymanagement.application.internal.outboundservices.cloudinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * This service is used to upload and delete images from Cloudinary.
 * It provides methods to upload an image and delete an image based on its URL.
 *
 * @summary
 * Service that provides methods for uploading and deleting images in Cloudinary.
 *
 * @since 1.0
 */
@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    private CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * This method uploads an image to Cloudinary.
     * @param imageFile The image file to be uploaded.
     * @return The URL of the uploaded image.
     */
    @Override
    public String UploadImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty.");
        }

        String publicId = UUID.randomUUID().toString();

        Map<?, ?> uploadParams = ObjectUtils.asMap(
                "public_id", publicId,
                "folder", "StockSip-OS-inventories"
        );

        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), uploadParams);

            if (uploadResult.containsKey("secure_url")) {
                return uploadResult.get("secure_url").toString();
            }

            throw new RuntimeException("Upload failed: " + uploadResult.get("error"));

        } catch (IOException e) {
            throw new RuntimeException("Failed to process image file", e);
        }
    }

    /**
     * This method deletes an image from Cloudinary based on its URL.
     * It checks if the image is protected and does not delete it if it is.
     * @param imageUrl The URL of the image to be deleted.
     * @return True if the image was successfully deleted, false if it was protected.
     */
    @Override
    public boolean DeleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty.");
        }

        // Imágenes protegidas que no deben eliminarse
        Set<String> protectedImages = new HashSet<>(Arrays.asList(
                "default-warehouse_nwd0i7",
                "default-product_ssjni6"
        ));

        try {
            // Convertir string a URI para parsear el path
            URI uri = new URI(imageUrl);
            String[] parts = uri.getPath().split("/");

            if (parts.length < 3) {
                throw new IllegalArgumentException("Invalid Cloudinary URL format.");
            }

            // Obtener nombre del archivo con extensión (último segmento)
            String fileNameWithExtension = parts[parts.length - 1];

            // Validar que tenga extensión
            int dotIndex = fileNameWithExtension.lastIndexOf('.');
            if (dotIndex == -1) {
                throw new IllegalArgumentException("Image filename does not contain a valid extension.");
            }

            String fileName = fileNameWithExtension.substring(0, dotIndex); // sin extensión
            String folder = parts[parts.length - 2];
            String publicId = folder + "/" + fileName;

            // Validar si es una imagen protegida
            if (protectedImages.stream().anyMatch(protectedImg -> protectedImg.equalsIgnoreCase(fileName))) {
                System.out.println("Skipping deletion of protected image: " + fileName);
                return false;
            }

            // Eliminar la imagen en Cloudinary
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));

        } catch (URISyntaxException e) {
            System.out.println("⛳ Attempting to parse imageUrl: " + imageUrl);
            throw new IllegalArgumentException("Invalid URL format", e);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting image from Cloudinary", e);
        }
    }
}
