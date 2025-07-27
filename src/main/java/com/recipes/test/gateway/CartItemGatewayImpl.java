package com.recipes.test.gateway;

import com.recipes.test.domain.entity.CartItemJpa;
import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.repository.CartItemRepository;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CartItemGatewayImpl extends AbstractBaseGateway<CartItemJpa> implements CartItemGateway {

    private final CartItemRepository repository;

    public CartItemGatewayImpl(CartItemRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<ProductJpa> findAllProductsByCartId(@NonNull @NotNull final Integer cartId) {
        return this.repository.findAllWithProductByCartId(cartId)
            .stream()
            .map(CartItemJpa::getProduct)
            .toList();
    }

    @Override
    public List<CartItemJpa> findAllByCartIdAndProductId(@NonNull @NotNull final Integer cartId, @NonNull @NotNull final Integer productId) {
        return this.repository.findAllByCartIdAndProductId(cartId, productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartItemJpa create(@NonNull @NotNull final CartJpa cart, @NonNull @NotNull final ProductJpa product) {
        return this.save(new CartItemJpa(cart, product));
    }
}
