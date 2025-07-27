package com.recipes.test.gateway;

import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.entity.RecipeIngredientJpa;
import com.recipes.test.domain.entity.RecipeJpa;
import com.recipes.test.repository.RecipeIngredientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class RecipeIngredientGatewayTest {

    @MockitoBean
    private RecipeIngredientRepository repository;

    @Autowired
    private RecipeIngredientGateway gateway;

    @Test
    void whenFetchingIngredientsWithProductByRecipe_thenRetrieveIngredients() {
        //given
        final RecipeJpa recipe = new RecipeJpa(1, "Recipe 1");
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final ProductJpa product2 = new ProductJpa(2, "Product 2", 300);
        final RecipeIngredientJpa recipeIngredient1 = new RecipeIngredientJpa(recipe, product1, 2);
        final RecipeIngredientJpa recipeIngredient2 = new RecipeIngredientJpa(recipe, product2, 1);
        final List<RecipeIngredientJpa> recipeIngredients = List.of(recipeIngredient1, recipeIngredient2);
        given(this.repository.findAllWithProductByRecipeId(any(Integer.class))).willReturn(recipeIngredients);

        //when
        final List<RecipeIngredientJpa> fetchedRecipeIngredients = this.gateway.findAllWithProductByRecipeId(recipe.getId());

        //then
        Assertions.assertThat(fetchedRecipeIngredients).isNotNull();
        Assertions.assertThat(fetchedRecipeIngredients).hasSize(2);
        Assertions.assertThat(fetchedRecipeIngredients.getFirst().getRecipe()).isEqualTo(recipe);
        Assertions.assertThat(fetchedRecipeIngredients.getFirst().getProduct()).isEqualTo(product1);
        Assertions.assertThat(fetchedRecipeIngredients.getLast().getRecipe()).isEqualTo(recipe);
        Assertions.assertThat(fetchedRecipeIngredients.getLast().getProduct()).isEqualTo(product2);
        Assertions.assertThat(fetchedRecipeIngredients.stream()
            .map(fri -> fri.getQuantity() * fri.getProduct().getPriceInCents()).reduce(0, Integer::sum)
        ).isEqualTo(1700);
    }
}