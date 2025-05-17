package com.qima.product.product.domain.model.vo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString(of = "path")
@EqualsAndHashCode(of = "path")
@Getter
public class CategoryPath {
    
    public static final String PATH_SEPARATOR = " > ";
    private final String path;

    public CategoryPath(List<String> categories) {
        if (categories == null || categories.isEmpty() || categories.stream().anyMatch(String::isEmpty)) {
            this.path = StringUtils.EMPTY;
            return;
        }
        this.path = String.join(PATH_SEPARATOR, categories);
    }

}
