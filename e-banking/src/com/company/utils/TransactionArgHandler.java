package com.company.utils;

import com.company.interfaces.IHandler;

public class TransactionArgHandler implements IHandler {
    private final String[] args;

    public TransactionArgHandler(String[] args) {
        this.args = args;
    }

    @Override
    public void handleArg(int index) {

    }
}
