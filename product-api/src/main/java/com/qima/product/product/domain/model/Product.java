package com.qima.product.product.domain.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.qima.product.product.domain.exception.InvalidCategoryException;
import com.qima.product.product.domain.model.vo.CategoryPath;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean available;
    private Category category;
    private CategoryPath categoryPath;

    public void updateCategoryPath() {
        if (category == null) {
            throw new InvalidCategoryException("Invalid Category: category is null");
        }
        this.categoryPath = new CategoryPath(category.buildPathSegments());
    }

    public String getCategoryPathAsString() {
        return categoryPath != null ? categoryPath.getPath() : StringUtils.EMPTY;
    }

}
