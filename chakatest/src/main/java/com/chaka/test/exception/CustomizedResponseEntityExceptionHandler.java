package com.chaka.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.*;

@Slf4j
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers,
                                                          HttpStatus status,
                                                          WebRequest request) {
        log.info("classname " + ex.getClass().getName());
        log.info("message  " + ex.getMessage());

        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST,new Date(), null, errors);
        return handleExceptionInternal(ex, exceptionResponse, headers, exceptionResponse.getStatus(), request);

    }

    @ExceptionHandler({TransactionException.class})
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        final ExceptionResponse exceptionResponse = new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
        new Date(),
                ex.getMessage(),
                "error occurred");
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }


}