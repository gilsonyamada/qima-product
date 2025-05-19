package com.qima.product.product.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.qima.product.product.domain.model.Product;
import com.qima.product.product.infrastructure.persistence.entity.ProductEntity;
import com.qima.product.product.infrastructure.persistence.jpa.ProductRepositoryJpa;
import com.qima.product.product.infrastructure.persistence.mapper.ProductMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductRepositoryImpl Unit Tests")
class ProductRepositoryImplTest {
    @Mock
    private ProductRepositoryJpa productRepositoryJpa;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    private Product domainProduct;
    private ProductEntity entityProduct;

    @BeforeEach
    void setup() {
        domainProduct = Product.builder()
                .id(1L)
                .name("Mouse")
                .description("Wireless mouse")
                .price(BigDecimal.TEN)
                .available(true)
                .category(null)
                .categoryPath(null)
                .build();

        entityProduct = ProductEntity.builder()
                .id(1L)
                .name("Mouse")
                .description("Wireless mouse")
                .price(BigDecimal.TEN)
                .available(true)
                .category(null)
                .categoryPath(null)
                .build();
    }

    @Test
    @DisplayName("save should persist the product and return the mapped domain object")
    void saveShouldReturnMappedDomainObject() {
        when(productMapper.toEntity(domainProduct)).thenReturn(entityProduct);
        when(productRepositoryJpa.save(entityProduct)).thenReturn(entityProduct);
        when(productMapper.toDomain(entityProduct)).thenReturn(domainProduct);

        Optional<Product> result = productRepository.save(domainProduct);

        assertTrue(result.isPresent());
        assertEquals(domainProduct, result.get());
        verify(productRepositoryJpa).save(entityProduct);
    }

    @Test
    @DisplayName("findById should return domain product when found")
    void findByIdShouldReturnMappedDomainObjectWhenFound() {
        when(productRepositoryJpa.findById(1L)).thenReturn(Optional.of(entityProduct));
        when(productMapper.toDomain(entityProduct)).thenReturn(domainProduct);

        Optional<Product> result = productRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(domainProduct, result.get());
        verify(productRepositoryJpa).findById(1L);
    }

    @Test
    @DisplayName("findById should return empty when product not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(productRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

        Optional<Product> result = productRepository.findById(1L);

        assertFalse(result.isPresent());
        verify(productRepositoryJpa).findById(1L);
    }

    @Test
    @DisplayName("delete should call the JPA delete method with mapped entity")
    void deleteShouldCallJpaDelete() {
        when(productMapper.toEntity(domainProduct)).thenReturn(entityProduct);

        productRepository.delete(domainProduct);

        verify(productRepositoryJpa).delete(entityProduct);
    }

    @Test
    @DisplayName("findByCategoryId should return a list of mapped domain products")
    void findByCategoryIdShouldReturnMappedList() {
        when(productRepositoryJpa.findByCategoryId(1L)).thenReturn(List.of(entityProduct));
        when(productMapper.toDomain(entityProduct)).thenReturn(domainProduct);

        List<Product> result = productRepository.findByCategoryId(1L);

        assertEquals(1, result.size());
        assertEquals(domainProduct, result.get(0));
        verify(productRepositoryJpa).findByCategoryId(1L);
    }

    @Test
    @DisplayName("findByName should return a list of mapped domain products")
    void findByNameShouldReturnMappedList() {
        when(productRepositoryJpa.findByName("Mouse")).thenReturn(List.of(entityProduct));
        when(productMapper.toDomain(entityProduct)).thenReturn(domainProduct);

        List<Product> result = productRepository.findByName("Mouse");

        assertEquals(1, result.size());
        assertEquals(domainProduct, result.get(0));
        verify(productRepositoryJpa).findByName("Mouse");
    }
}
