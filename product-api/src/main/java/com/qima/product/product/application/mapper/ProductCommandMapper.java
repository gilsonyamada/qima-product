package com.qima.product.product.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.qima.product.product.application.command.ProductCommand;
import com.qima.product.product.domain.model.Product;

@Mapper(componentModel = "spring")
public interface ProductCommandMapper {
    
    @Mapping(target = "categoryPath", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductCommand productCommand);

    @Mapping(target = "categoryPath", source = "categoryPath.path")
    @Mapping(target = "categoryId", source = "category.id")
    ProductCommand toCommand(Product product);

}
