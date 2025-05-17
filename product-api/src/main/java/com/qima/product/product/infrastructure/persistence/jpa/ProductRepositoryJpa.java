package com.qima.product.product.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qima.product.product.infrastructure.persistence.ProductEntity;

public interface ProductRepositoryJpa extends JpaRepository<ProductEntity, Long> {
    
    List<ProductEntity> findByCategoryId(Long categoryId);
    
    List<ProductEntity> findByName(String name);
}
