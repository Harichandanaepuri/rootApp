package com.example.RootApp.exceptions;

public class InvalidDriverException extends RuntimeException {

    public InvalidDriverException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
