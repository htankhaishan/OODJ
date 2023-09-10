/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 *
 * @author BEN
 //filepath
// "/Users/ben/Documents/OOPJAVA/OODJ/PO.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/PO.txt"
*/


public class PO extends manage {
    private static final String FILENAME = "/Users/ben/Desktop/OODJ/purchaseOrders.txt";
    private final UserInputUtility userInputUtility;

    public PO() {
        this.userInputUtility = new UserInputUtility(new Scanner(System.in));
    }

    private String date, supplier, itemCode, productName, quantity, price, description;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    public class DateTimeGenerator {
        public static String getCurrentDate() {
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return now.format(formatter);
        }
    }
    
    private String generateRandomItemCode() {
        long nanoTime = System.nanoTime();
        String nanoTimeString = String.valueOf(nanoTime);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(nanoTimeString);
        int randomCodeLength = 7;
        for (int i = 0; i < randomCodeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }
        return codeBuilder.toString().substring(10, 18);
    }
    
    @Override
    public void add() {
    try (FileWriter writer = new FileWriter(FILENAME, true);
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = now.format(dateFormat);

        String itemCode = generateRandomItemCode();

        System.out.print("Enter the product name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter the supplier: ");
        String supplier = scanner.nextLine();

        int quantity = 0;
        boolean validQuantity = false;
        while (!validQuantity) {
            try {
                System.out.print("Enter the quantity: ");
                quantity = Integer.parseInt(scanner.nextLine());
                validQuantity = true; // Input is valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for quantity.");
            }
        }

        double price = 0;
        boolean validPrice = false;
        while (!validPrice) {
            try {
                System.out.print("Enter the price: ");
                price = Double.parseDouble(scanner.nextLine());
                validPrice = true; // Input is valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid double for price.");
            }
        }

        System.out.print("Enter the description: ");
        String description = scanner.nextLine();

        System.out.println("\nPlease review the entered data:");
        System.out.println("Date: " + date);
        System.out.println("PO ID: " + itemCode);
        System.out.println("Product Name: " + productName);
        System.out.println("Supplier: " + supplier);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + price);
        System.out.println("Description: " + description);

        System.out.print("Do you want to save this PO? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            String poData = date + "," + itemCode + "," + productName + "," + supplier + "," + quantity + "," + price + "," + description;
            bufferedWriter.write(poData);
            bufferedWriter.newLine();
            System.out.println("\nPO saved successfully.\n");
        } else {
            System.out.println("\nPO not saved.\n");
        }

    } catch (IOException e) {
        System.out.println("\nAn error occurred while saving the PO.\n");
    }
}

    @Override
    public void delete(String poIDToDelete) {
        try (Scanner scanner = new Scanner(new File(FILENAME));
             FileWriter writer = new FileWriter(FILENAME + ".tmp");
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            boolean poDeleted = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] poInfo = line.split(",");
                String poIDFromFile = poInfo[1].trim();

                if (!poIDFromFile.equalsIgnoreCase(poIDToDelete)) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                } else {
                    poDeleted = true;
                }
            }

            if (poDeleted) {
                System.out.println("\nPO deleted successfully.\n");
            } else {
                System.out.println("\nPO not found.\n");
            }

        } catch (IOException e) {
            System.out.println("\nAn error occurred while deleting the PO.\n");
        }

        File originalFile = new File(FILENAME);
        File temporaryFile = new File(FILENAME + ".tmp");

        if (temporaryFile.renameTo(originalFile)) {
            System.out.println("Data File renamed successfully.\n");
        } else {
            System.out.println("Failed to rename Data file.\n");
        }
    }

    @Override
    public void view() {
        List<String> poList = readPOListFromFile(FILENAME);

        int numColumns = 7;
        int[] maxColumnWidths = new int[numColumns];
        List<String[]> formattedPOList = new ArrayList<>();

        for (String po : poList) {
            String[] poInfo = po.split(",");
            formattedPOList.add(poInfo);

            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], poInfo[i].length());
            }
        }

        String separator = " -----------------------------------------------------------------------------------------------------------------------------";
        String format = "| %-" + (maxColumnWidths[0] + 3) + "s | %-" + (maxColumnWidths[1] + 2) + "s | %-" + (maxColumnWidths[2] + 2) + "s | %-" + (maxColumnWidths[3] + 6) + "s | %-" + (maxColumnWidths[4] + 2) + "s | %-" + (maxColumnWidths[5] + 3) + "s | %-" + (maxColumnWidths[6] + 3) + "s |";

        System.out.println("Purchase Orders List:");
        System.out.println(separator);
        System.out.printf(format, "Date", "PO ID", "Name", "Supplier", "Qty", "Price", "Description");
        System.out.println();
        System.out.println(separator);

        boolean itemsFound = false;

        for (String po : poList) {
            String[] poInfo = po.split(",");

            if (poInfo.length >= numColumns) {
                formattedPOList.add(poInfo);

                for (int i = 0; i < numColumns; i++) {
                    maxColumnWidths[i] = Math.max(maxColumnWidths[i], poInfo[i].length());
                }

                System.out.printf(format, poInfo[0], poInfo[1], poInfo[2], poInfo[3], poInfo[4], poInfo[5], poInfo[6]);
                System.out.println();
                itemsFound = true;
            } else {
                System.out.println("Incomplete entry detected: " + po);
            }
        }

        System.out.println(separator);

        if (!itemsFound) {
            System.out.println("No POs found.");
        }
    }
    public static List<String> readPOListFromFile(String FILENAME) {
        List<String> poList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                poList.add(line);
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while reading PO data.\n");
        }
        return poList;
    }

    public static String formatTableCell(String cellContent) {
        int cellWidth = 15;
        if (cellContent.length() <= cellWidth) {
            return cellContent;
        } else {
            return cellContent.substring(0, cellWidth - 3) + "...";
        }
    }

    public boolean view(String filter) {
        List<String> poList = readPOListFromFile(FILENAME);

        int numColumns = 6;
        int[] maxColumnWidths = new int[numColumns];
        List<String[]> formattedPOList = new ArrayList<>();

        boolean itemsFound = false;

        for (String po : poList) {
            String[] poInfo = po.split(",");

            if (poInfo.length >= numColumns) {
                formattedPOList.add(poInfo);

                for (int i = 0; i < numColumns; i++) {
                    maxColumnWidths[i] = Math.max(maxColumnWidths[i], poInfo[i].length() + 2); // Adding extra padding
                }

                if (poInfo[0].contains(filter)) {
                    itemsFound = true;
                }
            } else {
                System.out.println("Incomplete entry detected: " + po);
            }
        }
        
        String format = "| %-" + (maxColumnWidths[0] + 3) + "s | %-" + (maxColumnWidths[1] + 3) + "s | %-" + (maxColumnWidths[2] + 3) + "s | %-" + (maxColumnWidths[3] + 4) + "s | %-" + (maxColumnWidths[4] + 2) + "s | %-" + (maxColumnWidths[5] + 2) + "s |";
        String separator = " --------------------------------------------------------------------------------------------------";

        System.out.println("Purchase Requisitions List:");
        System.out.println(separator);        
        System.out.printf(format, "Date","PR ID","Product Name","Supplier", "Qty", "Price" );
        System.out.println();
        System.out.println(separator);

        for (String[] prInfo : formattedPOList) {
            if (prInfo[0].contains(filter)) {
            System.out.printf(format, prInfo[0], prInfo[1], prInfo[2], prInfo[3], prInfo[4], prInfo[5]); // Displaying "Supplier ID"
            System.out.println();
            itemsFound = true;
            }
        }

        System.out.println(separator);
        
        
        return itemsFound;
    }

    public void edit(String poIDToEdit, String newName, String newSupplier, int newQuantity, double newPrice, String newDescription) {
    boolean poFound = false;

    try (Scanner scanner = new Scanner(new File(FILENAME));
         BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME + ".tmp", true))) {

        DateTimeGenerator dateTimeGenerator = new DateTimeGenerator();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] poInfo = line.split(",");
            String poID = poInfo[1].trim(); // Use index 1 for PO ID

            if (poID.equalsIgnoreCase(poIDToEdit)) {
                String currentDate = DateTimeGenerator.getCurrentDate();
                
                String newPOData = currentDate + "," + poID + "," + newName + "," + newSupplier + ","
                        + newQuantity + "," + newPrice + "," + newDescription;
                writer.write(newPOData);
                poFound = true;
            } else {
                writer.write(line);
                writer.newLine();
            }
        }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while editing the PO information.\n");
            return;
        }

        if (!poFound) {
            System.out.println("\nThere's no such PO to edit.\n");
            return;
        }

        File originalFile = new File(FILENAME);
        File temporaryFile = new File(FILENAME + ".tmp");

        if (temporaryFile.renameTo(originalFile)) {
            System.out.println("\nData File renamed successfully.\n");
        } else {
            System.out.println("\nFailed to rename Data file.\n");
        }
    }

    public boolean check(String poIDToEdit) {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] poInfo = line.split(",");
                String poIDFromFile = poInfo[1].trim();

                if (poIDFromFile.equalsIgnoreCase(poIDToEdit)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while checking PO information.\n");
        }
        return false;
    }

}


