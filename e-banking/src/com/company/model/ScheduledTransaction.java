package com.company.model;

import java.time.LocalDate;

public class ScheduledTransaction extends Transaction{
    private final LocalDate executionDate;

    public ScheduledTransaction(String senderName, String recipientName, String senderAccount, String recipientAccount, int value, LocalDate executionDate) {
        super(senderName, recipientName, senderAccount, recipientAccount, value);
        this.executionDate = executionDate;
    }

    public void checkForCompletion() {
        if (LocalDate.now().isAfter(this.executionDate)) {
            this.completeTransaction();
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                ", " + "executionDate=" + executionDate;
    }
}
