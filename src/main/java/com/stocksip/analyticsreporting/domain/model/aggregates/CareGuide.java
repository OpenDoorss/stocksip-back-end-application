package com.stocksip.analyticsreporting.domain.model.aggregates;

import com.stocksip.analyticsreporting.domain.model.commands.CreateCareGuideCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Care guide Aggregate Root
 *
 * @summary
 * The CareGuide class is an aggregate root that represents a report in the analytics reporting system.
 * It is responsible for handling various commands related to reports.
 *
 * @since 1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CareGuide extends AbstractAggregateRoot<CareGuide> {
    /**
     * The unique identifier of the care guide.
     * @type Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    /**
     * The name of the care guide.
     * @guideName String
     */
    @Column(nullable = false)
    @Setter
    @Getter
    private String guideName;

    /**
     * The type of the care guide.
     * @type String
     */
    @Column(nullable = false)
    @Getter
    private String type;

    /**
     * The description of the care guide.
     * @description description
     */
    @Column(nullable = false)
    @Getter
    private String description;

    protected CareGuide(){}

    /**
     * @summary Constructor.
     * This a create new CareGuide instance based on the CreateCareGuideCommand command.
     * @param command - The CreateCareGuideCommand command
     */
    public CareGuide(CreateCareGuideCommand command) {
        this.guideName = command.guideName();
        this.type = command.type();
        this.description = command.description();
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
        if (guideName != null && !guideName.isBlank()) {
            this.guideName = guideName;
        }
        if (type != null && !type.isBlank()) {
            this.type = type;
        }
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
        return this;
    }
}
