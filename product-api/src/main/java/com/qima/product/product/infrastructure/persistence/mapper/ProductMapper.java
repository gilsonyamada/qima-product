package com.qima.product.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.qima.product.product.domain.model.Product;
import com.qima.product.product.infrastructure.persistence.ProductEntity;

@Mapper(componentModel = "spring", uses = CategoryPathMapper.class)
public interface ProductMapper {

    ProductEntity toEntity(Product product);

    Product toDomain(ProductEntity productEntity);
    
}
