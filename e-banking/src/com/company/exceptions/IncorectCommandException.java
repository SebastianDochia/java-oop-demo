package com.company.exceptions;

public class IncorectCommandException extends Exception{
    public IncorectCommandException(String errorMessage) {
        super(errorMessage);
    }
}
