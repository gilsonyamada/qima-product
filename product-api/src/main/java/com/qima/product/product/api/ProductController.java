package com.qima.product.product.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qima.product.common.api.ResponseWrapper;
import com.qima.product.product.api.dto.ProductCreateRequest;
import com.qima.product.product.api.dto.ProductPatchRequest;
import com.qima.product.product.api.dto.ProductResponse;
import com.qima.product.product.api.dto.ProductUpdateRequest;
import com.qima.product.product.api.mapper.ProductApiMapper;
import com.qima.product.product.application.command.ProductCommand;
import com.qima.product.product.application.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private static final String PRODUCT_API_V1_URI = "/api/v1/product/%s";
    private final ProductService productService;
    private final ProductApiMapper productApiMapper;

    /**
     * Adds a new product.
     *
     * @param productRequest the product request
     * @return the created product response
     */
    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductResponse>> addProduct(ProductCreateRequest productRequest) {
        ProductCommand product = productService.addProduct(productApiMapper.toCommand(productRequest));
        return ResponseEntity.created(URI.create(String.format(PRODUCT_API_V1_URI, product.id())))
            .body(new ResponseWrapper<ProductResponse> (productApiMapper.toResponse(product)));
    }

    /**
     * Updates an existing product.
     *
     * @param id the product ID
     * @param productRequest the product request
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductResponse>> updateProduct(@PathVariable Long id, ProductUpdateRequest productRequest) {
        ProductCommand product = productService.updateProduct(id, productApiMapper.toCommand(productRequest));
        return ResponseEntity.ok(new ResponseWrapper<ProductResponse> (productApiMapper.toResponse(product)));
    }

    /**
     * Patches an existing product.
     *
     * @param id the product ID
     * @param productRequest the product request
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductResponse>> patchProduct(@PathVariable Long id, ProductPatchRequest productRequest) {
        ProductCommand product = productService.patchProduct(id, productApiMapper.toPatchCommand(productRequest));
        return ResponseEntity.ok(new ResponseWrapper<ProductResponse> (productApiMapper.toResponse(product)));
    }

    /**
     * Deletes a product.
     *
     * @param id the product ID
     */
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }   
}
