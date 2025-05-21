package com.qima.product.product.application.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.qima.product.product.application.command.ProductCommand;
import com.qima.product.product.domain.model.Category;
import com.qima.product.product.domain.model.Category.CategoryBuilder;
import com.qima.product.product.domain.model.Product;
import com.qima.product.product.domain.model.Product.ProductBuilder;

public class ProductServiceFixture {

    private ProductServiceFixture() {
        // Prevent instantiation
    }

    public static Optional<Category> getDefaultOptionalCategory() {
        return Optional.of(getDefaultCategoryBuilder().build());
    }

    public static Category getDefaultCategory() {
        return getDefaultCategoryBuilder().build();
    }

    public static CategoryBuilder getDefaultCategoryBuilder(){
        Category parentCategory = Category.builder()
                .id(1L)
                .name("parent")
                .build();

        return Category.builder()
                .id(2L)
                .name("category")
                .parent(parentCategory);
    }

    public static Category getUpdatedCategory() {
        return getDefaultCategoryBuilder()
                .id(1L)
                .name("category updated")
                .parent(null)
                .build();
    }

    public static ProductCommand getCreateProductCommand() {
        return new ProductCommand(null, "name", "description", null, 1L, true, BigDecimal.TEN);
    }

    public static ProductCommand getUpdateProductCommand() {
        return new ProductCommand(1L, "name updated", "description updated", null, 1L, true, BigDecimal.TEN);
    }

    public static ProductCommand getCreatedProductCommand() {
        return new ProductCommand(1L, "name", "description", null, 1L, true, BigDecimal.TEN);
    }

    public static ProductBuilder getDefaultProductBuilder() {
        return Product.builder()
                .id(1L)
                .name("name")
                .description("description")
                .available(true)
                .price(BigDecimal.TEN);
    }

    public static Product getToCreatedProduct() {
        return getDefaultProductBuilder().build();
    }

    public static Product getUpdatedProduct() {
        return Product.builder()
                .id(1L)
                .name("name updated")
                .description("description updated")
                .available(true)
                .price(BigDecimal.TWO)
                .build();
    }

    public static Optional<Product> getSavedProduct() {
        Product product = Product.builder()
                .id(1L)
                .name("name")
                .description("description")
                .available(true)
                .category(getDefaultCategory())
                .price(BigDecimal.TEN)
                .build();
        product.updateCategoryPath();
        return Optional.ofNullable(product);
    }
}
