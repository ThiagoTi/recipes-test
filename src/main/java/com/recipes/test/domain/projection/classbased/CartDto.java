package com.recipes.test.domain.projection.classbased;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.domain.entity.ProductJpa;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto extends AbstractBaseDto {

    @NonNull
    private final BigDecimal total;

    private final List<ProductDto> items;

    @Builder
    protected CartDto(@NonNull final Integer id, @NonNull final BigDecimal total, final List<ProductDto> items) {
        super(id);
        this.total = total;
        this.items = items;
    }

    public static CartDto toDto(@NonNull final CartJpa entity) {
        return CartDto.builder()
            .id(entity.getId())
            .total(BigDecimal.valueOf(entity.getTotalInCents() / 100D).setScale(2, RoundingMode.HALF_EVEN))
            .build();
    }

    public static CartDto toDto(@NonNull final CartJpa entity, final Collection<ProductJpa> items) {
        final Map<ProductJpa, Long> groupAndCount = items.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return CartDto.builder()
            .id(entity.getId())
            .total(BigDecimal.valueOf(entity.getTotalInCents() / 100D).setScale(2, RoundingMode.HALF_EVEN))
            .items(groupAndCount.entrySet().stream().map(entry -> ProductDto.toDto(entry.getKey(), entry.getValue())).toList())
            .build();
    }
}
