/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;
import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

/**
 *
 * @author htankhaishan
 //filepath
// "/Users/ben/Documents/OOPJAVA/OODJ/username.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/username.txt"
*/

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class PR extends manage {
    private static final String FILENAME = "/Users/ben/Desktop/OODJ/purchaseRequisitions.txt";
    private final UserInputUtility userInputUtility;

    public PR() {
        this.userInputUtility = new UserInputUtility(new Scanner(System.in));
    }

    private String date, requester, itemCode, productName, quantity, price, description;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
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

    @Override
    public String toString() {
        return date + requester + itemCode + productName + quantity + price + description;
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

            System.out.print("Enter the requester: ");
            String requester = scanner.nextLine();

            System.out.print("Enter the product name: ");
            String productName = scanner.nextLine();

            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();

            System.out.print("Enter the price: ");
            double price = scanner.nextDouble();

            scanner.nextLine();

            System.out.print("Enter the description: ");
            String description = scanner.nextLine();

            System.out.println("\nPlease review the entered data:");
            System.out.println("Date: " + date);
            System.out.println("Requester: " + requester);
            System.out.println("PR ID: " + itemCode);
            System.out.println("Product Name: " + productName);
            System.out.println("Quantity: " + quantity);
            System.out.println("Price: " + price);
            System.out.println("Description: " + description);

            System.out.print("Do you want to save this PR? (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
                String prData = date + "," + requester + "," + itemCode + "," + productName + "," + quantity + "," + price + "," + description;
                bufferedWriter.write(prData);
                bufferedWriter.newLine();
                System.out.println("\nPR saved successfully.\n");
            } else {
                System.out.println("\nPR not saved.\n");
            }

        } catch (IOException e) {
            System.out.println("\nAn error occurred while saving the PR.\n");
        }
    }

    @Override
    public void delete(String prIDToDelete) {
        try (Scanner scanner = new Scanner(new File(FILENAME));
             FileWriter writer = new FileWriter(FILENAME + ".tmp");
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            boolean prDeleted = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] prInfo = line.split(",");
                String prIDFromFile = prInfo[2].trim();

                if (!prIDFromFile.equalsIgnoreCase(prIDToDelete)) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                } else {
                    prDeleted = true;
                }
            }

            if (prDeleted) {
                System.out.println("\nPR deleted successfully.\n");
            } else {
                System.out.println("\nPR not found.\n");
            }

        } catch (IOException e) {
            System.out.println("\nAn error occurred while deleting the PR.\n");
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
        List<String> prList = ReadPRListFromFile(FILENAME);

        int numColumns = 7;
        int[] maxColumnWidths = new int[numColumns];
        List<String[]> formattedPRList = new ArrayList<>();

        for (String pr : prList) {
            String[] prInfo = pr.split(",");
            formattedPRList.add(prInfo);

            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], prInfo[i].length());
            }
        }

        String separator = " -----------------------------------------------------------------------------------------------------------------------";
        String format = "| %-" + (maxColumnWidths[0] + 2) + "s | %-" + (maxColumnWidths[1] + 2) + "s | %-" + (maxColumnWidths[2] + 2) + "s | %-" + (maxColumnWidths[3] + 2) + "s | %-" + (maxColumnWidths[4] + 2) + "s | %-" + (maxColumnWidths[5] + 2) + "s | %-" + (maxColumnWidths[6] + 2) + "s |";

        System.out.println("Purchase Requisitions List:");
        System.out.println(separator);
        System.out.printf(format, "Date","Requester","PR ID","Product Name", "Qty", "Price", "Description");
        System.out.println();
        System.out.println(separator);
        
        for (String[] prInfo : formattedPRList) {
        System.out.printf(format, prInfo[0], prInfo[1], prInfo[2], prInfo[3], prInfo[4], prInfo[5], prInfo[6]); // Displaying "Supplier ID"
        System.out.println();
        }

        System.out.println(separator);
    }

    

    public static List<String> ReadPRListFromFile(String FILENAME) {
        List<String> prList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                prList.add(line);
            }
        } catch (IOException e) {
        }
        return prList;
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
        List<String> prList = ReadPRListFromFile(FILENAME);

        int numColumns = 6;
        int[] maxColumnWidths = new int[numColumns];
        List<String[]> formattedPRList = new ArrayList<>();

        boolean itemsFound = false; // Initialize the flag

        for (String pr : prList) {
            String[] prInfo = pr.split(",");
            formattedPRList.add(prInfo);
            
            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], prInfo[i].length() + 2); // Adding extra padding
            }  
            
        }
        
        String format = "| %-" + (maxColumnWidths[0] + 3) + "s | %-" + (maxColumnWidths[1] + 3) + "s | %-" + (maxColumnWidths[2] + 3) + "s | %-" + (maxColumnWidths[3] + 4) + "s | %-" + (maxColumnWidths[4] + 2) + "s | %-" + (maxColumnWidths[5] + 2) + "s |";
        String separator = " ------------------------------------------------------------------------------------------------";

        System.out.println("Purchase Requisitions List:");
        System.out.println(separator);        
        System.out.printf(format, "Date","Requester","PR ID","Product Name", "Qty", "Price" );
        System.out.println();
        System.out.println(separator);

        for (String[] prInfo : formattedPRList) {
            if (filter == null || filter.isEmpty() || prInfo[0].contains(filter.toLowerCase())){
                System.out.printf(format, prInfo[0], prInfo[1], prInfo[2], prInfo[3], prInfo[4], prInfo[5]); // Displaying "Supplier ID"
                System.out.println();
                itemsFound = true;
            }
        }
            System.out.println(separator);
        
        
        return itemsFound;
    }

    public void savePRToFile(String prID, String productName, String description, String quantity, String price) {
        try (FileWriter writer = new FileWriter(FILENAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            String prData = prID + "," + productName + "," + description + "," + quantity + "," + price + "\n";
            bufferedWriter.write(prData);
            System.out.println("\nPR information saved Successfully.\n");
        } catch (IOException e) {
            System.out.println("\nAn error occurred while saving the PR information.\n");
        }
    }

    public void edit(String prIDToEdit, String requester, String newName, String newDescription, String newQuantity, String newPrice) {
        boolean prFound = false;

        // First, check if the PR exists
        try (Scanner scanner = new Scanner(new File(FILENAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME + ".tmp", true))) { // Append mode

            DateTimeGenerator dateTimeGenerator = new DateTimeGenerator(); // Create an instance

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] prInfo = line.split(",");
                String prID = prInfo[2].trim(); // Assuming the PR ID is at index 2

                if (prID.equalsIgnoreCase(prIDToEdit)) {
                    // PR found, update its information
                    String currentDate = DateTimeGenerator.getCurrentDate(); // Get the current date
                    String newPRData = currentDate + "," + requester + "," + prID + "," + newName + ","
                            + newQuantity + "," + newPrice + "," + newDescription + "\n";
                    writer.write(newPRData);
                    prFound = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while editing the PR information.\n");
            return;
        }

        if (!prFound) {
            System.out.println("\nThere's no such PR to edit.\n");
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

    public boolean check(String prIDToEdit) {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] prInfo = line.split(",");
                String prIDFromFile = prInfo[0].trim(); // Assuming the PR ID is at index 1

                if (prIDFromFile.equalsIgnoreCase(prIDToEdit)) {
                    return true; // PR found
                }
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while checking PR information.\n");
        }
        return false; // PR not found
    }
}
