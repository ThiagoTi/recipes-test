package com.recipes.test.repository;

import com.recipes.test.domain.entity.RecipeIngredientJpa;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends BaseRepository<RecipeIngredientJpa, Integer> {

    @EntityGraph(attributePaths = {"product"})
    List<RecipeIngredientJpa> findAllWithProductByRecipeId(@NonNull @NotNull @Param("recipeId") Integer recipeId);
}
