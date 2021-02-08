package com.example.RootApp.exceptions;

public class InvalidTripException extends RuntimeException {

    public InvalidTripException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
