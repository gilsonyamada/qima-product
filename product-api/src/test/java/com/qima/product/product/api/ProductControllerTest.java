package com.qima.product.product.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qima.product.product.api.dto.ProductCreateRequest;
import com.qima.product.product.api.dto.ProductPatchRequest;
import com.qima.product.product.api.dto.ProductResponse;
import com.qima.product.product.api.dto.ProductUpdateRequest;
import com.qima.product.product.api.mapper.ProductApiMapper;
import com.qima.product.product.application.command.ProductCommand;
import com.qima.product.product.application.command.ProductPatchCommand;
import com.qima.product.product.application.service.ProductService;
import com.qima.product.product.domain.exception.CategoryNotFoundException;
import com.qima.product.product.domain.exception.InvalidCategoryException;
import com.qima.product.product.domain.exception.ProductNotFoundException;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductApiMapper productApiMapper;

    @Test
    @DisplayName("should add a new product successfully")
    void shouldAddProductSuccessfully() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest("name", "description", BigDecimal.TEN, 1L, true);
        ProductCommand commandRequest = new ProductCommand(null, "Name", "descriptin", "cat > sub", 2L, true, BigDecimal.TEN);
        
        ProductCommand commandResponse = new ProductCommand(1L, "Name", "descriptin", "cat > sub", 2L, true, BigDecimal.TEN);
        ProductResponse response = new ProductResponse(1L, "Name", "description", BigDecimal.TEN, 1L, "cat > sub",
                true);

        when(productApiMapper.toCommand(request)).thenReturn(commandRequest);
        when(productService.addProduct(commandRequest)).thenReturn(commandResponse);
        when(productApiMapper.toResponse(commandResponse)).thenReturn(response);

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/product/1"))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @DisplayName("should update a product successfully")
    void shouldUpdateProductSuccessfully() throws Exception {
        Long id = 1L;
        ProductUpdateRequest request = new ProductUpdateRequest(1L, "Updated", "description", BigDecimal.TEN, 1L, true);
        ProductCommand commandRequest = new ProductCommand(1L, "Updated", "descriptin", null, 1L, true, BigDecimal.TEN);

        ProductCommand commandResponse = new ProductCommand(null, "Updated", "descriptin", "cat > sub", 2L, true, BigDecimal.TEN);
        ProductResponse response = new ProductResponse(1L, "Updated", "description", BigDecimal.TEN, 1L, "cat > sub",
                true);

        when(productApiMapper.toCommand(request)).thenReturn(commandRequest);
        when(productService.updateProduct(id, commandRequest)).thenReturn(commandResponse);
        when(productApiMapper.toResponse(commandResponse)).thenReturn(response);

        mockMvc.perform(put("/api/v1/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated"));
    }

    @Test
    @DisplayName("should patch a product successfully")
    void shouldPatchProductSuccessfully() throws Exception {
        Long id = 1L;
        ProductPatchRequest request = new ProductPatchRequest("Patched", null, null, null, null);
        ProductPatchCommand pathCommand = new ProductPatchCommand("Patched", null, null, null, null);
        ProductCommand command = new ProductCommand(null, "Patched", "descriptin", "cat > sub", 2L, true, BigDecimal.TEN);
        ProductResponse response = new ProductResponse(id, "Patched", "description", BigDecimal.TEN, 1L, "cat > sub",
                true);

        when(productApiMapper.toPatchCommand(request)).thenReturn(pathCommand);
        when(productService.patchProduct(id, pathCommand)).thenReturn(command);
        when(productApiMapper.toResponse(command)).thenReturn(response);

        mockMvc.perform(patch("/api/v1/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Patched"));
    }

    @Test
    @DisplayName("should delete a product successfully")
    void shouldDeleteProductSuccessfully() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/products/{id}", id))
                .andExpect(status().isOk());

        verify(productService).deleteProduct(id);
    }

    @Test
    @DisplayName("should return 404 when category not found")
    void shouldReturnNotFoundWhenCategoryNotFound() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest("name", "description", BigDecimal.TEN, 1L, true);
        ProductCommand commandRequest = new ProductCommand(null, "Name", "descriptin", "cat > sub", 2L, true, BigDecimal.TEN);
        

        when(productApiMapper.toCommand(request)).thenReturn(commandRequest);
        when(productService.addProduct(commandRequest)).thenThrow(new CategoryNotFoundException("category not found"));
        

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("should return 400 when invalid category")
    void shouldReturnBadRequestWhenInvalidCategory() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest("name", "description", BigDecimal.TEN, 1L, true);
        ProductCommand commandRequest = new ProductCommand(null, "Name", "descriptin", "cat > sub", 2L, true, BigDecimal.TEN);

        when(productApiMapper.toCommand(request)).thenReturn(commandRequest);
        when(productService.addProduct(commandRequest)).thenThrow(new InvalidCategoryException("Invalid category"));

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("should return 500 when internal server error")
    void shouldReturnInternalServerError() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest("name", "description", BigDecimal.TEN, 1L, true);
        ProductCommand commandRequest = new ProductCommand(null, "Name", "descriptin", "cat > sub", 2L, true, BigDecimal.TEN);

        when(productApiMapper.toCommand(request)).thenReturn(commandRequest);
        when(productService.addProduct(commandRequest)).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("should return 404 when product not found")
    void shouldReturnNotFoundWhenProductNotFound() throws Exception {
        Long id = 1L;
        ProductUpdateRequest request = new ProductUpdateRequest(1L, "Updated", "description", BigDecimal.TEN, 1L, true);
        ProductCommand commandRequest = new ProductCommand(1L, "Updated", "descriptin", null, 1L, true, BigDecimal.TEN);

        when(productApiMapper.toCommand(request)).thenReturn(commandRequest);
        when(productService.updateProduct(id, commandRequest)).thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(put("/api/v1/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }
    
}
