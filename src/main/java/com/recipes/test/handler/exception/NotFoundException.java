package com.recipes.test.handler.exception;

import com.recipes.test.domain.constant.ReasonConstants;
import org.springframework.http.HttpStatus;

public class NotFoundException extends TestException {

    public NotFoundException() {
        super(ReasonConstants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
