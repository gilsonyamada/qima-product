package com.qima.product.product.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.qima.product.product.domain.exception.InvalidCategoryException;

class ProductTest {
    @Test
    @DisplayName("should update category path correctly")
    void shouldUpdateCategoryPathCorrectly() {
        Category parent = Category.builder().id(1L).name("Parent").build();
        Category child = Category.builder().id(2L).name("Child").parent(parent).build();
        Product product = Product.builder().category(child).build();

        product.updateCategoryPath();

        String expectedPath = "Parent > Child";
        String actualPath = product.getCategoryPathAsString();

        assertEquals(expectedPath, actualPath);
    }

    @Test
    @DisplayName("should return empty string when category path is null")
    void shouldReturnEmptyStringWhenCategoryPathIsNull() {
        Product product = Product.builder().categoryPath(null).build();

        String actualPath = product.getCategoryPathAsString();

        assertEquals("", actualPath);
    }

    @Test
    @DisplayName("should throw exception when category is null")
    void shouldThrowExceptionWhenCategoryIsNull() {
        Product product = Product.builder().category(null).build();
        InvalidCategoryException exception = assertThrows(InvalidCategoryException.class, product::updateCategoryPath);
        assertEquals("Invalid Category: category is null", exception.getMessage());

    }
}
