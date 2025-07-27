package com.recipes.test.domain.projection.classbased;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recipes.test.domain.entity.ProductJpa;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto extends AbstractBaseDto {

    @NonNull
    private final String name;

    @NonNull
    private final BigDecimal price;

    private final Long quantity;

    @Builder
    protected ProductDto(@NonNull final Integer id, @NonNull final String name, @NonNull final BigDecimal price, final Long quantity) {
        super(id);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static ProductDto toDto(@NonNull final ProductJpa entity) {
        return ProductDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .price(BigDecimal.valueOf(entity.getPriceInCents() / 100D).setScale(2, RoundingMode.HALF_EVEN))
            .build();
    }

    public static ProductDto toDto(@NonNull final ProductJpa entity, @NonNull final Long quantity) {
        return ProductDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .quantity(quantity)
            .price(BigDecimal.valueOf(entity.getPriceInCents() / 100D).setScale(2, RoundingMode.HALF_EVEN))
            .build();
    }
}
