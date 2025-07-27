package com.recipes.test.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class TestException extends Exception {

    private final HttpStatus status;

    public TestException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }
}
