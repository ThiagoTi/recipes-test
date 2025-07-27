package com.recipes.test.gateway;

import com.recipes.test.domain.entity.CartItemJpa;
import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.List;

public interface CartItemGateway extends BaseGateway<CartItemJpa> {

    List<ProductJpa> findAllProductsByCartId(@NonNull @NotNull final Integer cartId);

    List<CartItemJpa> findAllByCartIdAndProductId(@NonNull @NotNull final Integer cartId, @NonNull @NotNull final Integer productId);

    CartItemJpa create(@NonNull @NotNull final CartJpa cart, @NonNull @NotNull final ProductJpa product);
}
