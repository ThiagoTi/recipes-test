package com.recipes.test.repository;

import com.recipes.test.config.TestConfig;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.entity.RecipeIngredientJpa;
import com.recipes.test.domain.entity.RecipeJpa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class RecipeIngredientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecipeIngredientRepository repository;

    private RecipeJpa recipe;

    private RecipeIngredientJpa recipeIngredient1;

    private RecipeIngredientJpa recipeIngredient2;

    private ProductJpa product1;

    @BeforeEach
    public void setUp() {
        recipe = this.entityManager.persist(new RecipeJpa("Recipe 1"));
        product1 = this.entityManager.persist(new ProductJpa("Product 1", 700));
        final ProductJpa product2 = this.entityManager.persist(new ProductJpa("Product 2", 300));
        recipeIngredient1 = this.entityManager.persist(new RecipeIngredientJpa(recipe, product1, 2));
        recipeIngredient2 = this.entityManager.persist(new RecipeIngredientJpa(recipe, product2, 1));
    }

    @Test
    void whenFetchingWithProductByRecipe_thenRetrieveItems() {
        //when
        final List<RecipeIngredientJpa> savedIngredients = this.repository.findAllWithProductByRecipeId(recipe.getId());

        //then
        Assertions.assertThat(savedIngredients).isNotNull();
        Assertions.assertThat(savedIngredients).hasSize(2);
        Assertions.assertThat(savedIngredients.getFirst()).isEqualTo(recipeIngredient1);
        Assertions.assertThat(savedIngredients.getFirst().getProduct()).isEqualTo(product1);
        Assertions.assertThat(savedIngredients.getFirst().getQuantity()).isEqualTo(2);
        Assertions.assertThat(savedIngredients.getLast()).isEqualTo(recipeIngredient2);
    }
}