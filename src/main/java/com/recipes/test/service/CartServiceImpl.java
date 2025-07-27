package com.recipes.test.service;

import com.recipes.test.domain.entity.CartItemJpa;
import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.entity.RecipeIngredientJpa;
import com.recipes.test.gateway.CartGateway;
import com.recipes.test.gateway.CartItemGateway;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartGateway cartGateway;

    private final CartItemGateway cartItemGateway;

    public CartServiceImpl(final CartGateway cartGateway, final CartItemGateway cartItemGateway) {
        this.cartGateway = cartGateway;
        this.cartItemGateway = cartItemGateway;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartJpa addItem(@NonNull @NotNull final CartJpa cart, @NonNull @NotNull final ProductJpa product) {
        this.cartItemGateway.create(cart, product);
        cart.setTotalInCents(cart.getTotalInCents() + product.getPriceInCents());
        return this.cartGateway.update(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartJpa removeItem(@NonNull @NotNull final CartJpa cart, @NonNull @NotNull final ProductJpa product) {
        final List<CartItemJpa> items = this.cartItemGateway.findAllByCartIdAndProductId(cart.getId(), product.getId());
        if (!items.isEmpty()) {
            final CartItemJpa item = items.getFirst();
            this.cartItemGateway.delete(item);
            cart.setTotalInCents(cart.getTotalInCents() - item.getProduct().getPriceInCents());
        }
        return this.cartGateway.update(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartJpa addRecipe(@NonNull @NotNull final CartJpa cart,
                             @NonNull @NotNull final List<RecipeIngredientJpa> ingredients) {
        int total = 0;
        for (RecipeIngredientJpa i : ingredients) {
            for (int j = 0; j < i.getQuantity(); j++) {
                this.cartItemGateway.create(cart, i.getProduct());
                total += i.getProduct().getPriceInCents();
            }
        }
        cart.setTotalInCents(cart.getTotalInCents() + total);
        return this.cartGateway.update(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartJpa removeRecipe(@NonNull @NotNull final CartJpa cart,
                                @NonNull @NotNull final List<RecipeIngredientJpa> ingredients) {
        int total = 0;
        for (RecipeIngredientJpa i : ingredients) {
            final List<CartItemJpa> items = this.cartItemGateway.findAllByCartIdAndProductId(cart.getId(), i.getProduct().getId());
            final int qty = Math.min(items.size(), i.getQuantity());
            final List<CartItemJpa> toRemove = items.subList(0, qty);
            total += toRemove.stream().map(item -> item.getProduct().getPriceInCents()).reduce(0, Integer::sum);
            this.cartItemGateway.deleteAll(toRemove);
        }
        cart.setTotalInCents(cart.getTotalInCents() - total);
        return this.cartGateway.update(cart);
    }
}
