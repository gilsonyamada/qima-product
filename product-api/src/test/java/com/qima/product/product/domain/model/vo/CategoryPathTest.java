package com.qima.product.product.domain.model.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CategoryPathTest {

    @Test
    @DisplayName("should create CategoryPath from valid category list")
    void shouldCreateCategoryPathFromValidCategoryList() {
        CategoryPath path = new CategoryPath (
            List.of("Electronics", "Computers", "Laptops")
        );
        assertNotNull(path);
        assertEquals("Electronics > Computers > Laptops", path.getPath());
    }
    
    @Test
    @DisplayName("should return equal for same path string")
    void shouldReturnEqualForSamePathString() {
        CategoryPath path1 = new CategoryPath(
            List.of("Electronics", "Computers", "Laptops")
        );
        CategoryPath path2 = new CategoryPath(
            List.of("Electronics", "Computers", "Laptops")
        );
        assertEquals(path1, path2);
    }

    @ParameterizedTest
    @MethodSource("invalidCategoryListProvider")
    @DisplayName("should create CategoryPath with invalid category list")
    void shouldCreateCategoryPathWithEmptyPathFromInvalidCategoryList(List<String> categories) {
        CategoryPath path = new CategoryPath(categories);
        assertNotNull(path);
        assertEquals(StringUtils.EMPTY, path.getPath());
    }

    private static Stream<Arguments> invalidCategoryListProvider() {
        return Stream.of(
            Arguments.of((Object) null),
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of("")),
            Arguments.of(List.of("category", ""))
        );
    }
}
