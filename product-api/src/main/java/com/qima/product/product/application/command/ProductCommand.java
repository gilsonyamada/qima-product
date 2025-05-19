package com.qima.product.product.application.command;

import java.math.BigDecimal;

public record ProductCommand(
        Long id,
        String name,
        String description,
        String categoryPath,
        Long categoryId,
        Boolean available,
        BigDecimal price) {
}
