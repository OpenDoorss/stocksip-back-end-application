package com.stocksip.inventorymanagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateCareGuideWithoutProductResource(
    @NotBlank
    @Schema(description = "Name of the care guide", example = "Basic Plant Care")
    String guideName,
    
    @NotBlank
    @Schema(description = "Type of the care guide", example = "PLANT_CARE")
    String type,
    
    @NotBlank
    @Schema(description = "Detailed care instructions", example = "Water once a week")
    String description,
    
    @Schema(description = "URL of the care guide image", example = "https://example.com/image.jpg")
    String imageUrl
) {}