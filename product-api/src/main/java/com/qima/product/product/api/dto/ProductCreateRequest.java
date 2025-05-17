package com.qima.product.product.api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreateRequest(
        @NotBlank(message = "Name is mandatory") String name,
        @NotBlank(message = "Description is mandatory") String description,
        @Min(value = 0, message = "Price must be greater than or equal to 0") BigDecimal price,
        @NotNull(message = "Category ID is mandatory") Long categoryId,
        @NotNull(message = "Availability status is mandatory") Boolean available) {

}
