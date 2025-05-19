package com.qima.product.product.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.qima.product.product.domain.model.Category;
import com.qima.product.product.infrastructure.persistence.entity.CategoryEntity;
import com.qima.product.product.infrastructure.persistence.jpa.CategoryRepositoryJpa;
import com.qima.product.product.infrastructure.persistence.mapper.CategoryMapper;

@ExtendWith(MockitoExtension.class) 
class CategoryRepositoryImplTest  {
    @Mock
    private CategoryRepositoryJpa categoryRepositoryJpa;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepository;

    private Category domainCategory;
    private CategoryEntity entityCategory;

    @BeforeEach
    void setUp() {
        domainCategory = Category.builder()
                .id(1L)
                .name("Books")
                .parent(null)
                .children(Collections.emptyList())
                .build();

        entityCategory = CategoryEntity.builder().id(1L).name("Books").children(Collections.emptyList()).build();
    }

    @Test
    @DisplayName("should return mapped domain object when category was saved")
    void shouldReturnMappedDomainObjectWhenSaved() {
        when(categoryMapper.toEntity(domainCategory)).thenReturn(entityCategory);
        when(categoryRepositoryJpa.save(entityCategory)).thenReturn(entityCategory);
        when(categoryMapper.toDomain(entityCategory)).thenReturn(domainCategory);

        Optional<Category> result = categoryRepository.save(domainCategory);

        assertTrue(result.isPresent());
        assertEquals(domainCategory, result.get());
        verify(categoryRepositoryJpa).save(entityCategory);
    }

    @Test
    @DisplayName("should return mapped found a cateogory by id")
    void shouldReturnMappedDomainObjectWhenFoundCategoryById() {
        when(categoryRepositoryJpa.findById(1L)).thenReturn(Optional.of(entityCategory));
        when(categoryMapper.toDomain(entityCategory)).thenReturn(domainCategory);

        Optional<Category> result = categoryRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(domainCategory, result.get());
        verify(categoryRepositoryJpa).findById(1L);
    }

    @Test
    @DisplayName("should return empty when category not found by id")
    void shouldReturnEmptyWhenCategoryNotFoundById() {
    
        when(categoryRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

        Optional<Category> result = categoryRepository.findById(1L);

        assertFalse(result.isPresent());
        verify(categoryRepositoryJpa).findById(1L);
    }

    @Test
    @DisplayName("should return mapped list when all categories are found")
    void shouldReturnMappedListWithAllCategories() {
        when(categoryRepositoryJpa.findAll()).thenReturn(List.of(entityCategory));
        when(categoryMapper.toDomain(entityCategory)).thenReturn(domainCategory);

        List<Category> result = categoryRepository.findAll();

        assertEquals(1, result.size());
        assertEquals(domainCategory, result.get(0));
        verify(categoryRepositoryJpa).findAll();
    }

    @Test
    @DisplayName("should return a list when found by name")
    void shouldReturnMappedListWhenFoundByName() {
        when(categoryRepositoryJpa.findByName("Books")).thenReturn(List.of(entityCategory));
        when(categoryMapper.toDomain(entityCategory)).thenReturn(domainCategory);

        List<Category> result = categoryRepository.findByName("Books");

        assertEquals(1, result.size());
        assertEquals(domainCategory, result.get(0));
        verify(categoryRepositoryJpa).findByName("Books");
    }

    @Test
    @DisplayName("should return mapped list when found by parent id")
    void shouldReturnMappedListWhenFindByParentId() {
        when(categoryRepositoryJpa.findByParentId(1L)).thenReturn(List.of(entityCategory));
        when(categoryMapper.toDomain(entityCategory)).thenReturn(domainCategory);

        List<Category> result = categoryRepository.findByParentId(1L);

        assertEquals(1, result.size());
        assertEquals(domainCategory, result.get(0));
        verify(categoryRepositoryJpa).findByParentId(1L);
    }

    @Test
    @DisplayName("should call jpa delete method when delete is called")
    void shouldCallJpaDeleteOnDelete() {
        when(categoryMapper.toEntity(domainCategory)).thenReturn(entityCategory);

        categoryRepository.delete(domainCategory);

        verify(categoryRepositoryJpa).delete(entityCategory);
    }

}
