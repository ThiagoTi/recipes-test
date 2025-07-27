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
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class CartUpdateInteractor implements CartUpdate {

    private final CartService cartService;

    private final CartGateway cartGateway;

    private final CartItemGateway cartItemGateway;

    private final ProductGateway productGateway;

    private final RecipeGateway recipeGateway;

    private final RecipeIngredientGateway recipeIngredientGateway;


    public CartUpdateInteractor(final CartService cartService,
                                final CartGateway cartGateway,
                                final CartItemGateway cartItemGateway,
                                final ProductGateway productGateway,
                                final RecipeGateway recipeGateway,
                                final RecipeIngredientGateway recipeIngredientGateway) {
        this.cartService = cartService;
        this.cartGateway = cartGateway;
        this.cartItemGateway = cartItemGateway;
        this.productGateway = productGateway;
        this.recipeGateway = recipeGateway;
        this.recipeIngredientGateway = recipeIngredientGateway;
    }

    @Override
    public ResponseEntity<CartDto> addItem(@NonNull @NotNull final Integer cartId,
                                           @NonNull @NotNull final CartAddItemPayload payload) {
        try {
            final CartJpa cart = this.cartGateway.getById(cartId);
            final ProductJpa product = this.productGateway.getById(payload.getItemId());
            final CartJpa updatedCart = this.cartService.addItem(cart, product);

            //to return cart with updated list of products
            final List<ProductJpa> products = this.cartItemGateway.findAllProductsByCartId(updatedCart.getId());
            return ResponseEntity.ok()
                .body(CartDto.toDto(updatedCart, products));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    @Override
    public ResponseEntity<CartDto> addRecipe(@NonNull @NotNull final Integer cartId,
                                             @NonNull @NotNull final CartAddRecipePayload payload) {
        try {
            final CartJpa cart = this.cartGateway.getById(cartId);
            final RecipeJpa recipe = this.recipeGateway.getById(payload.getRecipeId());
            final List<RecipeIngredientJpa> ingredients = this.recipeIngredientGateway.findAllWithProductByRecipeId(recipe.getId());
            final CartJpa updatedCart = this.cartService.addRecipe(cart, ingredients);

            //to return cart with updated list of products
            final List<ProductJpa> products = this.cartItemGateway.findAllProductsByCartId(updatedCart.getId());
            return ResponseEntity.ok()
                .body(CartDto.toDto(updatedCart, products));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    @Override
    public ResponseEntity<CartDto> removeItem(@NonNull @NotNull final Integer cartId,
                                              @NonNull @NotNull final Integer itemId) {
        try {
            final CartJpa cart = this.cartGateway.getById(cartId);
            final ProductJpa product = this.productGateway.getById(itemId);
            final CartJpa updatedCart = this.cartService.removeItem(cart, product);

            //to return cart with updated list of products
            final List<ProductJpa> products = this.cartItemGateway.findAllProductsByCartId(updatedCart.getId());
            return ResponseEntity.ok()
                .body(CartDto.toDto(updatedCart, products));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    @Override
    public ResponseEntity<CartDto> removeRecipe(@NonNull @NotNull final Integer cartId,
                                                @NonNull @NotNull final Integer recipeId) {
        try {
            final CartJpa cart = this.cartGateway.getById(cartId);
            final RecipeJpa recipe = this.recipeGateway.getById(recipeId);
            final List<RecipeIngredientJpa> ingredients = this.recipeIngredientGateway.findAllWithProductByRecipeId(recipe.getId());
            final CartJpa updatedCart = this.cartService.removeRecipe(cart, ingredients);

            //to return cart with updated list of products
            final List<ProductJpa> products = this.cartItemGateway.findAllProductsByCartId(updatedCart.getId());
            return ResponseEntity.ok()
                .body(CartDto.toDto(updatedCart, products));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }
}
