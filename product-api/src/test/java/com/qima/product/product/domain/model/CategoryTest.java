package com.qima.product.product.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    @DisplayName("should build path segments correctly")
    void shouldBuildPathSegmentsCorretcly() {
        Category parent = Category.builder().id(1L).name("Parent").build();
        Category child = Category.builder().id(2L).name("Child").parent(parent).build();

        List<String> pathSegments = child.buildPathSegments();

        assertEquals(2, pathSegments.size());
        assertEquals("Parent", pathSegments.get(0));
        assertEquals("Child", pathSegments.get(1));
    }
}
