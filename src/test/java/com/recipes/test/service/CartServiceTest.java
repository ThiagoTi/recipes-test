package com.recipes.test.service;

import com.recipes.test.domain.entity.CartItemJpa;
import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.entity.RecipeIngredientJpa;
import com.recipes.test.domain.entity.RecipeJpa;
import com.recipes.test.gateway.CartGateway;
import com.recipes.test.gateway.CartItemGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class CartServiceTest {

    @MockitoBean
    private CartItemGateway cartItemGateway;

    @MockitoBean
    private CartGateway cartGateway;

    @Autowired
    private CartService service;

    @Test
    void whenAddingItem_thenRetrieveUpdatedCart() {
        //given
        final CartJpa cart = new CartJpa(1, 0);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final CartItemJpa cartItem1 = new CartItemJpa(cart, product1);
        given(this.cartItemGateway.create(any(CartJpa.class), any(ProductJpa.class))).willReturn(cartItem1);
        given(this.cartGateway.update(any(CartJpa.class))).willReturn(cart);

        // when
        final CartJpa updatedCart = this.service.addItem(cart, product1);

        // then
        Assertions.assertThat(updatedCart).isNotNull();
        Assertions.assertThat(updatedCart.getTotalInCents()).isEqualTo(700);
    }

    @Test
    void whenRemovingItem_thenRetrieveUpdatedCart() {
        //given
        final CartJpa cart = new CartJpa(1, 700);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final CartItemJpa cartItem1 = new CartItemJpa(cart, product1);
        given(this.cartItemGateway.findAllByCartIdAndProductId(any(Integer.class), any(Integer.class))).willReturn(List.of(cartItem1));
        given(this.cartGateway.update(any(CartJpa.class))).willReturn(cart);

        // when
        final CartJpa updatedCart = this.service.removeItem(cart, product1);

        // then
        Assertions.assertThat(updatedCart).isNotNull();
        Assertions.assertThat(updatedCart.getTotalInCents()).isEqualTo(0);
    }

    @Test
    void whenAddingRecipe_thenRetrieveUpdatedCart() {
        //given
        final CartJpa cart = new CartJpa(1, 0);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final ProductJpa product2 = new ProductJpa(2, "Product 2", 300);
        final CartItemJpa cartItem1 = new CartItemJpa(cart, product1);
        final CartItemJpa cartItem2 = new CartItemJpa(cart, product2);
        final RecipeJpa recipe = new RecipeJpa(1, "Recipe");
        final RecipeIngredientJpa recipeIngredient1 = new RecipeIngredientJpa(recipe, product1, 2);
        final RecipeIngredientJpa recipeIngredient2 = new RecipeIngredientJpa(recipe, product2, 1);
        given(this.cartItemGateway.create(cart, product1)).willReturn(cartItem1);
        given(this.cartItemGateway.create(cart, product2)).willReturn(cartItem2);
        given(this.cartGateway.update(any(CartJpa.class))).willReturn(cart);

        // when
        final CartJpa updatedCart = this.service.addRecipe(cart, List.of(recipeIngredient1, recipeIngredient2));

        // then
        Assertions.assertThat(updatedCart).isNotNull();
        Assertions.assertThat(updatedCart.getTotalInCents()).isEqualTo(1700);
    }

    @Test
    void whenRemovingRecipe_thenRetrieveUpdatedCart() {
        //given
        final CartJpa cart = new CartJpa(1, 1700);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final ProductJpa product2 = new ProductJpa(2, "Product 2", 300);
        final CartItemJpa cartItem1 = new CartItemJpa(cart, product1);
        final CartItemJpa cartItem2 = new CartItemJpa(cart, product2);
        final RecipeJpa recipe = new RecipeJpa(1, "Recipe");
        final RecipeIngredientJpa recipeIngredient1 = new RecipeIngredientJpa(recipe, product1, 2);
        final RecipeIngredientJpa recipeIngredient2 = new RecipeIngredientJpa(recipe, product2, 1);
        given(this.cartItemGateway.findAllByCartIdAndProductId(1, 1)).willReturn(List.of(cartItem1, cartItem1));
        given(this.cartItemGateway.findAllByCartIdAndProductId(1, 2)).willReturn(List.of(cartItem2));
        given(this.cartGateway.update(any(CartJpa.class))).willReturn(cart);

        // when
        final CartJpa updatedCart = this.service.removeRecipe(cart, List.of(recipeIngredient1, recipeIngredient2));

        // then
        Assertions.assertThat(updatedCart).isNotNull();
        Assertions.assertThat(updatedCart.getTotalInCents()).isEqualTo(0);
    }
}