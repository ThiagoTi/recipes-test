package com.recipes.test.domain.projection.classbased;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NonNull;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractBaseDto implements BaseDto {

    private final Integer id;

    protected AbstractBaseDto() {
        id = null;
    }

    protected AbstractBaseDto(@NonNull final Integer id) {
        this.id = id;
    }
}