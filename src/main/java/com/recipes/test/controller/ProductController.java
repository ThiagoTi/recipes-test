package com.recipes.test.controller;

import com.recipes.test.domain.projection.classbased.ProductDto;
import com.recipes.test.usecase.product.ProductFetch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends BaseController {

    private final ProductFetch productFetch;

    public ProductController(final ProductFetch productFetch) {
        this.productFetch = productFetch;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return this.productFetch.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> get(@PathVariable final Integer id) {
        return this.productFetch.get(id);
    }
}
