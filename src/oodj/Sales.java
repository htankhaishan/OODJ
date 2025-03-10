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
    private final String userRole;
    private boolean running;
    private final Scanner scanner;
    private boolean displayMenu;
    private final UserInputUtility userInputUtility; // Composition
    
    public Sales(String userRole) throws IOException {
        this.userRole = userRole;
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

        System.out.println("   _____       _            __  __                                  ");        
        System.out.println("  / ____|     | |          |  \\/  |                                 ");     
        System.out.println(" | (___   __ _| |___  ___  | \\  / | __ _ _ __   __ _  __ _  ___ _ __");
        System.out.println("  \\___ \\ / _` | / __|/ _ \\ | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|");
        System.out.println("  ____) | (_| | \\__ \\  __/ | |  | | (_| | | | | (_| | (_| |  __/ |  ");
        System.out.println(" |_____/ \\__,_|_|___/\\___| |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|  "); 
        System.out.println("                                                      __/ |         ");
        System.out.println("                                                     |___/          ");
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
        
        
            System.out.println("Logged out Successfully. Goodbye!");
        }
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
        System.out.println("2. Find Specific Suppliers by Supplied Item Name (E.g, Coffee, Milk).");
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
                System.out.println("\n-------------- Find Specific Suppliers by Items Name -------------- ");
                Suppliers suppliers = new Suppliers();
                System.out.print("\nEnter a item name to search suppliers: ");
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
                System.out.print("\nEnter the code ID of the Supplier Name to delete: ");
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
        System.out.println("1. View Daily Item-Wise Sales");
        System.out.println("2. Find Specific Daily Item-Wise Sales by Item Name (E.g, Coffee, Milk).");
        System.out.println("3. Add Daily Item-Wise Sales");
        System.out.println("4. Delete Daily Item-Wise Sales Information.");
        System.out.println("5. Edit Daily Item-Wise Sales Information.");
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

                        int newQty = 0;
                        boolean validQty = false;
                        do {
                            try {
                                // Read and convert user input to int for new quantity
                                newQty = Integer.parseInt(getUserInput("Enter new Quantity: "));
                                validQty = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input for Quantity. Please enter a valid integer.");
                            }
                        } while (!validQty);

                        double newRev = 0.0;
                        boolean validRev = false;
                        do {
                            try {
                                // Read and convert user input to double for new revenue
                                newRev = Double.parseDouble(getUserInput("Enter new Revenue: "));
                                validRev = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input for Revenue. Please enter a valid number.");
                            }
                        } while (!validRev);

                        // Proceed with the edit, passing newQty as an int
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
        while (true) {
        System.out.println("\n----------------- Purchase Requisition Menu -----------------");
        System.out.println("1. View Purchase Requisition");
        System.out.println("2. Find Specific Purchase Requisition by Date");
        System.out.println("3. Add Purchase Requisition");
        System.out.println("4. Delete Purchase Requisition Information");
        System.out.println("5. Edit Purchase Requisition Information");
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
            case 3 -> {
                System.out.println("\nAdd Purchase Requisition.");
                PR pr = new PR();
                pr.add();
            }
            case 4 -> {
                System.out.println("Delete Purchase Requisition.");
                PR pr = new PR();
                pr.view(); // Display the PR list before deletion

                System.out.print("Enter the PR ID of the item to delete (Enter to Cancel Process): ");
                String prIDToDelete = scanner.nextLine().trim();

                if (!prIDToDelete.isEmpty()) {
                    pr.delete(prIDToDelete);
                } else {
                    System.out.println("Deletion process canceled.\n");
                }
            }
            case 5 -> {
                System.out.println("Edit Purchase Requisition Information...");
                PR pr = new PR();
                pr.view();
                System.out.print("\nEnter the PR ID to edit: ");
                String prIDToEdit = scanner.nextLine();

                // Check if the PR exists before asking for new information
                if (pr.check(prIDToEdit)) {
                    // Get user input for editing and check confirmation
                    boolean confirmed = getUserConfirmation(scanner);

                    if (confirmed) {
                        String requester = getUserInput("Enter new Requester name: ");
                        String newName = getUserInput("Enter new product name: ");

                        int newQty = 0;
                        boolean validQty = false;
                        do {
                            try {
                                // Read and convert user input to int for new quantity
                                newQty = Integer.parseInt(getUserInput("Enter new quantity: "));
                                validQty = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input for quantity. Please enter a valid integer.");
                            }
                        } while (!validQty);

                        double newPrice = 0.0;
                        boolean validPrice = false;
                        do {
                            try {
                                // Read and convert user input to double for new price
                                newPrice = Double.parseDouble(getUserInput("Enter new price: "));
                                validPrice = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input for price. Please enter a valid number.");
                            }
                        } while (!validPrice);

                        String newDescription = getUserInput("Enter new description: ");

                        // Proceed with the edit
                        pr.edit(prIDToEdit, requester, newName, newDescription, newQty, newPrice);
                    } else {
                        System.out.println("\nEdit process canceled.\n");
                    }
                } else {
                    System.out.println("\nThere's no such PR to edit.\n");
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
        
        System.out.println("\n----------------- Purchase Order Menu -----------------");
        System.out.println("1. View Purchase Order");
        System.out.println("2. Find Specific Purchase Order by Date");
        System.out.println("0. Go Back to Main Menu");
        
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
        if ("sales".equals(userRole)) {
            running = false;
        } else {
            running = false;
            Admin admin = new Admin("admin");
        }
    }
    

    
}
