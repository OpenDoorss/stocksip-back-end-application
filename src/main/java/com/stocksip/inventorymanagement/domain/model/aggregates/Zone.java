package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.entities.ProductZone;
import com.stocksip.inventorymanagement.domain.model.valueobjects.Temperature;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long zoneId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minTemperature", column = @Column(name = "min_temperature", nullable = false)),
            @AttributeOverride(name = "maxTemperature", column = @Column(name = "max_temperature", nullable = false))
    })
    private Temperature temperature;

    private String Type;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductZone> productZones = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    protected Zone() {}
}
