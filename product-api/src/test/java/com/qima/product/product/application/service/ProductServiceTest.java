package com.qima.product.product.application.service;

import static com.qima.product.product.application.service.ProductServiceFixture.getCreateProductCommand;
import static com.qima.product.product.application.service.ProductServiceFixture.getToCreatedProduct;
import static com.qima.product.product.application.service.ProductServiceFixture.getDefaultProductBuilder;
import static com.qima.product.product.application.service.ProductServiceFixture.getCreatedProductCommand;
import static com.qima.product.product.application.service.ProductServiceFixture.getDefaultCategory;
import static com.qima.product.product.application.service.ProductServiceFixture.getDefaultOptionalCategory;
import static com.qima.product.product.application.service.ProductServiceFixture.getSavedProduct;
import static com.qima.product.product.application.service.ProductServiceFixture.getUpdateProductCommand;
import static com.qima.product.product.application.service.ProductServiceFixture.getUpdatedProduct;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.qima.product.product.application.command.ProductCommand;
import com.qima.product.product.application.command.ProductPatchCommand;
import com.qima.product.product.application.mapper.ProductCommandMapper;
import com.qima.product.product.domain.exception.CategoryNotFoundException;
import com.qima.product.product.domain.exception.ProductNotFoundException;
import com.qima.product.product.domain.model.Product;
import com.qima.product.product.domain.repository.CategoryRepository;
import com.qima.product.product.domain.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductCommandMapper productCommandMapper;

    @Test
    @DisplayName("should add product successfully")
    void shouldAddProductSuccessfully() {
        when(categoryRepository.findById(anyLong())).thenReturn(getDefaultOptionalCategory());
        when(productCommandMapper.toProduct(any(ProductCommand.class))).thenReturn(getToCreatedProduct());
        when(productCommandMapper.toCommand(any(Product.class))).thenReturn(getCreatedProductCommand());
        
        
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        when(productRepository.save(productCaptor.capture())).thenReturn(getSavedProduct());
        
        ProductCommand product = productService.addProduct(getCreateProductCommand());

        assertNotNull(product);
        assertNotNull(product.id());
        assertNotNull(productCaptor.getValue().getCategory());
        assertEquals("parent > category", productCaptor.getValue().getCategoryPathAsString());
    }

    @Test
    @DisplayName("should update product successfully")
    void shouldUpdateProductSuccessfully() {
        when(productRepository.findById(anyLong())).thenReturn(getSavedProduct());
        when(categoryRepository.findById(anyLong())).thenReturn(getDefaultOptionalCategory());
        when(productCommandMapper.toProduct(any(ProductCommand.class))).thenReturn(getUpdatedProduct());
        when(productCommandMapper.toCommand(any(Product.class))).thenReturn(getCreatedProductCommand());
        
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        when(productRepository.save(productCaptor.capture())).thenReturn(getSavedProduct());
        
        productService.updateProduct(1L, getUpdateProductCommand());

        Product argument = productCaptor.getValue();
        
        assertNotNull(argument.getCategory());
        assertEquals("name updated", argument.getName());
        assertEquals("description updated", argument.getDescription());
        assertEquals("parent > category", argument.getCategoryPathAsString());
    }

    @ParameterizedTest
    @MethodSource("patchProductTestCases")
    @DisplayName("should patch product successfully")
    void shouldPatchProductSuccessfully(ProductPatchCommand patchCommand, Product expectedProduct) {
        lenient().when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(getDefaultOptionalCategory().get().getParent()));
        when(productRepository.findById(anyLong())).thenReturn(getSavedProduct());
        when(productCommandMapper.toCommand(any(Product.class))).thenReturn(getCreatedProductCommand());
        
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        when(productRepository.save(productCaptor.capture())).thenReturn(getSavedProduct());
        
        productService.patchProduct(1L, patchCommand);

        Product argument = productCaptor.getValue();
        
        expectedProduct.updateCategoryPath();
        assertNotNull(argument.getCategory());
        assertEquals(expectedProduct.getName(), argument.getName());
        assertEquals(expectedProduct.getDescription(), argument.getDescription());
        assertEquals(expectedProduct.getCategoryPath(), argument.getCategoryPath());
    }

    private static Stream<Arguments> patchProductTestCases() {
        Product namePatched = getDefaultProductBuilder().name("name patched").category(getDefaultCategory()).build();
        Product descriptionPatched = getDefaultProductBuilder().description("description patched").category(getDefaultCategory()).build();
        Product pricePatched = getDefaultProductBuilder().price(BigDecimal.TEN).category(getDefaultCategory()).build();
        Product categoryPatched = getDefaultProductBuilder().category(getDefaultOptionalCategory().get().getParent()).build();
        Product availablePatched = getDefaultProductBuilder().available(false).category(getDefaultCategory()).build();
    

        return Stream.of(
                Arguments.of(new ProductPatchCommand("name patched", null, null, null, null), namePatched),
                Arguments.of(new ProductPatchCommand(null, "description patched", null, null, null), descriptionPatched),
                Arguments.of(new ProductPatchCommand(null, null, null, null, BigDecimal.TEN), pricePatched),
                Arguments.of(new ProductPatchCommand(null, null, 1L, null, null), categoryPatched),
                Arguments.of(new ProductPatchCommand(null, null, null, false, null), availablePatched)
        );
    }

    @Test
    @DisplayName("should delete product successfully")
    void shouldDeleteProductSuccessfully() {
        
        when(productRepository.findById(anyLong())).thenReturn(getSavedProduct());
        
        assertDoesNotThrow(() -> productService.deleteProduct(1L));

    }

    @Test
    @DisplayName("should throw exception when product not found")
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
    }

    @Test
    @DisplayName("should throw exception when category not found")
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.addProduct(getCreateProductCommand()));
    }
    
}
