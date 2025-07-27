package com.recipes.test.gateway;

import com.recipes.test.domain.entity.RecipeIngredientJpa;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.List;

public interface RecipeIngredientGateway extends BaseGateway<RecipeIngredientJpa> {

    List<RecipeIngredientJpa> findAllWithProductByRecipeId(@NonNull @NotNull final Integer recipeId);
}
