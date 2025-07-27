package com.recipes.test.usecase.cart;

import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.projection.classbased.CartDto;
import com.recipes.test.gateway.CartGateway;
import com.recipes.test.gateway.CartItemGateway;
import com.recipes.test.handler.exception.NotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class CartFetchInteractor implements CartFetch {

    private final CartGateway cartGateway;

    private final CartItemGateway cartItemGateway;

    public CartFetchInteractor(final CartGateway cartGateway, final CartItemGateway cartItemGateway) {
        this.cartGateway = cartGateway;
        this.cartItemGateway = cartItemGateway;
    }

    @Override
    public ResponseEntity<List<CartDto>> findAll() {
        final List<CartJpa> carts = this.cartGateway.findAll();
        return ResponseEntity.ok()
            .body(carts.stream()
                .map(CartDto::toDto)
                .toList());
    }

    @Override
    public ResponseEntity<CartDto> get(@NonNull @NotNull final Integer id) {
        try {
            final CartJpa cart = this.cartGateway.getById(id);
            final List<ProductJpa> products = this.cartItemGateway.findAllProductsByCartId(cart.getId());
            return ResponseEntity.ok()
                .body(CartDto.toDto(cart, products));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }
}
