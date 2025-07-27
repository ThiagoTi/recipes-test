package com.recipes.test.domain.projection.classbased;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.domain.entity.RecipeIngredientJpa;
import com.recipes.test.domain.entity.RecipeJpa;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDto extends AbstractBaseDto {

    @NonNull
    private final String name;

    private final BigDecimal total;

    private final List<ProductDto> items;

    @Builder
    protected RecipeDto(@NonNull final Integer id,
                        @NonNull final String name,
                        BigDecimal total,
                        final List<ProductDto> items) {
        super(id);
        this.name = name;
        this.total = total;
        this.items = items;
    }

    public static RecipeDto toDto(@NonNull final RecipeJpa entity) {
        return RecipeDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    public static RecipeDto toDto(@NonNull final RecipeJpa entity, final Collection<RecipeIngredientJpa> ingredients) {
        int total = 0;
        List<ProductDto> items = new ArrayList<>();
        for (RecipeIngredientJpa i : ingredients) {
            final ProductJpa product = i.getProduct();
            final Integer quantity = i.getQuantity();
            items.add(ProductDto.toDto(product, Long.valueOf(quantity)));
            total += product.getPriceInCents() * quantity;
        }
        return RecipeDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .total(BigDecimal.valueOf(total / 100D).setScale(2, RoundingMode.HALF_EVEN))
            .items(items)
            .build();
    }
}
