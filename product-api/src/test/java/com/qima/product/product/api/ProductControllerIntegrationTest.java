package com.qima.product.product.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qima.product.common.test.IntegrationTestBase;
import com.qima.product.product.api.dto.ProductCreateRequest;
import com.qima.product.product.api.dto.ProductPatchRequest;
import com.qima.product.product.api.dto.ProductUpdateRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/sql/category/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/common/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("should create product successfully")
    void shouldCreateProductSuccessfully() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest("name", "description", BigDecimal.TEN, 4L, true);

        mockMvc.perform(post("/api/v1/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("name"));
    }

    @Test
    @DisplayName("shuould update product successfully")
    @Sql(scripts = "/sql/category/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/product/insert-products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/common/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldUpdateProductSuccessfully() throws Exception {
        ProductUpdateRequest request = new ProductUpdateRequest(1L, "updated", "description upd", BigDecimal.ONE, 1L, false);

        mockMvc.perform(put("/api/v1/products/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("updated"));
    }

    @Test
    @DisplayName("should return 404 when product not found")
    void shouldReturn404WhenProductNotFound() throws Exception {
        ProductUpdateRequest request = new ProductUpdateRequest(1L, "updated", "description upd", BigDecimal.ONE, 1L, false);

        mockMvc.perform(put("/api/v1/products/100")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should patch product successfully")
    @Sql(scripts = "/sql/category/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/product/insert-products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/common/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldPatchProductSuccessfully() throws Exception {
        ProductPatchRequest request = new ProductPatchRequest("patched", null, null, null, null);

        mockMvc.perform(put("/api/v1/products/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("patched"));
    }

    @Test
    @DisplayName("should delete product successfully")
    @Sql(scripts = "/sql/category/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/product/insert-products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/common/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldDeleteProductSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/products/1"))
                .andExpect(status().isNoContent());
    }

}
