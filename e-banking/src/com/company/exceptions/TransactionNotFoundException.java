package com.company.exceptions;

public class TransactionNotFoundException extends Exception {
    public TransactionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
