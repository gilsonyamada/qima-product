package com.qima.product.product.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.qima.product.product.domain.model.Product;
import com.qima.product.product.domain.repository.ProductRepository;
import com.qima.product.product.infrastructure.persistence.jpa.ProductRepositoryJpa;
import com.qima.product.product.infrastructure.persistence.mapper.ProductMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductRepositoryJpa productRepositoryJpa;
    private final ProductMapper productMapper;

    @Override
    public Optional<Product> save(Product product) {
        return Optional.ofNullable(productRepositoryJpa.save(productMapper.toEntity(product)))
                .map(productMapper::toDomain);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepositoryJpa.findById(id).map(productMapper::toDomain);
    }

    @Override
    public void delete(Product product) {
        productRepositoryJpa.delete(productMapper.toEntity(product));
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepositoryJpa.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepositoryJpa.findByName(name)
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }
}
