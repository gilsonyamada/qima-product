package com.qima.product.product.infrastructure.persistence;

import java.math.BigDecimal;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class ProductEntity {
    
    @Id @GeneratedValue
    private Long id;
    
    private String name;
    
    private String description;
    
    private BigDecimal price;
    
    private Boolean available;
    
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @Embedded
    private EmbeddableCategoryPath categoryPath;


}
