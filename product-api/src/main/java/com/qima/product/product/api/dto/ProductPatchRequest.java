package com.qima.product.product.api.dto;

import java.math.BigDecimal;

public record ProductPatchRequest(
        String name,
        String description,
        Long categoryId,
        Boolean available,
        BigDecimal price) {

}
