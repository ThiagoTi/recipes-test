package com.recipes.test.usecase.product;

import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.projection.classbased.ProductDto;
import com.recipes.test.gateway.ProductGateway;
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
class ProductFetchTest {

    @MockitoBean
    private ProductGateway productGateway;

    @Autowired
    private ProductFetch productFetch;

    @Test
    void whenFetchingProducts_thenRetrieveProducts() {
        //given
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 100);
        final ProductJpa product2 = new ProductJpa(2, "Product 2", 1250);
        final List<ProductJpa> products = List.of(product1, product2);
        given(this.productGateway.findAll()).willReturn(products);

        // when
        final ResponseEntity<List<ProductDto>> response = this.productFetch.findAll();

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().size()).isEqualTo(2);
        Assertions.assertThat(response.getBody().getFirst().getName()).isEqualTo("Product 1");
        Assertions.assertThat(response.getBody().getFirst().getPrice()).isEqualTo(new BigDecimal("1.00"));
        Assertions.assertThat(response.getBody().getLast().getName()).isEqualTo("Product 2");
        Assertions.assertThat(response.getBody().getLast().getPrice()).isEqualTo(new BigDecimal("12.50"));
    }

    @Test
    void whenFetchingOneValidProduct_thenRetrieveProduct() throws NotFoundException {
        //given
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 100);
        given(this.productGateway.getById(any(Integer.class))).willReturn(product1);

        // when
        final ResponseEntity<ProductDto> response = this.productFetch.get(product1.getId());

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.hasBody()).isTrue();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Product 1");
        Assertions.assertThat(response.getBody().getPrice()).isEqualTo(new BigDecimal("1.00"));
    }

    @Test
    void whenFetchingOneInvalidProduct_thenThrowError() throws NotFoundException {
        //given
        final ProductJpa product1 = new ProductJpa(1, "Product 1", 100);
        given(this.productGateway.getById(any(Integer.class))).willThrow(new NotFoundException());

        // when
        Exception e1 = null;
        try {
            this.productFetch.get(product1.getId());
        } catch (Exception e2) {
            e1 = e2;
        }

        // then
        Assertions.assertThat(e1).isNotNull();
        Assertions.assertThat(e1.getClass()).isEqualTo(ResponseStatusException.class);
    }
}