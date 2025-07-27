package com.recipes.test.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity
@Table(
    name = RecipeJpa.TABLE_NAME
)
@NoArgsConstructor
public class RecipeJpa extends AbstractBaseJpa {

    protected static final String TABLE_NAME = "recipes";

    @NotNull
    @NotBlank
    @Column(name = "recipe_name", nullable = false)
    private String name;

    public RecipeJpa(@NonNull final String name) {
        this.name = name.trim();
    }

    public RecipeJpa(@NonNull final Integer id, @NonNull final String name) {
        super(id);
        this.name = name.trim();
    }
}
