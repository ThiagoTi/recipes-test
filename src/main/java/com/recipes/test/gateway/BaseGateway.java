package com.recipes.test.gateway;

import com.recipes.test.domain.entity.BaseJpa;
import com.recipes.test.handler.exception.NotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseGateway<E extends BaseJpa> {

    //    findAll... {returns List<type>}
    List<E> findAll();

    //    findOne... {returns Optional<type>}
    Optional<E> findOneById(@NonNull @NotNull final Integer id);

    //    get...     {returns type or not found}
    E getById(@NonNull @NotNull final Integer id) throws NotFoundException;

    //    create...  {returns ?}

    //    update...  {returns ?}
    E update(@NonNull @NotNull final E entity);

    //    delete...  {returns ?}
    void delete(@NonNull @NotNull final E entity);

    void deleteAll(@NonNull @NotNull final Collection<E> entities);

    //    count...   {returns int}

    //    exists...  {returns boolean}

    //    filter...  {returns ?}

    //    new{Entity}... {returns Entity}

    //    others...  {returns type}
}
