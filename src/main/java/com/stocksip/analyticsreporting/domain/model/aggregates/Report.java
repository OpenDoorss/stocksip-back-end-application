package com.stocksip.analyticsreporting.domain.model.aggregates;

import com.stocksip.analyticsreporting.domain.model.commands.CreateReportCommand;
import com.stocksip.analyticsreporting.domain.model.valueobjects.ProductId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

/**
 * Report Aggregate Root
 *
 * @summary
 * The Report class is an aggregate root that represents a report in the analytics reporting system.
 * It is responsible for handling various commands related to reports.
 *
 * @since 1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Report extends AbstractAggregateRoot<Report> {
    /**
     * The unique identifier of the report.
     * @type Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    /**
     * The name of the products.
     * @type String
     */
    @Column(nullable = false)
    @Embedded
    @Getter
    private ProductId productId;
    /**
     * The type of the report.
     * @type String
     */
    @Column(nullable = false)
    @Getter
    private String type;
    /**
     * The price of the product.
     * @type double
     */
    @Column(nullable = false)
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Getter
    @Setter
    private double price;
    /**
     * The amount of the product sold.
     * @type double
     */
    @Column(nullable = false)
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Getter
    @Setter
    private double amount;
    /**
     * The date from which the report is generated.
     * @type Date
     */
    @Column(nullable = false, updatable = false)
    @Getter
    private Date reportDate;
    /**
     * The lost amount of the product.
     * @type double
     */
    @Column(nullable = false)
    @Getter
    @Setter
    private double lostAmount;

    protected Report(){}
    /**
     * @summary Constructor.
     * This a create new Report instance based on the CreateReportCommand command.
     * @param command - The CreateReportCommand command
     */
    public Report(CreateReportCommand command) {
        Long productIdValue = Long.parseLong(command.productName());
        this.productId = new ProductId(productIdValue);
        this.type = command.type();
        this.price = command.price();
        this.amount = command.amount();
        this.reportDate = command.reportDate();
        this.lostAmount = command.lostAmount();
        
        if (this.price < 0) {
            throw new IllegalArgumentException("The price not can be negative");
        }
        if (this.amount < 0) {
            throw new IllegalArgumentException("The amount not can be negative");
        }
    }
    /**
     * Updates the report information with the provided values.
     * Only non-null and valid values will be updated.
     *
     * @param productId The new product ID (optional)
     * @param type The new report type (optional)
     * @param price The new unit price (must be non-negative if provided)
     * @param amount The new quantity (must be non-negative if provided)
     * @param reportDate The new report date (optional)
     * @param lostAmount The new lost amount (must be non-negative if provided)
     * @return The updated report instance
     */
    public Report updateInformation(String productId, String type, double price, double amount, Date reportDate, double lostAmount) {
        if(productId!=null && !productId.isBlank()){
            Long productIdValue = Long.parseLong(productId);
            this.productId = new ProductId(productIdValue);
        }
        if(type!=null && !type.isBlank()){
            this.type=type;
        }
        if(price>=0){
            this.price=price;
        } else {
            throw new IllegalArgumentException("The price not can be negative");
        }
        if(amount>=0){
            this.amount=amount;
        } else {
            throw new IllegalArgumentException("The amount not can be negative");
        }
        if(reportDate!=null){
            this.reportDate=reportDate;
        }
        if(lostAmount>=0){
            this.lostAmount=lostAmount;
        } else {
            throw new IllegalArgumentException("The lost amount not can be negative");
        }
        return this;
    }
}

