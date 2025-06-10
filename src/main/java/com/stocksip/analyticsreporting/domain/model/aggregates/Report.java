package com.stocksip.analyticsreporting.domain.model.aggregates;

import com.stocksip.analyticsreporting.domain.model.commands.CreateReportCommand;
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
    @Setter
    @Getter
    private String productName;
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
        this.productName = command.productName();
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
}

