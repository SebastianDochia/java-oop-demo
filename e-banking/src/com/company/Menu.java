package com.company;

import com.company.exceptions.IncorectCommandException;
import com.company.exceptions.IncorrectDateException;
import com.company.exceptions.NegativeValueException;
import com.company.exceptions.TransactionNotFoundException;
import com.company.model.DataBundle;
import com.company.model.ScheduledTransaction;
import com.company.model.Settings;
import com.company.model.Transaction;
import com.company.utils.BinaryReader;
import com.company.utils.Commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Menu {
    private static final DataBundle bundle = DataBundle.getDataBundle();
    private static final HashMap<String, Transaction> transactions = new HashMap<>(bundle.transactions.size());

    public static void mainMenu(Scanner scanner, String dataFile) throws IOException, InterruptedException {
        boolean firstLoad = true;
        Settings settings = Settings.getSettings();

        checkCompletions();

        for (Transaction transaction : bundle.transactions) {
            transactions.put(transaction.getTransactionName(), transaction);
        }

        while(true) {
            if (!firstLoad) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }

            firstLoad = false;

            System.out.println("Welcome " + settings.getUserName() + "!");
            System.out.println("Chose an action by pressing the associated command:\n");
            System.out.println("1 - Transactions");
            System.out.println("2 - Create new transaction");
            System.out.println(":wq - Save & Exit");
            System.out.println(":q - Exit");

            String response = scanner.nextLine();

            switch (response) {
                case "1":
                    transactionsMenu(scanner, dataFile);
                    break;
                case "2":
                    createTransaction(scanner, settings);
                    break;
                case ":q":
                    if (Commands.isShure(scanner)) {
                        System.out.println("Ending session...");
                        System.exit(1);
                    }
                case ":wq":
                    Commands.write(dataFile);
                    System.out.println("Ending session...");
                    System.exit(0);
            }


        }
    }

    private static void createTransaction(Scanner scanner, Settings settings) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

        String senderName = settings.getUserName();
        String senderAccount = settings.getAccount();
        int transactionValue;

        System.out.println("Enter the detail of your transaction or :b to go back");

        System.out.println("Enter recipient name:");
        String recipientName = scanner.nextLine();
        if(recipientName.equals(":b")) {
            return;
        }

        System.out.println("Enter recipient account name:");
        String recipientAccount = scanner.nextLine();
        if(recipientAccount.equals(":b")) {
            return;
        }

        while(true) {
            System.out.println("Enter transaction value:");
            String value = scanner.nextLine();

            if(value.equals(":b")) {
                return;
            }

            try {
                if(Integer.parseInt(value) < 0) {
                    throw new NegativeValueException("Negative value");
                }

                transactionValue = Integer.parseInt(value);
                break;
            } catch (NegativeValueException e) {
                System.out.println("The value cannot be lower than zero.");
            } catch (Exception e) {
                System.out.println("Invalid value.");
            }
        }

        System.out.println("Would you like this transaction to autocomplete in the future? y/n");

        String response = scanner.nextLine();

        if(response.equalsIgnoreCase("y")) {
            LocalDate date = null;

            while(true) {
                System.out.println("Enter the date of the transfer (yyyy-mm-dd):");
                String value = scanner.nextLine();

                try {
                    date = LocalDate.parse(value);

                    if (date.isBefore(LocalDate.now())) {
                        throw new IncorrectDateException("Date not valid");
                    }

                    break;
                } catch (IncorrectDateException e) {
                    System.out.println("Date cannot be earlier than today.");
                } catch (Exception e) {
                    System.out.println("DAte is invalid.");
                }
            }

            ScheduledTransaction newTransaction = new ScheduledTransaction(senderName, recipientName, senderAccount, recipientAccount, transactionValue, date);
            bundle.scheduledTransactions.add(newTransaction);

            System.out.println("Transaction created!");
            scanner.nextLine();

        } else {
            Transaction newTransaction = new Transaction(senderName, recipientName, senderAccount, recipientAccount, transactionValue);
            bundle.transactions.add(newTransaction);
            transactions.put(newTransaction.getTransactionName(), newTransaction);

            System.out.println("Transaction created!");

            if (settings.isAutoConfirm().equals("ON")) {
                transactions.get(newTransaction.getTransactionName()).completeTransaction();
            }

            scanner.nextLine();

        }
    }

    private static void transactionsMenu(Scanner scanner, String dataFile) throws IOException, InterruptedException {
        String[] options = {"-c", "-a"};

        BinaryReader reader = new BinaryReader();

        while(true) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            reader.readBundle();
            System.out.println("Enter the name of the transaction you want to select followed by the operation flag:");
            System.out.println("-c - Cancel the transaction");
            System.out.println("-a - Confirm the transaction");
            System.out.println("Or...");
            System.out.println(":b - Return to Main Menu (changes will not be discarded)");
            System.out.println(":wq - Save & Exit");
            System.out.println(":q - Exit");

            String response = scanner.nextLine();

            if (response.contains(":")) {
                switch (response) {
                    case ":b":
                        return;
                    case ":q":
                        if (Commands.isShure(scanner)) {
                            System.out.println("Ending session...");
                            System.exit(1);
                        }
                    case ":wq":
                        Commands.write(dataFile);
                        System.out.println("Ending session...");
                        System.exit(0);
                }
            } else {
                try {
                    String transactionName = response.substring(0, response.indexOf("-")).replaceAll("\\s+","");
                    String command = response.substring(response.indexOf("-") - 1).replaceAll("\\s+","");

                    if (!Arrays.asList(options).contains(command)) {
                        throw new IncorectCommandException("Command does not exist.");
                    }

                    if(!transactions.containsKey(transactionName)) {
                        throw new TransactionNotFoundException("Could not find transaction.");
                    }

                    if (command.equals("-a")) {
                        if (Commands.isShure(scanner)) {
                            transactions.get(transactionName).completeTransaction();
                        }
                    } else if (command.equals("-c")) {
                        if (Commands.isShure(scanner)) {
                            transactions.get(transactionName).completeTransaction();
                        }
                    }
                    scanner.nextLine();

                    break;
                } catch (IncorectCommandException e) {
                    System.out.println("Invalid command. \n");
                    scanner.nextLine();
                } catch (TransactionNotFoundException e) {
                    System.out.println("Transaction not found. \n");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid input \n");
                    scanner.nextLine();
                }
            }
        }


    }

    private static void checkCompletions() {
        System.out.println("Checking for transactions scheduled to complete...");

        bundle.scheduledTransactions.forEach(ScheduledTransaction::checkForCompletion);
    }
}
