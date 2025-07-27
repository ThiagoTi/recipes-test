package com.recipes.test.domain.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartAddRecipePayload implements Serializable {

    @NotNull
    @NonNull
    private Integer recipeId;

    public CartAddRecipePayload(@NonNull final Integer recipeId) {
        this.recipeId = recipeId;
    }
}
