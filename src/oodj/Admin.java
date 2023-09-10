/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

/**
 * @author BEN
 */

//filepath
// "/Users/ben/Desktop/OODJ/username.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/username.txt"

import java.io.IOException;
import java.util.Scanner;

public class Admin extends Users {
    Scanner sc = new Scanner(System.in);
    String filename = "/Users/ben/Desktop/OODJ/username.txt";
    private String username;
    private final UserInputUtility userInputUtility;

    public Admin(String username) {
        this.username = username;
        this.userInputUtility = new UserInputUtility(sc);

        while (true) { // Add an outer while loop to restart the admin menu
            try {
                System.out.println("              _           _       _     _             _              ");
                System.out.println("    /\\      | |         (_)     (_)   | |           | |            ");
                System.out.println("   /  \\   __| |_ __ ___  _ _ __  _ ___| |_ _ __ __ _| |_ ___  _ __ ");
                System.out.println("  / /\\\\/  _` | '_ ` _ \\| | '_ \\| / __| __| '__/ _` | __/ _ \\| '__|");
                System.out.println(" / ____ \\ (_| | | | | | | | | | | \\__ \\ |_| | | (_| | || (_) | |   ");
                System.out.println("/_/    \\_\\__ ,_|_| |_| |_|_|_| |_|_|___/\\__|_|  \\__,_|\\__\\___/|_|    ");

                System.out.println("\n---------------------------------------------------");
                System.out.println(" What do you what to do today! ");
                System.out.println("---------------------------------------------------");
                System.out.println(" 1. Account Management ");
                System.out.println(" 2. Sales Manager ");
                System.out.println(" 3. Purchase Manager ");
                System.out.println(" 4. View Daily Report ");
                System.out.println(" 0. Logout ");
                System.out.println("---------------------------------------------------");

                System.out.print(" please enter  choice ( the number only ) :  ");
                String choice = sc.nextLine();

                if (choice.equals("1")) {
                    adminLoop();
                } else if (choice.equals("2")) {
                    Sales sales = new Sales("admin");
                } else if (choice.equals("3")) {
                    PM pm = new PM("admin");
                } else if (choice.equals("4")) {
                    DailyReport report = new DailyReport();
                } else if (choice.equals("0")) {
                    System.out.println("Bye Admin, Have a nice day");
                    System.exit(0);
                } else {
                    System.out.println("Invalid Input");
                }
            } catch (IOException ex) {
                // Handle exceptions
            }
        }
    }

    private String getUserInput(String prompt) {
        return userInputUtility.getUserInput(prompt);
    }

    private void adminLoop() throws IOException {
        boolean returnToMainMenu = false; // Add a flag to control whether to return to the main menu

        while (!returnToMainMenu) { // Use the flag as the exit condition
            System.out.println("---------------------------------------------");
            System.out.println("1. Registration");
            System.out.println("2. Delete User Account");
            System.out.println("3. Edit User Account");
            System.out.println("4. View User Account List");
            System.out.println("0. To Admin Menu");
            System.out.println("---------------------------------------------");
            System.out.print("Enter number here : ");

            if (sc.hasNextLine()) { // Check if there's more input available
                int select = Integer.parseInt(sc.nextLine());

                switch (select) {
                    case 1: {
                        super.viewUseraccount();
                        super.createaccount();
                        super.viewUseraccount();
                        break;
                    }
                    case 2: {
                        System.out.println("\n------------ Delete User Account ------------\n");
                        super.viewUseraccount();
                        System.out.print("Enter the Code of the Account User to delete : ");
                        String accountCodeToDelete = sc.nextLine().trim();
                        super.DeleteAccount(accountCodeToDelete);
                        super.viewUseraccount();
                        break;
                    }
                    case 3: {
                        System.out.println("\n------------ Edit User Account ------------\n");
                        super.viewUseraccount();
                        System.out.print("\nEnter the Code of the user account to edit: ");
                        String accountCodeToEdit = sc.nextLine();

                        // Check if the item exists before asking for new information
                        if (super.check(accountCodeToEdit)) {
                            boolean confirmed = true;
                            // Assuming you have a confirmed boolean variable set appropriately
                            if (confirmed) {
                                String newName = getUserInput("Enter new name: ");
                                String newPassword = getUserInput("Enter new password: ");
                                String newRole = getUserInput("Enter new role: ");

                                // Proceed with the edit
                                super.EditAccount(accountCodeToEdit, newName, newPassword, newRole);
                            } else {
                                System.out.println("\nEdit process canceled.\n");
                            }
                        } else {
                            System.out.println("\nThere's no such user account to edit.\n");
                        }
                        super.viewUseraccount();
                        break;
                    }

                    case 4: {
                        super.viewUseraccount();
                        break;
                    }
                    case 0: {
                        // Set the flag to return to the main menu
                        returnToMainMenu = true;
                        break;
                    }

                    default:
                        System.out.println("Wrong Choice please enter only numbers");
                }
            } else {
                System.out.println("No input found.");
                returnToMainMenu = true; // Return to the main menu on no input
            }
        }
    }
}
