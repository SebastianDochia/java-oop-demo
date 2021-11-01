package com.company.utils;

import com.company.model.DataBundle;
import com.company.model.ScheduledTransaction;
import com.company.model.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Commands {

    public static void createDummy(String fileName) {
        fileName += ".dat";

        try {
            FileOutputStream stream = new FileOutputStream(fileName);
            ObjectOutputStream writer = new ObjectOutputStream(stream);

            Transaction transaction1 = new Transaction(
                    "Alin Buftea",
                    "Domnu Iohannis",
                    "0000000",
                    "0000001",
                    123456
            );

            Transaction transaction2 = new Transaction(
                    "Dumitru Boss",
                    "Domnu Iohannis",
                    "0000004",
                    "0000001",
                    123456
            );

            ScheduledTransaction transaction3 = new ScheduledTransaction(
                    "Dumitru Boss",
                    "Domnu Iohannis",
                    "0000004",
                    "0000001",
                    123456,
                    LocalDate.parse("2018-12-27")
            );

            writer.writeObject(transaction1);
            writer.writeObject(transaction2);
            writer.writeObject(transaction3);

            writer.flush();
            writer.close();
            System.out.println("Dummy file 'dummy' created!");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void write(String fileName) {
        DataBundle bundle = DataBundle.getDataBundle();

        fileName += ".dat";

        try {
            FileOutputStream stream = new FileOutputStream(fileName);
            ObjectOutputStream writer = new ObjectOutputStream(stream);

            for (Transaction transaction: bundle.transactions) {
                writer.writeObject(transaction);
            }
            for (ScheduledTransaction transaction: bundle.scheduledTransactions) {
                writer.writeObject(transaction);
            }
            writer.flush();
            writer.close();
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isShure(Scanner scanner) {
        System.out.println("Are you sure you want to perform this action? y/n");

        String response = scanner.nextLine();

        return response.equalsIgnoreCase("y");
    }
}
