package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.commands.UploadImageCommand;
import org.springframework.web.multipart.MultipartFile;

/**
 * Assembler for converting UploadImageResource to UploadImageCommand.
 * This class provides a method to transform the resource into a command object.
 *
 * @since 1.0.0
 */
public class UploadImageCommandFromResource {

    /**
     * Converts a MultipartFile resource to an UploadImageCommand.
     *
     * @param resource the MultipartFile resource containing the image to upload
     * @return an UploadImageCommand containing the image data
     * @throws IllegalArgumentException if the resource is null
     * @since 1.0.0
     */
    public static UploadImageCommand toCommandFromResource(MultipartFile resource) {
        return new UploadImageCommand(resource);
    }
}
