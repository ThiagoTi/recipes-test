package com.recipes.test.usecase.cart;

import com.recipes.test.domain.entity.CartItemJpa;
import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.entity.RecipeJpa;
import com.recipes.test.domain.projection.classbased.CartDto;
import com.recipes.test.gateway.CartGateway;
import com.recipes.test.gateway.CartItemGateway;
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
class CartFetchTest {

    @MockitoBean
    private CartGateway cartGateway;

    @MockitoBean
    private CartItemGateway cartItemGateway;

    @Autowired
    private CartFetch cartFetch;

    @Test
    void whenFetchingCarts_thenRetrieveCarts() {
        //given

        final CartJpa cart1 = new CartJpa(1, 1700);
        final CartJpa cart2 = new CartJpa(2, 140);
        final List<CartJpa> carts = List.of(cart1, cart2);
        given(this.cartGateway.findAll()).willReturn(carts);

        // when
        final ResponseEntity<List<CartDto>> response = this.cartFetch.findAll();

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().size()).isEqualTo(2);
        Assertions.assertThat(response.getBody().getFirst().getTotal()).isEqualTo(new BigDecimal("17.00"));
        Assertions.assertThat(response.getBody().getLast().getTotal()).isEqualTo(new BigDecimal("1.40"));
    }

    @Test
    void whenFetchingOneValidCart_thenRetrieveCart() throws NotFoundException {
        //given
        final CartJpa cart1 = new CartJpa(1, 1700);
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 700);
        final ProductJpa product2 = new ProductJpa(2, "Product 2", 300);
        final CartItemJpa cartItem1 = new CartItemJpa(cart1, product1);
        final CartItemJpa cartItem3 = new CartItemJpa(cart1, product2);
        given(this.cartGateway.getById(any(Integer.class))).willReturn(cart1);
        given(this.cartItemGateway.findAllProductsByCartId(any(Integer.class))).willReturn(List.of(product1, product2));

        // when
        final ResponseEntity<CartDto> response = this.cartFetch.get(cart1.getId());

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getItems().size()).isEqualTo(2);
        Assertions.assertThat(response.getBody().getTotal()).isEqualTo(new BigDecimal("17.00"));
    }

    @Test
    void whenFetchingOneInvalidRecipe_thenThrowError() throws NotFoundException {
        //given
        final RecipeJpa recipe = new RecipeJpa(1, "Recipe");
        given(this.cartGateway.getById(any(Integer.class))).willThrow(new NotFoundException());

        // when
        Exception e1 = null;
        try {
            this.cartFetch.get(recipe.getId());
        } catch (Exception e2) {
            e1 = e2;
        }

        // then
        Assertions.assertThat(e1).isNotNull();
        Assertions.assertThat(e1.getClass()).isEqualTo(ResponseStatusException.class);
    }
}