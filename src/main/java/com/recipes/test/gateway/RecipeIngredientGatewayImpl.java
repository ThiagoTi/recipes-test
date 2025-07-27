package com.recipes.test.gateway;

import com.recipes.test.domain.entity.RecipeIngredientJpa;
import com.recipes.test.repository.RecipeIngredientRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RecipeIngredientGatewayImpl extends AbstractBaseGateway<RecipeIngredientJpa> implements RecipeIngredientGateway {

    private final RecipeIngredientRepository repository;

    public RecipeIngredientGatewayImpl(RecipeIngredientRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<RecipeIngredientJpa> findAllWithProductByRecipeId(final @NonNull Integer recipeId) {
        return this.repository.findAllWithProductByRecipeId(recipeId);
    }
}
