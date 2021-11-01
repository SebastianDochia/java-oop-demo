package com.company.utils;

import com.company.interfaces.IHandler;

public class ArgHandler implements IHandler {
    private final String[] args;

    public ArgHandler(String[] args) {
        this.args = args;
    }

    @Override
    public void handleArg(int index) {
        BinaryReader reader = new BinaryReader();

        if(this.args[index].charAt(0) == '-') {
            switch (this.args[index]) {
                case "-createDummy":
                    Commands.createDummy("dummy");
                    System.exit(0);
                    break;
                case "-l":
                    if(index == 1) {
                        reader.loadDataFromFile(this.args[index - 1]);
                        reader.readBundle();
                    }
            }
        } else {
            reader.loadDataFromFile(this.args[index]);
        }

    }
}
