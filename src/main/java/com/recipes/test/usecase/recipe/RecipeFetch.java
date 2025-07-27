package com.recipes.test.usecase.recipe;

import com.recipes.test.domain.projection.classbased.RecipeDto;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipeFetch {

    ResponseEntity<List<RecipeDto>> findAll();

    ResponseEntity<RecipeDto> get(@NonNull @NotNull final Integer id);
}
