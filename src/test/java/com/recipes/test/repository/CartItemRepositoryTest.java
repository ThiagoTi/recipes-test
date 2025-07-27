package com.recipes.test.repository;

import com.recipes.test.config.TestConfig;
import com.recipes.test.domain.entity.CartItemJpa;
import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
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
class CartItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartItemRepository repository;

    private CartJpa cart;

    private CartItemJpa cartItem1;

    private CartItemJpa cartItem2;

    private ProductJpa product1;

    @BeforeEach
    public void setUp() {
        cart = this.entityManager.persist(new CartJpa(1000));
        product1 = this.entityManager.persist(new ProductJpa("Product 1", 700));
        final ProductJpa product2 = this.entityManager.persist(new ProductJpa("Product 2", 300));
        cartItem1 = this.entityManager.persist(new CartItemJpa(cart, product1));
        cartItem2 = this.entityManager.persist(new CartItemJpa(cart, product2));
    }

    @Test
    void whenFetchingWithProductByCart_thenRetrieveItems() {
        //when
        final List<CartItemJpa> savedItems = this.repository.findAllWithProductByCartId(cart.getId());

        //then
        Assertions.assertThat(savedItems).isNotNull();
        Assertions.assertThat(savedItems).hasSize(2);
        Assertions.assertThat(savedItems.getFirst()).isEqualTo(cartItem1);
        Assertions.assertThat(savedItems.getLast()).isEqualTo(cartItem2);
    }

    @Test
    void whenFetchingByCartAndProduct_thenRetrieveItems() {
        //when
        final List<CartItemJpa> savedItems = this.repository.findAllByCartIdAndProductId(cart.getId(), product1.getId());

        //then
        Assertions.assertThat(savedItems).isNotNull();
        Assertions.assertThat(savedItems).hasSize(1);
        Assertions.assertThat(savedItems.getFirst()).isEqualTo(cartItem1);
    }
}