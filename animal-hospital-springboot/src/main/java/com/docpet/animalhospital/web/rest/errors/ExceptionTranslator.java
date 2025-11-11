package com.docpet.animalhospital.web.rest.errors;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator {

    private static final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

    private static final String ERRORS = "errors";

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleBadRequestAlertException(BadRequestAlertException ex) {
        return createBadRequestResponse(ex);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("type", "https://www.jhipster.tech/problem/constraint-violation");
        body.put("title", "Method argument not valid");
        body.put("status", HttpStatus.BAD_REQUEST.value());
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        body.put(ERRORS, fieldErrors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> createBadRequestResponse(BadRequestAlertException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("params", ex.getEntityName());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

