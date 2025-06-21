package com.stocksip.inventorymanagement.domain.model.entities;

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
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @NotNull
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

    protected CareGuide(){}

    /**
     * @summary Constructor.
     * This a create new CareGuide instance based on the CreateCareGuideCommand command.
     * @param command - The CreateCareGuideCommand command
     */
    public CareGuide(Product product, Warehouse warehouse, String guideName, String type, String description) {
        this.product = product;
        this.warehouse = warehouse;
        this.guideName = guideName;
        this.type = type;
        this.description = description;
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
}
