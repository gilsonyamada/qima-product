package com.qima.product.product.application.service;

import org.springframework.stereotype.Service;

import com.qima.product.product.application.command.ProductCommand;
import com.qima.product.product.application.command.ProductPatchCommand;
import com.qima.product.product.application.mapper.ProductCommandMapper;
import com.qima.product.product.domain.exception.CategoryNotFoundException;
import com.qima.product.product.domain.exception.ProductNotFoundException;
import com.qima.product.product.domain.model.Category;
import com.qima.product.product.domain.model.Product;
import com.qima.product.product.domain.repository.CategoryRepository;
import com.qima.product.product.domain.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCommandMapper productCommandMapper;

    public ProductCommand addProduct(ProductCommand productCommand) {
        Category category = getCategory(productCommand.categoryId());
        Product product = productCommandMapper.toProduct(productCommand);
        product.setCategory(category);
        product.updateCategoryPath();
        return productRepository.save(product).map(productCommandMapper::toCommand).orElse(null);
    }

    public ProductCommand updateProduct(Long id, ProductCommand productCommand) {
        getProduct(id);
        Category category = getCategory(productCommand.categoryId());
        Product updatedProduct = productCommandMapper.toProduct(productCommand);
        updatedProduct.setCategory(category);
        updatedProduct.updateCategoryPath();
        return productRepository.save(updatedProduct).map(productCommandMapper::toCommand).orElse(null);
    }

    public ProductCommand patchProduct(Long id, ProductPatchCommand productCommand) {
        Product product = getProduct(id);
        productCommand.available().ifPresent(product::setAvailable);
        productCommand.name().ifPresent(product::setName);
        productCommand.description().ifPresent(product::setDescription);
        productCommand.price().ifPresent(product::setPrice);
        productCommand.categoryId().ifPresent(categoryId -> {
            Category category = getCategory(categoryId);
            product.setCategory(category);
            product.updateCategoryPath();
        });
        return productRepository.save(product).map(productCommandMapper::toCommand).orElse(null);
    }

    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found:" + id));
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }
}
