package com.recipes.test.repository;

import com.recipes.test.domain.entity.CartItemJpa;
import com.recipes.test.domain.entity.ProductJpa;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends BaseRepository<CartItemJpa, Integer> {

    @EntityGraph(attributePaths = {"product"})
    List<CartItemJpa> findAllWithProductByCartId(@NonNull @NotNull Integer cartId);

    List<CartItemJpa> findAllByCartIdAndProductId(@NonNull @NotNull Integer cartId, @NonNull @NotNull Integer productId);
}
