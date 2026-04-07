package com.kafka.exceptionhandler;

public class ProductNotFound extends Exception {
    private String message;
    public ProductNotFound(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
