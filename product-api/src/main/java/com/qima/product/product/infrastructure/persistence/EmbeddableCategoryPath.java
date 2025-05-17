package com.qima.product.product.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@ToString(of = "path")
@EqualsAndHashCode(of = "path")
@Getter
@Setter
public class EmbeddableCategoryPath {
    
    @Column(name = "category_path")
    private String path;

}
