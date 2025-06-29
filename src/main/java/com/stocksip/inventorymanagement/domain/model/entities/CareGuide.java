package com.stocksip.inventorymanagement.domain.model.entities;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ImageUrl;
import com.stocksip.shared.domain.model.entities.AuditableModel;
import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Entity
public class CareGuide extends AuditableModel {

    @ManyToOne
    @JoinColumn(name = "productId", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouseId", nullable = true)
    private Warehouse warehouse;

    /**
     * The name of the care guide.
     * @guideName String
     */
    @Setter
    @Getter
    private String guideName;
    /**
     * The type of the care guide.
     * @type String
     */
    @Setter
    @Getter
    private String type;
    /**
     * The description of the care guide.
     * @description description
     */
    @Setter
    @Getter
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageUrl", column = @Column(name = "image_url"))
    })
    private ImageUrl imageUrl;

    protected CareGuide(){}

    /**
     * @summary Constructor.
     * It creates a new CareGuide instance based on the provided product, warehouse, guide name, type, and description.
     */
    /**
     * Creates a new CareGuide instance with the specified details.
     *
     * @param product the associated product (can be null)
     * @param warehouse the associated warehouse (can be null)
     * @param guideName the name of the care guide (required)
     * @param type the type of the care guide (required)
     * @param description the description of the care guide (required)
     * @param imageUrl the URL of the care guide image (can be null, will use default if null or blank)
     * @throws IllegalArgumentException if guideName, type, or description is null or blank
     */
    public CareGuide(Product product, Warehouse warehouse, String guideName, String type, String description, String imageUrl) {
        if (guideName == null || guideName.isBlank()) {
            throw new IllegalArgumentException("Guide name cannot be null or blank");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        
        this.product = product;
        this.warehouse = warehouse;
        this.guideName = guideName;
        this.type = type;
        this.description = description;
        this.imageUrl = this.setDefaultImageUrlIfNotProvided(imageUrl);
    }
    /**
     * Updates the care guide information with the provided values.
     * Only non-null and non-empty values will be updated.
     *
     * @param guideName The new name for the care guide (optional)
     * @param type The new type/category for the care guide (optional)
     * @param description The new description/content for the care guide (optional)
     * @return The updated care guide instance
     */
    public CareGuide updateInformation(String guideName, String type, String description) {
        this.guideName = guideName;
        this.type = type;
        this.description = description;
        return this;
    }
    private ImageUrl setDefaultImageUrlIfNotProvided(String imageUrl) {
        return imageUrl == null || imageUrl.isBlank()
                ? ImageUrl.defaultImageUrl()
                : new ImageUrl(imageUrl);
    }
    
    /**
     * Sets the image URL for this care guide.
     * If the provided URL is null or blank, a default image URL will be used.
     *
     * @param imageUrl The URL of the image to set, or null/blank to use default
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = setDefaultImageUrlIfNotProvided(imageUrl);
    }
}
