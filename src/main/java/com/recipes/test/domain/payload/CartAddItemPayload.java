package com.recipes.test.domain.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartAddItemPayload implements Serializable {

    @NotNull
    @NonNull
    private Integer itemId;

    public CartAddItemPayload(@NonNull final Integer itemId) {
        this.itemId = itemId;
    }
}
