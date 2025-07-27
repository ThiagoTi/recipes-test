package com.recipes.test.service;

import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.entity.RecipeIngredientJpa;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.List;

public interface CartService {

    CartJpa addItem(@NonNull @NotNull final CartJpa cart, @NonNull @NotNull final ProductJpa product);

    CartJpa removeItem(@NonNull @NotNull final CartJpa cart, @NonNull @NotNull final ProductJpa product);

    CartJpa addRecipe(@NonNull @NotNull final CartJpa cart,
                      @NonNull @NotNull final List<RecipeIngredientJpa> ingredients);

    CartJpa removeRecipe(@NonNull @NotNull final CartJpa cart,
                         @NonNull @NotNull final List<RecipeIngredientJpa> ingredients);
}
