package com.qima.product.product.domain.repository;

import java.util.List;
import java.util.Optional;

import com.qima.product.product.domain.model.Product;

public interface ProductRepository {

    Optional<Product> save(Product product);

    Optional<Product> findById(Long id);

    void delete(Product product);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByName(String name);
}
