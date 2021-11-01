package com.company.utils;

import com.company.model.DataBundle;
import com.company.model.ScheduledTransaction;
import com.company.model.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryReader {

    /**
     * Reads a binary file and loads it into a singleton instance of DataBundle.
     * @param fileName - Binary file that should be read.
     */
    public void loadDataFromFile(String fileName) {
        fileName += ".dat";
        List<ScheduledTransaction> scheduledTransactions = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        try {
            FileInputStream stream = new FileInputStream(fileName);
            ObjectInputStream reader = new ObjectInputStream(stream);

            while(true) {
                Object obj = null;

                try {
                    obj = reader.readObject();
                } catch (EOFException e) {
                    reader.close();
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if (!(obj instanceof ScheduledTransaction)) {
                    transactions.add((Transaction) obj);
                } else {
                    scheduledTransactions.add((ScheduledTransaction) obj);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.out.println("Ending session...");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("ERROR reading from file.");
            System.out.println("Ending session...");
            System.exit(0);
        }

        DataBundle bundle = DataBundle.getDataBundle();
        bundle.setScheduledTransactions(scheduledTransactions);
        bundle.setTransactions(transactions);
    }

    /**
     * Prints the DataBundle Contents.
     */
    public void readBundle() {
        DataBundle bundle = DataBundle.getDataBundle();
        System.out.println("Transactions: ");
        bundle.transactions.forEach(System.out::println);
        System.out.println("\nScheduled Transactions: ");
        bundle.scheduledTransactions.forEach(System.out::println);
        System.out.println("\n");
    }
}
