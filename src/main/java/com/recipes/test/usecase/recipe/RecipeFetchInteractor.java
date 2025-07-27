package com.recipes.test.usecase.recipe;

import com.recipes.test.domain.entity.RecipeIngredientJpa;
import com.recipes.test.domain.entity.RecipeJpa;
import com.recipes.test.domain.projection.classbased.RecipeDto;
import com.recipes.test.gateway.RecipeGateway;
import com.recipes.test.gateway.RecipeIngredientGateway;
import com.recipes.test.handler.exception.NotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class RecipeFetchInteractor implements RecipeFetch {

    private final RecipeGateway recipeGateway;

    private final RecipeIngredientGateway recipeIngredientGateway;

    public RecipeFetchInteractor(final RecipeGateway recipeGateway, final RecipeIngredientGateway recipeIngredientGateway) {
        this.recipeGateway = recipeGateway;
        this.recipeIngredientGateway = recipeIngredientGateway;
    }

    @Override
    public ResponseEntity<List<RecipeDto>> findAll() {
        final List<RecipeJpa> recipes = this.recipeGateway.findAll();
        return ResponseEntity.ok()
            .body(recipes.stream()
                .map(RecipeDto::toDto)
                .toList());
    }

    @Override
    public ResponseEntity<RecipeDto> get(@NonNull @NotNull final Integer id) {
        try {
            final RecipeJpa recipe = this.recipeGateway.getById(id);
            final List<RecipeIngredientJpa> ingredients = this.recipeIngredientGateway.findAllWithProductByRecipeId(recipe.getId());
            return ResponseEntity.ok()
                .body(RecipeDto.toDto(recipe, ingredients));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }
}
