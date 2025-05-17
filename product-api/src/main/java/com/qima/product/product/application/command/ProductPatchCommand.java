package com.qima.product.product.application.command;

import java.util.Optional;

public record ProductPatchCommand(
        Optional<String> name,
        Optional<String> description,
        Optional<Long> categoryId,
        Optional<Boolean> available,
        Optional<Double> price) {

}
