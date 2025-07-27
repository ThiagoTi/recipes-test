package com.recipes.test.repository;

import com.recipes.test.domain.entity.CartJpa;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends BaseRepository<CartJpa, Integer> {

}
