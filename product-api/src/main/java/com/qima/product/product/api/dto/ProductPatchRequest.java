package com.qima.product.product.api.dto;

import java.math.BigDecimal;
import java.util.Optional;

public record ProductPatchRequest(
        Optional<String> name,
        Optional<String> description,
        Optional<Long> categoryId,
        Optional<Boolean> available,
        Optional<BigDecimal> price) {

}
