package com.stocksip.inventorymanagement.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

/**
 * Command to upload an image.
 *
 * @summary
 * This command encapsulates the necessary information to upload an image file.
 * It includes the image file itself.
 *
 * @since 1.0.0
 */
public record UploadImageCommand(MultipartFile imageFile) {}
