package com.recipes.test.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity
@Table(
    name = RecipeIngredientJpa.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(name = "recipe_ingredients_uk_recipe_product", columnNames = {"recipe_id", "product_id"})
    },
    indexes = {
        @Index(name = "recipe_ingredients_recipe_id_idx", columnList = "cart_id"),
        @Index(name = "recipe_ingredients_product_id_idx", columnList = "product_id")
    }
)
@NoArgsConstructor
public class RecipeIngredientJpa extends AbstractBaseJpa {

    protected static final String TABLE_NAME = "recipe_ingredients";

    @Column(name = "recipe_id", insertable = false, updatable = false)
    private Long recipeId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties("recipes")
    private RecipeJpa recipe;

    @Column(name = "product_id", insertable = false, updatable = false)
    private Long productId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties("products")
    private ProductJpa product;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public RecipeIngredientJpa(@NonNull final RecipeJpa recipe, @NonNull final ProductJpa product, @NonNull final Integer quantity) {
        this.recipe = recipe;
        this.product = product;
        this.quantity = quantity;
    }
}
