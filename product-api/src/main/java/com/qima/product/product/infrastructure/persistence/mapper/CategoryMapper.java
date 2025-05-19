package com.qima.product.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.qima.product.product.domain.model.Category;
import com.qima.product.product.infrastructure.persistence.entity.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toEntity(Category category);
    
    Category toDomain(CategoryEntity categoryEntity);
}
