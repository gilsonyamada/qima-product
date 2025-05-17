package com.qima.product.product.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.qima.product.product.domain.model.Category;
import com.qima.product.product.domain.repository.CategoryRepository;
import com.qima.product.product.infrastructure.persistence.jpa.CategoryRepositoryJpa;
import com.qima.product.product.infrastructure.persistence.mapper.CategoryMapper;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryRepositoryJpa categoryRepositoryJpa;
    private final CategoryMapper categoryMapper;

    @Override
    public void delete(Category category) {
        categoryRepositoryJpa.delete(categoryMapper.toEntity(category));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepositoryJpa.findAll()
                .stream()
                .map(categoryMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepositoryJpa.findById(id)
                .map(categoryMapper::toDomain);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepositoryJpa.findByName(name).stream()
                .map(categoryMapper::toDomain).toList();
    }

    @Override
    public List<Category> findByParentId(Long parentId) {
        return categoryRepositoryJpa.findByParentId(parentId).stream()
                .map(categoryMapper::toDomain).toList();
    }

    @Override
    public Optional<Category> save(Category category) {
        return Optional.ofNullable(categoryRepositoryJpa.save(categoryMapper.toEntity(category)))
                .map(categoryMapper::toDomain);
    }

}
