package com.qima.product.product.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qima.product.product.infrastructure.persistence.CategoryEntity;

public interface CategoryRepositoryJpa extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByParentId(Long parentId);

    List<CategoryEntity> findByName(String name);

    List<CategoryEntity> findByNameAndParentId(String name, Long parentId);
    
}
