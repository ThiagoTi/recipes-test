package com.recipes.test.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Entity
@Table(
    name = CartJpa.TABLE_NAME
)
@NoArgsConstructor
public class CartJpa extends AbstractBaseJpa {

    protected static final String TABLE_NAME = "carts";

    @Setter
    @NotNull
    @Column(name = "total_in_cents", nullable = false)
    private Integer totalInCents;

    public CartJpa(@NonNull final Integer totalInCents) {
        this.totalInCents = totalInCents;
    }

    public CartJpa(@NonNull final Integer id, @NonNull final Integer totalInCents) {
        super(id);
        this.totalInCents = totalInCents;
    }
}
