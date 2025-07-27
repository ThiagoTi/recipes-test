package com.recipes.test.usecase.cart;

import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.entity.RecipeIngredientJpa;
import com.recipes.test.domain.entity.RecipeJpa;
import com.recipes.test.domain.payload.CartAddItemPayload;
import com.recipes.test.domain.payload.CartAddRecipePayload;
import com.recipes.test.domain.projection.classbased.CartDto;
import com.recipes.test.gateway.CartGateway;
import com.recipes.test.gateway.CartItemGateway;
import com.recipes.test.gateway.ProductGateway;
import com.recipes.test.gateway.RecipeGateway;
import com.recipes.test.gateway.RecipeIngredientGateway;
import com.recipes.test.handler.exception.NotFoundException;
import com.recipes.test.service.CartService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class CartUpdateTest {

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private CartGateway cartGateway;

    @MockitoBean
    private CartItemGateway cartItemGateway;

    @MockitoBean
    private ProductGateway productGateway;

    @MockitoBean
    private RecipeGateway recipeGateway;

    @MockitoBean
    private RecipeIngredientGateway recipeIngredientGateway;

    @Autowired
    private CartUpdate cartUpdate;

    @Test
    void whenAddingItem_thenRetrieveUpdatedCart() throws NotFoundException {
        //given
        final CartJpa cart = new CartJpa(1, 3700);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 3700);
        given(this.cartGateway.getById(any(Integer.class))).willReturn(cart);
        given(this.productGateway.getById(any(Integer.class))).willReturn(product1);
        given(this.cartService.addItem(any(CartJpa.class), any(ProductJpa.class))).willReturn(cart);
        given(this.cartItemGateway.findAllProductsByCartId(any(Integer.class))).willReturn(List.of(product1));

        // when
        final ResponseEntity<CartDto> response = this.cartUpdate.addItem(1, new CartAddItemPayload(1));

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getTotal()).isEqualTo(new BigDecimal("37.00"));
        Assertions.assertThat(response.getBody().getItems().size()).isEqualTo(1);
        Assertions.assertThat(response.getBody().getItems().getFirst().getPrice()).isEqualTo(new BigDecimal("37.00"));
    }

    @Test
    void whenRemovingItem_thenRetrieveUpdatedCart() throws NotFoundException {
        //given
        final CartJpa cart = new CartJpa(1, 0);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        given(this.cartGateway.getById(any(Integer.class))).willReturn(cart);
        given(this.productGateway.getById(any(Integer.class))).willReturn(product1);
        given(this.cartService.removeItem(any(CartJpa.class), any(ProductJpa.class))).willReturn(cart);
        given(this.cartItemGateway.findAllProductsByCartId(any(Integer.class))).willReturn(Collections.emptyList());

        // when
        final ResponseEntity<CartDto> response = this.cartUpdate.removeItem(1, 1);

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getTotal()).isEqualTo(new BigDecimal("0.00"));
        Assertions.assertThat(response.getBody().getItems().size()).isEqualTo(0);
    }

    @Test
    void whenAddingRecipe_thenRetrieveUpdatedCart() throws NotFoundException {
        //given
        final CartJpa cart = new CartJpa(1, 1000);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final ProductJpa product2 = new ProductJpa(2, "Product 2", 300);
        final RecipeJpa recipe = new RecipeJpa(1, "Recipe");
        final RecipeIngredientJpa recipeIngredient1 = new RecipeIngredientJpa(recipe, product1, 1);
        final RecipeIngredientJpa recipeIngredient2 = new RecipeIngredientJpa(recipe, product2, 1);
        given(this.cartGateway.getById(any(Integer.class))).willReturn(cart);
        given(this.recipeGateway.getById(any(Integer.class))).willReturn(recipe);
        given(this.recipeIngredientGateway.findAllWithProductByRecipeId(any(Integer.class))).willReturn(List.of(recipeIngredient1, recipeIngredient2));
        given(this.cartService.addRecipe(any(CartJpa.class), anyList())).willReturn(cart);
        given(this.cartItemGateway.findAllProductsByCartId(any(Integer.class))).willReturn(List.of(product1, product2));

        // when
        final ResponseEntity<CartDto> response = this.cartUpdate.addRecipe(1, new CartAddRecipePayload(1));

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getTotal()).isEqualTo(new BigDecimal("10.00"));
        Assertions.assertThat(response.getBody().getItems().size()).isEqualTo(2);
    }

    @Test
    void whenRemovingRecipe_thenRetrieveUpdatedCart() throws NotFoundException {
        //given
        final CartJpa cart = new CartJpa(1, 0);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final ProductJpa product2 = new ProductJpa(2, "Product 2", 300);
        final RecipeJpa recipe = new RecipeJpa(1, "Recipe");
        final RecipeIngredientJpa recipeIngredient1 = new RecipeIngredientJpa(recipe, product1, 2);
        final RecipeIngredientJpa recipeIngredient2 = new RecipeIngredientJpa(recipe, product2, 1);
        given(this.cartGateway.getById(any(Integer.class))).willReturn(cart);
        given(this.recipeGateway.getById(any(Integer.class))).willReturn(recipe);
        given(this.recipeIngredientGateway.findAllWithProductByRecipeId(any(Integer.class))).willReturn(List.of(recipeIngredient1, recipeIngredient2));
        given(this.cartService.removeRecipe(any(CartJpa.class), anyList())).willReturn(cart);
        given(this.cartItemGateway.findAllProductsByCartId(any(Integer.class))).willReturn(Collections.emptyList());

        // when
        final ResponseEntity<CartDto> response = this.cartUpdate.removeRecipe(1, 1);

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getTotal()).isEqualTo(new BigDecimal("0.00"));
        Assertions.assertThat(response.getBody().getItems().size()).isEqualTo(0);
    }
}