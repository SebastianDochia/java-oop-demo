package com.company;

import com.company.model.Settings;
import com.company.utils.ArgHandler;
import com.company.utils.BinaryReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EBanking {

    public static void main(String[] args) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        Scanner scanner = new Scanner(System.in);

        if(args.length > 0) {
            ArgHandler handler = new ArgHandler(args);

            if(args.length == 2) {
                handler.handleArg(1);
            } else {
                handler.handleArg(0);
                String dataFile = args[0];

                System.out.println("Starting the App with file \"" + dataFile + "\"");
                loadSettings(false, scanner);
                launchApp(scanner, dataFile);
            }

        } else {
            System.out.println("Starting the App with default file...");

            BinaryReader reader = new BinaryReader();
            loadSettings(true, scanner);
            Settings settings = Settings.getSettings();
            String dataFile = settings.getDefaultFile();

            reader.loadDataFromFile(dataFile);

            launchApp(scanner, dataFile);
        }

        scanner.close();
    }

    private static void launchApp(Scanner scanner, String dataFile) throws IOException, InterruptedException {
        Menu.mainMenu(scanner, dataFile);
    }

    private static void loadSettings(boolean shouldCheckForFile, Scanner scanner) {
        List<String> list;
        try (Stream<String> stream = Files.lines(Paths.get("C:\\Users\\JoeBeleaua\\IdeaProjects\\e-banking\\src\\settings"))) {
            list = stream.collect(Collectors.toList());

            Settings settings = Settings.getSettings();

            settings.setUserName(list.get(0));
            settings.setAccount(list.get(1));
            settings.setAutoConfirm(list.get(2));
            if(list.size() > 3) {
                settings.setDefaultFile(list.get(3));
            } else if(shouldCheckForFile){
                System.out.println("No default file set.");
                System.out.println("Would you like to set one now? y/n");

                String response = scanner.nextLine();

                if (response.equalsIgnoreCase("y")) {
                    System.out.println("Enter the file name or path.");
                    String fileName = scanner.nextLine();

                    settings.setDefaultFile(fileName);
                } else {
                    System.out.println("No default file set.");
                    System.out.println("To run the App on a specific file type the name of the file as an argument to the launch command.");
                    System.out.println("Ending session...");
                    System.exit(0);
                }

            }

        } catch (IOException e) {
            System.out.println("ERROR loading settings.");
            System.out.println("Ending session...");
            System.exit(0);
        }


    }
}
