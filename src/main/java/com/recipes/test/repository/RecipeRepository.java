package com.recipes.test.repository;

import com.recipes.test.domain.entity.RecipeJpa;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends BaseRepository<RecipeJpa, Integer> {

}
