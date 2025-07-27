package com.recipes.test.controller;

import com.recipes.test.domain.payload.CartAddItemPayload;
import com.recipes.test.domain.payload.CartAddRecipePayload;
import com.recipes.test.domain.projection.classbased.CartDto;
import com.recipes.test.usecase.cart.CartFetch;
import com.recipes.test.usecase.cart.CartUpdate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/carts", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController extends BaseController {

    private final CartFetch cartFetch;

    private final CartUpdate cartUpdate;

    public CartController(final CartFetch cartFetch, final CartUpdate cartUpdate) {
        this.cartFetch = cartFetch;
        this.cartUpdate = cartUpdate;
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> findAll() {
        return this.cartFetch.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<CartDto> get(@PathVariable final Integer id) {
        return this.cartFetch.get(id);
    }

    @PostMapping("{id}/add_item")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable final Integer id,
                                                 @Valid @RequestBody final CartAddItemPayload payload) {
        return this.cartUpdate.addItem(id, payload);
    }

    @PostMapping("{id}/add_recipe")
    public ResponseEntity<CartDto> addRecipeItemsToCart(@PathVariable final Integer id,
                                                        @Valid @RequestBody final CartAddRecipePayload payload) {
        return this.cartUpdate.addRecipe(id, payload);
    }

    @DeleteMapping("{id}/items/{itemId}")
    public ResponseEntity<CartDto> removeItemsFromCart(@PathVariable final Integer id,
                                                       @PathVariable final Integer itemId) {
        return this.cartUpdate.removeItem(id, itemId);
    }

    @DeleteMapping("{id}/recipes/{recipeId}")
    public ResponseEntity<CartDto> removeRecipeItemsFromCart(@PathVariable final Integer id,
                                                             @PathVariable final Integer recipeId) {
        return this.cartUpdate.removeRecipe(id, recipeId);
    }
}
