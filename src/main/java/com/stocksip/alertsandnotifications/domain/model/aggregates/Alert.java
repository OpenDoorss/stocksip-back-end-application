package com.stocksip.alertsandnotifications.domain.model.aggregates;

import com.stocksip.alertsandnotifications.domain.model.commands.CreateAlertCommand;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProductId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.AccountId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.WarehouseId;
import com.stocksip.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * This class represents an Alert aggregate in the domain model.
 *
 * @summary
 * Alert aggregate root that represents an alert in the system with properties
 * like title, message, severity, type, and associated entities.
 *
 * @since 1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Alert extends AuditableAbstractAggregateRoot<Alert> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long AlertId;

    /**
     * The title of the alert, providing a brief description of the alert.
     */
    @Column(nullable = false)
    @Getter
    private String title;
    
    /**
     * The message of the alert, providing detailed information about the alert.
     */
    @Column(nullable = false)
    @Getter
    private String message;

    /**
     * The severity of the alert, indicating its importance or urgency.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private SeverityTypes severity;

    /**
     * The type of the alert, categorizing it into a specific type.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private AlertTypes type;

    /**
     * The date and time when the alert was resolved, if applicable.
     */
    @Column
    @Getter
    private Date resolvedAt;

    /**
     * The state of the alert, indicating whether it is active, read, or resolved.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private AlertState state = AlertState.ACTIVE;
    
    /**
     * The unique identifier of the account associated with the alert.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountId", column = @Column(name = "account_id", nullable = false))
    })
    @Getter
    private AccountId accountId;
    
    /**
     * The unique identifier of the product associated with the alert.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false))
    })
    @Getter
    private ProductId productId;

    /**
     * The unique identifier of the warehouse associated with the alert.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "warehouseId", column = @Column(name = "warehouse_id", nullable = false))
    })
    @Getter
    private WarehouseId warehouseId;

    // Default constructor for JPA
    protected Alert() {}

    /**
     * Constructor for creating an Alert with all required parameters.
     *
     * @param title      The title of the alert.
     * @param message    The message of the alert.
     * @param severity   The severity of the alert.
     * @param type       The type of the alert.
     * @param accountId  The unique identifier of the account associated with the alert.
     * @param productId  The unique identifier of the product associated with the alert.
     * @param warehouseId The unique identifier of the warehouse associated with the alert.
     */
    public Alert(String title, String message, String severity, String type,
                 AccountId accountId, ProductId productId, WarehouseId warehouseId) {
        this.title = title;
        this.message = message;
        this.severity = SeverityTypes.valueOf(severity.toUpperCase());
        this.type = AlertTypes.valueOf(type.toUpperCase());
        this.accountId = accountId;
        this.productId = productId;
        this.warehouseId = warehouseId;
    }

    /**
     * Command handler constructor for the Alert class.
     *
     * @param command The command that contains the necessary information to create an alert.
     */
    public Alert(CreateAlertCommand command) {
        this(command.title(), command.message(), command.severity(), command.type(),
             command.accountId(), command.productId(), command.warehouseId());
    }
    
    /**
     * This method resolves the alert, changing its state to Read.
     */
    public void read() {
        this.state = AlertState.READ;
    }

    /**
     * Enum for alert severity types.
     */
    public enum SeverityTypes {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    /**
     * Enum for alert types.
     */
    public enum AlertTypes {
        STOCK_LOW, EXPIRATION_WARNING, SYSTEM_ALERT, MAINTENANCE
    }

    /**
     * Enum for alert states.
     */
    public enum AlertState {
        ACTIVE, READ, RESOLVED
    }
} 