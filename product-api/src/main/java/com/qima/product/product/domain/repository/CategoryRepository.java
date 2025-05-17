package com.qima.product.product.domain.repository;

import java.util.List;
import java.util.Optional;

import com.qima.product.product.domain.model.Category;

public interface CategoryRepository {
    
    Optional<Category> findById(Long id);

    Optional<Category> save(Category category);

    void delete(Category category);

    List<Category> findAll();

    List<Category> findByParentId(Long parentId);

    List<Category> findByName(String name);
}
