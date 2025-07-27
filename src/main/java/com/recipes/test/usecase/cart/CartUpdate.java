package com.recipes.test.usecase.cart;

import com.recipes.test.domain.payload.CartAddItemPayload;
import com.recipes.test.domain.payload.CartAddRecipePayload;
import com.recipes.test.domain.projection.classbased.CartDto;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

public interface CartUpdate {

    ResponseEntity<CartDto> addItem(@NonNull @NotNull final Integer cartId,
                                    @NonNull @NotNull final CartAddItemPayload payload);

    ResponseEntity<CartDto> addRecipe(@NonNull @NotNull final Integer cartId,
                                      @NonNull @NotNull final CartAddRecipePayload payload);

    ResponseEntity<CartDto> removeItem(@NonNull @NotNull final Integer cartId,
                                       @NonNull @NotNull final Integer itemId);

    ResponseEntity<CartDto> removeRecipe(@NonNull @NotNull final Integer cartId,
                                         @NonNull @NotNull final Integer recipeId);
}
