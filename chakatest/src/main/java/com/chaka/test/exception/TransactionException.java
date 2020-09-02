package com.chaka.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(message);
    }
}
