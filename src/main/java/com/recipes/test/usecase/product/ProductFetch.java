package com.recipes.test.usecase.product;

import com.recipes.test.domain.projection.classbased.ProductDto;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductFetch {

    ResponseEntity<List<ProductDto>> findAll();

    ResponseEntity<ProductDto> get(@NonNull @NotNull final Integer id);
}
