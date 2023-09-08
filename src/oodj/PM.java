/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

/**
 *
 * @author BEN
 */
import java.io.IOException;
import java.util.Scanner;

public final class PM {
    
    private boolean running;
    private final Scanner scanner;
    private boolean displayMenu;
    private final UserInputUtility userInputUtility; // Composition
    
    public PM() throws IOException {
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
        System.out.println("Welcome, Sale Manager!\n1. View Item List\n2. View Supplier List.\n3. Display Requsition.\n4. Manage Purchase Order.\n0. Logout.\n");
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
                    case 3 -> purchaseRequisition();
                    case 4 -> listOfPurchaseOrders();
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
        System.out.println("\n----------------- Item Entry Menu -----------------\n1. View Items.\n2. Find Specific Items with Name.\n0. Go back to Main Menu\n---------------------------------------------------");
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
        System.out.println("1. View Suppliers.\n2. Find Specific Suppliers by Company Name.\n0. Go back to Main Menu\n----------------------------------------------------------\n");
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
            case 0 -> {
                return; // Exit the loop to go back to the main menu
            }
            default -> {
                System.out.println("\nInvalid number. Please enter a valid option.\n");
            }
        }
    }
}
  
    public void purchaseRequisition() {
        while (true) {
        System.out.println("\n----------------- Purchase Requisition Menu -----------------\n1. View Suppliers.\n2. Find Specific Suppliers by Company Name.\n0. Go back to Main Menu\n----------------------------------------------------------\n");
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
                System.out.println("View All Purchase Requisitions.");
                PR pr = new PR();
                pr.view();
                // Ask the user to enter something before breaking
                System.out.println("Press Enter to continue...\n");
                scanner.nextLine(); // Wait for the user to press Enter
            }
            case 2 -> {
                System.out.println("Input date (dd-mm-yyyy) to find Specific Purchase Requisition Information.");
                PR pr = new PR();
                System.out.print("Enter a Date to search list of Purchase Requisitions: ");
                String filter = scanner.nextLine();
                boolean itemsFound = pr.view(filter);
                if (!itemsFound) {
                    System.out.println("\nNo such items.");
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
    
    public void listOfPurchaseOrders() {
    while (true) {
        System.out.println("\n----------------- Purchase Order List Menu -----------------\n1. View.\n2. Find.\n3. Add\n4. Delete\n5. Edit\n0. Go back to Main Menu\n");
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
                System.out.println("\nView All Purchase Orders.");
                PO po = new PO();
                po.view();
                // Ask the user to enter something before breaking
                System.out.println("Press Enter to continue...\n");
                scanner.nextLine(); // Wait for the user to press Enter
            }
            case 2 -> {
                System.out.println("Input date (dd-mm-yyyy) to find Specific Purchase Order Information.");
                PO po = new PO();
                System.out.print("Enter a Date to search for Purchase Orders: ");
                String filter = scanner.nextLine();
                boolean itemsFound = po.view(filter);
                if (!itemsFound) {
                    System.out.println("\nNo purchase orders found for the given date.");
                }
            }
            case 3 -> {
                System.out.println("\nCreate a New Purchase Order.");
                PO po = new PO();
                po.add();
            }
            case 4 -> {
                System.out.println("Delete Purchase Order.");
                PO po = new PO();
                po.view(); // Display the list of purchase orders before deletion

                System.out.print("Enter the Purchase Order ID to delete (Press Enter to Cancel): ");
                String poIDToDelete = scanner.nextLine().trim();

                if (!poIDToDelete.isEmpty()) {
                    po.delete(poIDToDelete);
                } else {
                    System.out.println("Deletion process canceled.\n");
                }
            }
            case 5 -> {
            System.out.println("\n-------------- Edit Purchase Order Information -------------- ");
            PO po = new PO();
            po.view();
            System.out.print("\nEnter the Item Code of the Purchase Order to edit: ");
            String itemCodeToEdit = scanner.nextLine();

            // Check if the purchase order exists before asking for new information
            if (po.check(itemCodeToEdit)) {
                // Get user input for editing and check confirmation
                boolean confirmed = getUserConfirmation(scanner);

                if (confirmed) {
                    String newProductName = getUserInput("Enter new product name: ");
                    String newSupplier = getUserInput("Enter new supplier: ");
                    String newQuantity = getUserInput("Enter new quantity: ");
                    String newPrice = getUserInput("Enter new price: ");
                    String newDescription = getUserInput("Enter new description: ");

                    // Proceed with the edit
                    po.edit(itemCodeToEdit, newProductName, newSupplier, newQuantity, newPrice, newDescription);
                } else {
                    System.out.println("\nEdit process canceled.\n");
                }
            } else {
                System.out.println("\nThere's no such purchase order to edit.\n");
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

    public void logout() {
        running = false;
    }
}
