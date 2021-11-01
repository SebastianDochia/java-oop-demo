package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class DataBundle {
    public List<ScheduledTransaction> scheduledTransactions = new ArrayList<>();
    public List<Transaction> transactions = new ArrayList<>();

    private static final DataBundle instance = new DataBundle();

    public static DataBundle getDataBundle() {
        return instance;
    }

    public void setScheduledTransactions(List<ScheduledTransaction> scheduledTransactions) {
        this.scheduledTransactions = scheduledTransactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    private DataBundle() {}
}
