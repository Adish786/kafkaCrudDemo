package com.kafka.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<Map<String, String>> handleAll(ProductNotFound productNotFound) {
        return new ResponseEntity<>(Map.of("message", productNotFound.getMessage()), HttpStatus.NOT_FOUND);
    }

}