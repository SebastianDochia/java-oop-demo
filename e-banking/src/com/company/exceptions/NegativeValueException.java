package com.company.exceptions;

public class NegativeValueException extends Exception {
    public NegativeValueException(String errorMessage) {
        super(errorMessage);
    }
}
