package com.recipes.test.repository;

import com.recipes.test.domain.entity.BaseJpa;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseJpa, ID extends Integer> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    default Optional<T> findOneById(@NonNull @NotNull ID id) {
        Assert.notNull(id, "The given ID must not be null");
        return this.findById(id);
    }
}
