package com.stocksip.inventorymanagement.application.internal.queryservices;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.queries.*;
import com.stocksip.inventorymanagement.domain.services.ProductQueryService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the ProductQueryService interface to provide methods for querying product data.
 *
 * @summary
 * Implementation of the ProductQueryService interface for handling queries related to products.
 *
 * @since 1.0.0
 */
@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    /**
     * Repository for accessing product data.
     */
    private final ProductRepository productRepository;

    /**
     * Dependency injection constructor for ProductQueryServiceImpl.
     * @param productRepository the repository for accessing product data
     */
    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> handle(GetAllProductsByAccountIdQuery query) {
        return productRepository.findAllProductsByAccountId(query.accountId());
    }

    @Override
    public List<Product> handle(GetAllProductsByProviderIdAndWarehouseIdQuery query) {
        return productRepository.findAllProductsByWarehouseIdAndAccountId(query.warehouseId(), query.accountId().accountId());
    }

    @Override
    public List<Product> handle(GetAllProductsByWarehouseIdQuery query) {
        return productRepository.findAllProductsByWarehouseId(query.warehouseId());
    }

    @Override
    public Optional<Product> handle(GetProductByFullNameAndWarehouseIdQuery query) {
        return productRepository.findProductByBrandNameAndLiquorTypeAndProductNameAndWarehouseId(query.warehouseId(), query.brandName(), query.liquorType(), query.additionalName());
    }

    @Override
    public Optional<Product> handle(GetProductByIdAndWarehouseIdAndBestBeforeDateQuery query) {
        return productRepository.findProductByIdAndWarehouseIdAndBestBeforeDate(query.productId(), query.warehouseId(), query.bestBeforeDate());
    }

    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.productId());
    }
}
