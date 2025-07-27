package com.recipes.test.usecase.product;

import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.projection.classbased.ProductDto;
import com.recipes.test.gateway.ProductGateway;
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
public class ProductFetchInteractor implements ProductFetch {

    private final ProductGateway productGateway;

    public ProductFetchInteractor(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public ResponseEntity<List<ProductDto>> findAll() {
        final List<ProductJpa> products = this.productGateway.findAll();
        return ResponseEntity.ok()
            .body(products.stream()
                .map(ProductDto::toDto)
                .toList());
    }

    @Override
    public ResponseEntity<ProductDto> get(@NonNull @NotNull final Integer id) {
        try {
            final ProductJpa product = this.productGateway.getById(id);
            return ResponseEntity.ok()
                .body(ProductDto.toDto(product));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }
}
