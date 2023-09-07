package oodj;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author htank
 */

import java.io.IOException;
import java.util.Scanner;

public final class Sales {
    
    private boolean running;
    private final Scanner scanner;
    private boolean displayMenu;
    private final UserInputUtility userInputUtility; // Composition

    public Sales() throws IOException {
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.displayMenu = true;
        this.userInputUtility = new UserInputUtility(scanner); // Initialize the UserInputUtility
        start();
    }

    private boolean getUserConfirmation(Scanner scanner1) {
        return userInputUtility.getUserConfirmation();
    }

    private String getUserInput(String prompt) {
        return userInputUtility.getUserInput(prompt);
    }

    private void displayMenu() {

        System.out.println("  _____       _   ");        
        System.out.println(" / ____|     | |     ");     
        System.out.println("| (___   __ _| | ___  ___ ");
        System.out.println(" \\___ \\ / _` | |/ _ \\/ __| ");
        System.out.println(" ____) | (_| | |  __/\\__ \\");
        System.out.println("|_____/ \\__,_|_|\\___||___/");                   
        System.out.println("\n---------------------------------------------------");
        System.out.println("What do you want to do today!");
        System.out.println("---------------------------------------------------");
        System.out.println("Welcome, Sale Manager!\n1. Item Entry Manage.\n2. Supplier Entry Manage.\n3. Daily Item-Wise Sales Entry.\n4. Create Requisition.\n5. List of Purchase Orders.\n0. Logout.");
        System.out.println("---------------------------------------------------");
        System.out.print("Enter your choice: ");
    }

    public void start() throws IOException {
        try (scanner) {
            while (running) {
                int choice;
                if (displayMenu) {
                    displayMenu();
                }
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    displayMenu = true; // Reset the displayMenu flag after successful input
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Enter your choice: ");
                    displayMenu = false; // Don't display the menu again for invalid input
                    continue;
                }

                switch (choice) {
                    case 1 -> itemEntry();
                    case 2 -> supplierEntry();
                    case 3 -> dailyItemsWiseSales();
                    case 4 -> purchaseRequisition();
                    case 5 -> listOfPurchaseOrders();
                    case 0 -> logout();
                    default -> {
                        System.out.println("Invalid number. Please enter a valid option.");
                        displayMenu = false; // Don't display the menu again for invalid input
                    }
                }
            }
        }
        System.out.println("Logged out Successfully. Goodbye!");
    }

    public void itemEntry() throws IOException {
    while (true) {
        System.out.println("\n----------------- Item Entry Menu -----------------");
        System.out.println("\n1. View Items.\n2. Find Specific Items with Name.\n3. Add New Items.\n4. Delete Items.\n5. Edit Item Informations.\n0. Go back to Main Menu\n");
        System.out.println("---------------------------------------------------");
        System.out.print("Enter your choice: ");

        String submenuChoiceStr = scanner.nextLine();

        // Validate input before parsing
        if (!submenuChoiceStr.matches("\\d+")) {
            System.out.println("Invalid input. Please enter a number.");
            continue;
        }

        int submenuChoice = Integer.parseInt(submenuChoiceStr);

        switch (submenuChoice) {
            case 1 -> {
                System.out.println("\n-------------- View All Items -------------- ");
                Items items = new Items();
                items.view();
                // Ask the user to enter something before breaking
                System.out.println("Press Enter to continue...\n");
                scanner.nextLine(); // Wait for user to press Enter
            }
            case 2 -> {     
                System.out.println("\n-------------- Find Specific Item Information -------------- ");
                Items items = new Items();
                System.out.print("Enter a keyword to search for specific items: ");
                String filter = scanner.nextLine();
                boolean itemsFound = items.view(filter);
                if (!itemsFound) {
                    System.out.println("\nNo such items.");
                }
            }
            case 3 -> {
                System.out.println("\n-------------- Add New Items -------------- ");
                Items items = new Items();
                items.add();
            }
            case 4 -> {
                System.out.println("\n-------------- Delete item -------------- ");
                Items items = new Items();
                items.view(); // Display the item list before deletion

                System.out.print("Enter the Code of the item to delete (Enter to Cancel Process): ");
                String itemCodeToDelete = scanner.nextLine().trim();

                if (!itemCodeToDelete.isEmpty()) {
                    items.delete(itemCodeToDelete);
                } else {
                    System.out.println("Deletion process canceled.\n");
                }
                break;
            }
            case 5 -> {
                System.out.println("\n-------------- Edit Item Information -------------- ");
                Items item = new Items();
                item.view();
                System.out.print("\nEnter the Code of the item to edit: ");
                String itemCodeToEdit = scanner.nextLine();

                // Check if the item exists before asking for new information
                if (item.check(itemCodeToEdit)) {
                    // Get user input for editing and check confirmation
                        boolean confirmed = getUserConfirmation(scanner);

                        if (confirmed) {
                            System.out.println("\n-------------- New Item Information -------------- ");
                            String newName = getUserInput("Enter new name: ");
                            String newCategory = getUserInput("Enter new category: ");
                            String newPrice = getUserInput("Enter new price: ");
                            String newAvailability = getUserInput("Enter Available or NoStock: ");
                            String newDescription = getUserInput("Enter new description: ");
                            System.out.println("---------------------------------------------------");

                            // Proceed with the edit
                            item.edit(itemCodeToEdit, newName, newCategory, newPrice, newAvailability, newDescription);
                        } else {
                            System.out.println("\nEdit process canceled.\n");
                        }
                    } else {
                    System.out.println("\nThere's no such item to edit.\n");
                }
            }


            case 0 -> {
                // Exit the loop to go back to the main menu
                return;
            }
            default -> {
                System.out.println("Invalid number. Please enter a valid option.");
            }
        }
    }
}
        
    public void supplierEntry() throws IOException {
    while (true) {
        System.out.println("\n----------------- Suppliers Entry Menu -----------------");
        System.out.println("1. View Suppliers.");
        System.out.println("2. Find Specific Suppliers by Company Name.");
        System.out.println("3. Add Supplier.");
        System.out.println("4. Delete Supplier Information.");
        System.out.println("5. Edit Supplier Information.");
        System.out.println("0. Go back to Main Menu");
        System.out.println("----------------------------------------------------------");
        System.out.print("\nEnter your choice: ");
        
        String submenuChoiceStr = scanner.nextLine();

        // Validate input before parsing
        if (!submenuChoiceStr.matches("\\d+")) {
            System.out.println("Invalid input. Please enter a number.");
            continue;
        }

        int submenuChoice = Integer.parseInt(submenuChoiceStr);

        switch (submenuChoice) {
            case 1 -> {
                System.out.println("\n-------------- View All Suppliers -------------- ");
                Suppliers suppliers = new Suppliers();
                suppliers.view();
                System.out.println("\nPress Enter to continue...\n");
                scanner.nextLine(); // Wait for user to press Enter
            }
            case 2 -> {
                System.out.println("\n-------------- Find Specific Suppliers by Company Name -------------- ");
                Suppliers suppliers = new Suppliers();
                System.out.print("\nEnter a company name to search suppliers: ");
                String filter = scanner.nextLine();
                boolean suppliersFound = suppliers.view(filter);
                if (!suppliersFound) {
                    System.out.println("\nNo such suppliers found.\n");
                }
            }
            case 3 -> {
                System.out.println("\n-------------- Add New Supplier Information -------------- ");
                Suppliers suppliers = new Suppliers();
                suppliers.add();
            }
            case 4 -> {
                System.out.println("\n-------------- Delete Supplier Information -------------- ");
                Suppliers suppliers = new Suppliers();
                suppliers.view();
                System.out.print("\nEnter the name of the Supplier Name to delete: ");
                String supNameToDelete = scanner.nextLine();
                suppliers.delete(supNameToDelete);
            }
            case 5 -> {
                System.out.println("\n-------------- Edit Supplier Information -------------- ");
                Suppliers suppliers = new Suppliers();
                suppliers.view();
                System.out.print("Enter the Supplier ID to edit Supplier Information: ");
                String supplierIdToEdit = scanner.nextLine();
                if (suppliers.check(supplierIdToEdit)) {
                    boolean confirmed = getUserConfirmation(scanner);

                    if (confirmed) {
                        System.out.println("\n-------------- New Suppliers Information -------------- ");
                        String newSupplierName = getUserInput("Enter new Supplier name: ");
                        String newSupplierContact = getUserInput("Enter new Supplier Contact: ");
                        String newSupplierEmail = getUserInput("Enter new Supplier Email: ");
                        String newSupplierPhone = getUserInput("Enter new Supplier Phone: ");
                        String newSupplierAddress = getUserInput("Enter new Supplier Address: ");
                        String newSupplierWebsite = getUserInput("Enter new Supplier Website: ");
                        System.out.println("------------------------------------------------------- ");

                        suppliers.edit(supplierIdToEdit, newSupplierName, newSupplierContact, newSupplierEmail, newSupplierPhone, newSupplierAddress, newSupplierWebsite);
                    } else {
                        System.out.println("\nEdit process canceled.\n");
                    }
                } else {
                    System.out.println("\nSupplier not found. Please enter a valid supplier name.\n");
                }          
            }
            case 0 -> {
                return; // Exit the loop to go back to the main menu
            }
            default -> {
                System.out.println("\nInvalid number. Please enter a valid option.\n");
            }
        }
    }
}
  
    public void dailyItemsWiseSales() throws IOException {
        while (true) {
        System.out.println("\n----------------- Daily Item-Wise Sales Menu -----------------");
        System.out.println("1. View.\n2. Find.\n3. Add\n4. Delete\n5. Edit\n0. Go back to Main Menu\n");
        System.out.print("Enter your choice: ");
        String submenuChoiceStr = scanner.nextLine();
        // Validate input before parsing
        if (!submenuChoiceStr.matches("\\d+")) {
            System.out.println("Invalid input. Please enter a number.");
            continue;
        }
        int submenuChoice = Integer.parseInt(submenuChoiceStr);

        switch (submenuChoice) {
            case 1 -> {
                System.out.println("\n-------------- View All Daily Item-Wise Sales -------------- ");
                DailyItemsSale DIS = new DailyItemsSale();
                DIS.view();
                // Ask the user to enter something before breaking
                System.out.println("Press Enter to continue...\n");
                scanner.nextLine(); // Wait for user to press Enter
            }
            case 2 -> {
                System.out.println("\n-------------- to find Specific Daily Items Sale Information -------------- ");
                DailyItemsSale DIS = new DailyItemsSale();
                System.out.print("Enter a Date to search list of Daily-Wise Sale Items (dd-mm-yyyy): ");
                String filter = scanner.nextLine();
                boolean itemsFound = DIS.view(filter);
                if (!itemsFound) {
                    System.out.println("\nNo such items.");
                }
            }
            case 3 -> {
                System.out.println("\n-------------- Add Daily Item Sales -------------- ");
                DailyItemsSale DIS = new DailyItemsSale();
                DIS.add();
            }
            case 4 -> {
                System.out.println("\n-------------- Delete Daily Item-Wise Sales -------------- ");
                DailyItemsSale DIS = new DailyItemsSale();
                DIS.view(); // Display the item list before deletion

                System.out.print("Enter the Code of the Daily Item-Wise Sale item to delete (Enter to Cancel Process): ");
                String itemCodeToDelete = scanner.nextLine().trim();

                if (!itemCodeToDelete.isEmpty()) {
                    DIS.delete(itemCodeToDelete);
                } else {
                    System.out.println("Deletion process canceled.\n");
                }
                break;
            }
            case 5 -> {
                System.out.println("\n-------------- Edit Daily Item-Wise Sale Information -------------- ");
                DailyItemsSale DIS = new DailyItemsSale();
                DIS.view();
                System.out.print("\nEnter the Code of the Daily Item-Wise Sale to edit: ");
                String itemCodeToEdit = scanner.nextLine();

                // Check if the item exists before asking for new information
                if (DIS.check(itemCodeToEdit)) {
                    // Get user input for editing and check confirmation
                        boolean confirmed = getUserConfirmation(scanner);

                        if (confirmed) {
                            String newName = getUserInput("Enter new name: ");
                            String newDescription = getUserInput("Enter new description: ");
                            String newQty = getUserInput("Enter new Quantity: ");
                            String newRev = getUserInput("Enter new Revenue: ");
                            // Proceed with the edit
                            DIS.edit(itemCodeToEdit, newName, newDescription, newQty, newRev);
                        } else {
                            System.out.println("\nEdit process canceled.\n");
                        }
                    } else {
                    System.out.println("\nThere's no such item to edit.\n");
                }
            }


            case 0 -> {
                // Exit the loop to go back to the main menu
                return;
            }
            default -> {
                System.out.println("Invalid number. Please enter a valid option.");
            }
        }
    }
    }
    
    public void purchaseRequisition() {
        System.out.println("\n----------------- Purchase Requisition Menu -----------------");
        // Add submenu options and logic here
        System.out.println("Click Enter to Go Back Main Menu.");
        String submenuChoice = scanner.nextLine();
        if (submenuChoice.isEmpty()) {
            System.out.print("Go back to Main Menu? (Yes/No): ");
            String yesNo = scanner.nextLine().toLowerCase();
            if (yesNo.equals("yes")) {
                displayMenu = true; // Go back to the main menu
            }
        } else {
            System.out.println("Invalid number. Please enter a valid option.");
        }
    }
    
    public void listOfPurchaseOrders() {
        System.out.println("\n-------------- List of Purchase Orders Submenu -------------- ");
        // Add submenu options and logic here
        System.out.println("Click Enter to Go Back Main Menu.");
        String submenuChoice = scanner.nextLine();
        if (submenuChoice.isEmpty()) {
            System.out.print("Go back to Main Menu? (Yes/No): ");
            String yesNo = scanner.nextLine().toLowerCase();
            if (yesNo.equals("yes")) {
                displayMenu = true; // Go back to the main menu
            }
        } else {
            System.out.println("Invalid number. Please enter a valid option.");
        }
    }
    
    public void logout() {
        running = false;
    }
    
}
