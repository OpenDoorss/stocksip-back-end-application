package com.stocksip.inventorymanagement.application.internal.commandservices;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.commands.CreateCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.entities.CareGuide;
import com.stocksip.inventorymanagement.domain.services.CareGuideCommandService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.CareGuideRepository;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.ProductRepository;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementation of the CareGuideCommandService interface.
 * Handles the creation, update, and deletion of care guides.
 */
@Service
public class CareGuideCommandServiceImpl implements CareGuideCommandService {

    private final CareGuideRepository careGuideRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public CareGuideCommandServiceImpl(
            CareGuideRepository careGuideRepository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository) {
        this.careGuideRepository = careGuideRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    @Transactional
    public Optional<CareGuide> handle(CreateCareGuideCommand command) {
        try {
            // Get the first available product and warehouse if they exist
            // In a production environment, you might want to make these required
            // or handle their absence differently
            Product product = productRepository.findAll().stream().findFirst().orElse(null);
            Warehouse warehouse = warehouseRepository.findAll().stream().findFirst().orElse(null);

            // Create the care guide with or without product/warehouse
            CareGuide careGuide = new CareGuide(
                product,
                warehouse,
                command.guideName(),
                command.type(),
                command.description(),
                command.imageUrl()
            );
            
            return Optional.of(careGuideRepository.save(careGuide));
            
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create care guide: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<CareGuide> handle(UpdateCareGuideCommand command) {
        return careGuideRepository.findById(command.id())
            .map(careGuide -> {
                careGuide.updateInformation(
                    command.guideName(),
                    command.type(),
                    command.description()
                );
                // Update the image URL if provided
                if (command.imageUrl() != null && !command.imageUrl().isBlank()) {
                    careGuide.setImageUrl(command.imageUrl());
                }
                return careGuideRepository.save(careGuide);
            });
    }

    @Override
    @Transactional
    public void handle(DeleteCareGuideCommand command) {
        careGuideRepository.findById(command.id())
            .ifPresentOrElse(
                careGuide -> careGuideRepository.delete(careGuide),
                () -> { throw new NoSuchElementException("Care guide not found with id: " + command.id()); }
            );
    }
}
