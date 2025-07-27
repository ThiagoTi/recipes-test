package com.recipes.test.gateway;

import com.recipes.test.domain.entity.BaseJpa;
import com.recipes.test.handler.exception.NotFoundException;
import com.recipes.test.repository.BaseRepository;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("abstractBaseGateway")
@Transactional(readOnly = true)
public abstract class AbstractBaseGateway<E extends BaseJpa> implements BaseGateway<E> {

    private final BaseRepository<E, Integer> repository;

    public AbstractBaseGateway(BaseRepository<E, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public List<E> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<E> findOneById(@NonNull @NotNull final Integer id) {
        return this.repository.findOneById(id);
    }

    @Override
    public E getById(@NonNull @NotNull final Integer id) throws NotFoundException {
        return this.findOneById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public E update(@NonNull @NotNull final E entity) {
        return this.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NonNull @NotNull final E entity) {
        this.repository.delete(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(@NonNull @NotNull final Collection<E> entities) {
        this.repository.deleteAll(entities);
    }

    /**
     * Single repository save/update entity
     *
     * @param entity the entity
     */
    @Transactional(rollbackFor = Exception.class)
    protected E save(@NonNull @NotNull final E entity) {
        return this.repository.save(entity);
    }
}
