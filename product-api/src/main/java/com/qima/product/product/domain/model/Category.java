package com.qima.product.product.domain.model;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    private Long id;
    private String name;
    private Category parent;

    public List<String> buildPathSegments() {
        List<String> segments = new LinkedList<>();
        buildPathRecursively(this, segments);
        return segments;
    }

    private void buildPathRecursively(Category category, List<String> segments) {
        if (category.getParent() != null) {
            buildPathRecursively(category.getParent(), segments);
        }
        segments.add(category.getName());
    }
}
