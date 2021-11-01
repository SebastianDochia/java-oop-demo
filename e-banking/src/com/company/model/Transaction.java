package com.company.model;

import com.company.utils.UtilityMethods;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {
    private final String senderName;
    private final String recipientName;
    private final String senderAccount;
    private final String recipientAccount;
    private final String transactionName;
    private final int value;
    private final LocalDate creationDate;
    private Status status;

    public Transaction(String senderName, String recipientName, String senderAccount, String recipientAccount, int value) {
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
        this.transactionName = UtilityMethods.generateCode();
        this.value = value;
        this.creationDate = LocalDate.now();
        this.status = Status.PENDING;
    }

    public void completeTransaction() {
        if(this.status == Status.PENDING) {
            this.status = Status.COMPLETED;
            System.out.println("| Transaction No. \"" + this.transactionName + "\" COMPLETED!");
        } else {
            System.out.println("This transaction has already been resolved");
        }
    }

    public void cancelTransaction(){
        if(this.status == Status.PENDING) {
            this.status = Status.CANCELED;
            System.out.println("Transaction No." + this.transactionName + " CANCELED!");
        } else {
            System.out.println("This transaction has already been resolved");
        }
    }

    @Override
    public String toString() {
        return "senderName='" + senderName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", senderAccount='" + senderAccount + '\'' +
                ", recipientAccount='" + recipientAccount + '\'' +
                ", transactionName='" + transactionName + '\'' +
                ", value=" + value +
                ", creationDate=" + creationDate +
                ", status=" + status;
    }

    public String getTransactionName() {
        return transactionName;
    }
}
