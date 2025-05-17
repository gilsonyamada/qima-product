package com.qima.product.product.infrastructure.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.qima.product.product.domain.model.vo.CategoryPath;
import com.qima.product.product.infrastructure.persistence.EmbeddableCategoryPath;

@Mapper(componentModel = "spring")
public interface CategoryPathMapper {

    EmbeddableCategoryPath toEmbeddable(CategoryPath categoryPath);

    default CategoryPath toDomain(EmbeddableCategoryPath embeddableCategoryPath) {
        return new CategoryPath(List.of(embeddableCategoryPath.getPath()
                .split(CategoryPath.PATH_SEPARATOR)));
    }

}
