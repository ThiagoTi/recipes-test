package com.recipes.test.gateway;

import com.recipes.test.domain.entity.CartItemJpa;
import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.repository.CartItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class CartItemGatewayTest {

    @MockitoBean
    private CartItemRepository repository;

    @Autowired
    private CartItemGateway gateway;

    @Test
    void whenFetchingProductsByCart_thenRetrieveProducts() {
        // given
        final CartJpa cart = new CartJpa(1, 1000);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final ProductJpa product2 = new ProductJpa(2, "Product 2", 300);
        final CartItemJpa cartItem1 = new CartItemJpa(cart, product1);
        final CartItemJpa cartItem2 = new CartItemJpa(cart, product2);
        final List<CartItemJpa> cartItems = List.of(cartItem1, cartItem2);
        given(this.repository.findAllWithProductByCartId(any(Integer.class))).willReturn(cartItems);

        // when
        final List<ProductJpa> products = this.gateway.findAllProductsByCartId(cart.getId());

        // then
        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products).hasSize(2);
        Assertions.assertThat(products.getFirst().getName()).isEqualTo("Product 1");
        Assertions.assertThat(products.getFirst().getPriceInCents()).isEqualTo(700);
        Assertions.assertThat(products.getLast().getName()).isEqualTo("Product 2");
        Assertions.assertThat(products.getLast().getPriceInCents()).isEqualTo(300);
    }

    @Test
    void whenFetchingIdenticalProductsByCart_thenRetrieveProducts() {
        // given
        final CartJpa cart = new CartJpa(1, 1400);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final CartItemJpa cartItem1 = new CartItemJpa(cart, product1);
        final CartItemJpa cartItem2 = new CartItemJpa(cart, product1);
        final List<CartItemJpa> cartItems = List.of(cartItem1, cartItem2);
        given(this.repository.findAllWithProductByCartId(any(Integer.class))).willReturn(cartItems);

        // when
        final List<ProductJpa> products = this.gateway.findAllProductsByCartId(cart.getId());

        // then
        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products).hasSize(2);
        Assertions.assertThat(products.getFirst().getName()).isEqualTo("Product 1");
        Assertions.assertThat(products.getFirst().getPriceInCents()).isEqualTo(700);
        Assertions.assertThat(products.getLast().getName()).isEqualTo("Product 1");
        Assertions.assertThat(products.getLast().getPriceInCents()).isEqualTo(700);
    }

    @Test
    void whenFetchingItemsByCartAndProduct_thenRetrieveItems() {
        // given
        final CartJpa cart = new CartJpa(1, 700);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final CartItemJpa cartItem1 = new CartItemJpa(cart, product1);
        final List<CartItemJpa> cartItems = List.of(cartItem1);
        given(this.repository.findAllByCartIdAndProductId(any(Integer.class), any(Integer.class))).willReturn(cartItems);

        // when
        final List<CartItemJpa> items1 = this.gateway.findAllByCartIdAndProductId(cart.getId(), product1.getId());

        // then
        Assertions.assertThat(items1).isNotNull();
        Assertions.assertThat(items1).hasSize(1);
        Assertions.assertThat(items1.getFirst().getProduct().getName()).isEqualTo("Product 1");
        Assertions.assertThat(items1.getFirst().getProduct().getPriceInCents()).isEqualTo(700);
    }

    @Test
    void whenCreatingOneCartItem_thenRetrieveOneCartItem() {
        // given
        final CartJpa cart = new CartJpa(1, 500);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 500);
        final CartItemJpa cartItem = new CartItemJpa(cart, product1);
        given(this.repository.save(any(CartItemJpa.class))).willReturn(cartItem);

        // when
        final CartItemJpa newCartItem = this.gateway.create(cart, product1);

        // then
        Assertions.assertThat(newCartItem).isNotNull();
        Assertions.assertThat(newCartItem.getCart()).isEqualTo(cart);
        Assertions.assertThat(newCartItem.getCart().getTotalInCents()).isEqualTo(500);
        Assertions.assertThat(newCartItem.getProduct()).isEqualTo(product1);
        Assertions.assertThat(newCartItem.getProduct().getName()).isEqualTo(product1.getName());
        Assertions.assertThat(newCartItem.getProduct().getPriceInCents()).isEqualTo(product1.getPriceInCents());
    }
}