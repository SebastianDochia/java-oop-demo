package com.company.exceptions;

public class IncorrectDateException extends Exception {
    public IncorrectDateException(String errorMessage) {
        super(errorMessage);
    }
}
