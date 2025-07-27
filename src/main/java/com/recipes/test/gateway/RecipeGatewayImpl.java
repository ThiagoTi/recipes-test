package com.recipes.test.gateway;

import com.recipes.test.domain.entity.RecipeJpa;
import com.recipes.test.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RecipeGatewayImpl extends AbstractBaseGateway<RecipeJpa> implements RecipeGateway {

    public RecipeGatewayImpl(RecipeRepository repository) {
        super(repository);
    }
}
