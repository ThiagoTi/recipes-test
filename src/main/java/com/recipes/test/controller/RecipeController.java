package com.recipes.test.controller;

import com.recipes.test.domain.projection.classbased.RecipeDto;
import com.recipes.test.usecase.recipe.RecipeFetch;
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
@RequestMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController extends BaseController {

    private final RecipeFetch recipeFetch;

    public RecipeController(final RecipeFetch recipeFetch) {
        this.recipeFetch = recipeFetch;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> findAll() {
        return this.recipeFetch.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipeDto> get(@PathVariable final Integer id) {
        return this.recipeFetch.get(id);
    }
}
