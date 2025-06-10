package com.stocksip.analyticsreporting.domain.model.aggregates;

import com.stocksip.analyticsreporting.domain.model.commands.CreateCareGuideCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
}
