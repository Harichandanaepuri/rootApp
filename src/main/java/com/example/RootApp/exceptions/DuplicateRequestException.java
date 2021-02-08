package com.example.RootApp.exceptions;

/**
 * Run time exception when duplicate requests come.
 */
public class DuplicateRequestException extends RuntimeException {

    public DuplicateRequestException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}