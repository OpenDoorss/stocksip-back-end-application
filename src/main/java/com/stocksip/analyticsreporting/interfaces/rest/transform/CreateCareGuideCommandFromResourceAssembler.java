package com.stocksip.analyticsreporting.interfaces.rest.transform;

import com.stocksip.analyticsreporting.domain.model.commands.CreateCareGuideCommand;
import com.stocksip.analyticsreporting.domain.services.ImageService;
import com.stocksip.analyticsreporting.interfaces.rest.resources.CreateCareGuideResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * @summary 
 * This class is used to convert CreateCareGuideResource to CreateCareGuideCommand.
 */
@Component
public class CreateCareGuideCommandFromResourceAssembler {
    
    private final ImageService imageService;
    
    public CreateCareGuideCommandFromResourceAssembler(ImageService imageService) {
        this.imageService = imageService;
    }
    /**
     * Converts a CreateCareGuideResource to a CreateCareGuideCommand.
     *
     * @param resource The CreateCareGuideResource to convert.
     * @return The CreateCareGuideCommand.
     */
    public CreateCareGuideCommand toCommandFromResource(CreateCareGuideResource resource) {
        String imageUrl = "";
        MultipartFile imageFile = resource.image();
        
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imageUrl = imageService.uploadImage(imageFile);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }
        
        return new CreateCareGuideCommand(
            resource.guideName(),
            resource.type(),
            resource.description(),
            imageUrl
        );
    }
}
