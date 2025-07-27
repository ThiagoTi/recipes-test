package com.recipes.test.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class ApiError {

    private Instant timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;
}
