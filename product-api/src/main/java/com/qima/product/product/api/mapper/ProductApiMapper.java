package com.qima.product.product.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.qima.product.product.api.dto.ProductCreateRequest;
import com.qima.product.product.api.dto.ProductPatchRequest;
import com.qima.product.product.api.dto.ProductResponse;
import com.qima.product.product.api.dto.ProductUpdateRequest;
import com.qima.product.product.application.command.ProductCommand;
import com.qima.product.product.application.command.ProductPatchCommand;

@Mapper(componentModel = "spring")
public interface ProductApiMapper {

    @Mapping(target = "categoryPath", ignore = true)
    @Mapping(target = "id", ignore = true)
    ProductCommand toCommand(ProductCreateRequest productRequest);

    @Mapping(target = "categoryPath", ignore = true)
    ProductCommand toCommand(ProductUpdateRequest productUpdateRequest);
 
    ProductPatchCommand toPatchCommand(ProductPatchRequest productPatchRequest);
    
    ProductResponse toResponse(ProductCommand productCommand);

    
}
