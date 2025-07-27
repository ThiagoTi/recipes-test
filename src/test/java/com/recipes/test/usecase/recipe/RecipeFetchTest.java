package com.recipes.test.usecase.recipe;

import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.entity.RecipeIngredientJpa;
import com.recipes.test.domain.entity.RecipeJpa;
import com.recipes.test.domain.projection.classbased.RecipeDto;
import com.recipes.test.gateway.RecipeGateway;
import com.recipes.test.gateway.RecipeIngredientGateway;
import com.recipes.test.handler.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class RecipeFetchTest {

    @MockitoBean
    private RecipeGateway recipeGateway;

    @MockitoBean
    private RecipeIngredientGateway recipeIngredientGateway;

    @Autowired
    private RecipeFetch recipeFetch;

    @Test
    void whenFetchingRecipes_thenRetrieveRecipes() {
        //given
        final RecipeJpa recipe1 = new RecipeJpa(1, "Recipe 1");
        final RecipeJpa recipe2 = new RecipeJpa(2, "Recipe 2");
        final List<RecipeJpa> recipes = List.of(recipe1, recipe2);
        given(this.recipeGateway.findAll()).willReturn(recipes);

        // when
        final ResponseEntity<List<RecipeDto>> response = this.recipeFetch.findAll();

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().size()).isEqualTo(2);
        Assertions.assertThat(response.getBody().getFirst().getName()).isEqualTo("Recipe 1");
        Assertions.assertThat(response.getBody().getLast().getName()).isEqualTo("Recipe 2");
    }

    @Test
    void whenFetchingOneValidRecipe_thenRetrieveRecipe() throws NotFoundException {
        //given
        final RecipeJpa recipe = new RecipeJpa(1, "Recipe");
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final ProductJpa product2 = new ProductJpa(2, "Product 2", 300);
        final RecipeIngredientJpa recipeIngredient1 = new RecipeIngredientJpa(recipe, product1, 2);
        final RecipeIngredientJpa recipeIngredient2 = new RecipeIngredientJpa(recipe, product2, 1);
        given(this.recipeGateway.getById(any(Integer.class))).willReturn(recipe);
        given(this.recipeIngredientGateway.findAllWithProductByRecipeId(any(Integer.class))).willReturn(List.of(recipeIngredient1, recipeIngredient2));

        // when
        final ResponseEntity<RecipeDto> response = this.recipeFetch.get(recipe.getId());

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Recipe");
        Assertions.assertThat(response.getBody().getTotal()).isEqualTo(new BigDecimal("17.00"));
    }

    @Test
    void whenFetchingOneInvalidRecipe_thenThrowError() throws NotFoundException {
        //given
        final RecipeJpa recipe = new RecipeJpa(1, "Recipe");
        given(this.recipeGateway.getById(any(Integer.class))).willThrow(new NotFoundException());

        // when
        Exception e1 = null;
        try {
            this.recipeFetch.get(recipe.getId());
        } catch (Exception e2) {
            e1 = e2;
        }

        // then
        Assertions.assertThat(e1).isNotNull();
        Assertions.assertThat(e1.getClass()).isEqualTo(ResponseStatusException.class);
    }
}