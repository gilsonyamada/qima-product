package com.qima.product.product.application.command;

public record ProductCommand(
        Long id,
        String name,
        String description,
        String categoryPath,
        Long categoryId,
        Boolean available,
        Double price) {
}
