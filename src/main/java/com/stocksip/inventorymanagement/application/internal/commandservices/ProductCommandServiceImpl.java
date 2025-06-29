package com.stocksip.inventorymanagement.application.internal.commandservices;

import com.stocksip.inventorymanagement.application.internal.outboundservices.cloudinary.CloudinaryService;
import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.commands.CreateProductCommand;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteProductCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateProductCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateProductMinimumStockCommand;
import com.stocksip.inventorymanagement.domain.model.valueobjects.BrandName;
import com.stocksip.inventorymanagement.domain.model.valueobjects.LiquorType;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductName;
import com.stocksip.inventorymanagement.domain.services.ProductCommandService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.InventoryRepository;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ProductCommandServiceImpl
 *
 * @summary
 * ProductCommandServiceImpl is an implementation of the ProductCommandService interface.
 *
 * @since 1.0.0
 */
@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    /**
     * Repository for accessing product data.
     */
    private final ProductRepository productRepository;

    /**
     * Repository for accessing inventory data.
     */
    private final InventoryRepository inventoryRepository;

    private final CloudinaryService cloudinaryService;

    public ProductCommandServiceImpl(ProductRepository productRepository, InventoryRepository inventoryRepository, CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.cloudinaryService = cloudinaryService;
    }

    /**
     * Handles the command to update a product
     *
     * @param command The command containing the information to create a product.
     * @return The updated product.
     */
    @Override
    public Optional<Product> handle(UpdateProductCommand command) {

        var productToUpdate = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        String currentImageUrl = productToUpdate.getImageUrl().imageUrl();
        String imageUrl = currentImageUrl;

        if (command.image() != null && !command.image().isEmpty()) {
            cloudinaryService.deleteImage(currentImageUrl);
            imageUrl = cloudinaryService.UploadImage(command.image());
        }

        productToUpdate.updateInformation(
                command.unitPriceAmount(),
                command.minimumStock(),
                imageUrl
        );

        try {
            var updatedProduct = productRepository.save(productToUpdate);
            return Optional.of(updatedProduct);
        } catch (Exception e) {
            throw new RuntimeException("Error updating product: " + e.getMessage(), e);
        }
    }

    /**
     * Handles the command for creating a new product instance.
     *
     * @param command The command containing the details for creating a new product in a warehouse.
     * @return The created product.
     */
    @Override
    public Optional<Product> handle(CreateProductCommand command) {
        var targetBrandName = BrandName.valueOf(command.brandName());
        var targetLiquorType = LiquorType.valueOf(command.liquorType());

        if (productRepository.existsByBrandNameAndLiquorTypeAndProductName(targetBrandName,
                        targetLiquorType,
                        new ProductName(targetBrandName, targetLiquorType, command.additionalName()))) {
            throw new IllegalArgumentException("Product will full name given already exists.");
        }

        String imageUrl = command.image() != null ? cloudinaryService.UploadImage(command.image())
                : "https://res.cloudinary.com/deuy1pr9e/image/upload/v1751091989/default-product_ssjni6.jpg";

        var product = new Product(command, imageUrl);
        var createdProduct = productRepository.save(product);
        return Optional.of(createdProduct);
    }

    /**
     * Handles the command for updating the minimum stock level of a product.
     *
     * @param command The command containing the details to update the minimum stock level of the product.
     * @return The updated product.
     */
    @Override
    public Optional<Product> handle(UpdateProductMinimumStockCommand command) {
        var productToUpdate = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        productToUpdate.setMinimumStock(command.minimumStock());

        try {
            var updatedProduct = productRepository.save(productToUpdate);
            return Optional.of(updatedProduct);
        } catch (Exception e) {
            throw new RuntimeException("Error updating product: " + e.getMessage(), e);
        }
    }

    /**
     * Handles the command for deleting a product only when it's out of stock in all the warehouses.
     * If the product is out of stock in all the warehouses, then it will be deleted and also all the inventory objects related to that product.
     *
     * @param command The command containing the details for deleting a product
     */
    @Override
    public void handle(DeleteProductCommand command) {
        var productToDelete = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var count = 0;
        for (int i = 0; i < productToDelete.getInventories().size(); i++)
        {
            if (productToDelete.getInventories().get(i).getProductStock().getStock() == 0){
                count++;
            }
        }

        try {
            if (count == productToDelete.getInventories().size()) {

                String imageUrl = productRepository.findImageUrlByProductId(command.productId());

                productRepository.delete(productToDelete);
                inventoryRepository.deleteAll(productToDelete.getInventories());
                cloudinaryService.deleteImage(imageUrl);
            }
            else {
                throw new IllegalArgumentException("Cannot delete product with ID %s because it has stock available in a warehouse.".formatted(command.productId()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting product: " + e.getMessage(), e);
        }
    }
}
