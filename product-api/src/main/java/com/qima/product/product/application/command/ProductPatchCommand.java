package com.qima.product.product.application.command;

import java.math.BigDecimal;

public record ProductPatchCommand(
        String name,
        String description,
        Long categoryId,
        Boolean available,
        BigDecimal price) {

}
