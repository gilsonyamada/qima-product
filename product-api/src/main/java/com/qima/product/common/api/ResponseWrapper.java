package com.qima.product.common.api;

import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class ResponseWrapper<T> {

    private T data;
    private Metadata metadata;

    public ResponseWrapper(T data) {
        this.data = data;
        this.metadata = new Metadata();
    }
    
    @Getter
    @NoArgsConstructor
    public static class Metadata {
        private Instant timestamp = Instant.now();        
    }
}
