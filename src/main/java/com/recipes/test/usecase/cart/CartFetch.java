package com.recipes.test.usecase.cart;

import com.recipes.test.domain.projection.classbased.CartDto;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartFetch {

    ResponseEntity<List<CartDto>> findAll();

    ResponseEntity<CartDto> get(@NonNull @NotNull final Integer id);
}
