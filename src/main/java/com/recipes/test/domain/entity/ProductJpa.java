package com.recipes.test.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity
@Table(
    name = ProductJpa.TABLE_NAME
)
@NoArgsConstructor
public class ProductJpa extends AbstractBaseJpa {

    protected static final String TABLE_NAME = "products";

    @NotNull
    @NotBlank
    @Column(name = "product_name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price_in_cents", nullable = false)
    private Integer priceInCents;

    public ProductJpa(@NonNull final String name, @NonNull final Integer priceInCents) {
        this.name = name.trim();
        this.priceInCents = priceInCents;
    }

    public ProductJpa(@NonNull final Integer id, @NonNull final String name, @NonNull final Integer priceInCents) {
        super(id);
        this.name = name.trim();
        this.priceInCents = priceInCents;
    }
}
