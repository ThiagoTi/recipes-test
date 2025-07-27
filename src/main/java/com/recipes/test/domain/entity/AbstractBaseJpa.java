package com.recipes.test.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serial;
import java.util.Objects;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractBaseJpa implements BaseJpa {

    @Serial
    private static final long serialVersionUID = -5144401939612964877L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    protected Integer id;

    public AbstractBaseJpa(@NonNull @NotNull final Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractBaseJpa entity = (AbstractBaseJpa) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
