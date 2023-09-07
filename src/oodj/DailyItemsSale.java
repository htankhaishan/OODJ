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
 */
public class DailyItemsSale extends manage {
    private static final String FILENAME = "/Users/ben/Desktop/OODJ/dailyItemsSales.txt";
    private final UserInputUtility userInputUtility; //Composition OOD method
    
    public DailyItemsSale() {
        this.userInputUtility = new UserInputUtility(new Scanner(System.in)); // Initialize the UserInputUtility
    }
    
    private String orderID, productName, quantitySold, revenue;
    
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(String quantitySold) {
        this.quantitySold = quantitySold;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }
    
    @Override
    public String toString() {
        return orderID + productName + quantitySold + revenue;
    }
    
    public class DateTimeGenerator {
        public static String getCurrentDate() {
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return now.format(formatter);
        }
    }


    @Override
    public void add() {
    try (FileWriter writer = new FileWriter(FILENAME, true);
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {    
        Scanner scanner = new Scanner(System.in);
        LocalDateTime now = LocalDateTime.now();
        
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = now.format(dateFormat);

        System.out.print("Enter the item code: ");
        String itemCode = scanner.nextLine();

        System.out.print("Enter the item name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter the item description: ");
        String itemDescription = scanner.nextLine();

        System.out.print("Enter the quantity sold: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter the total revenue: ");
        double revenue = scanner.nextDouble();

        // Display the entered data for confirmation
        System.out.println("\nPlease review the entered data:");
        System.out.println("Date: " + date);
        System.out.println("Item Code: " + itemCode);
        System.out.println("Item Name: " + itemName);
        System.out.println("Description: " + itemDescription);
        System.out.println("Quantity Sold: " + quantity);
        System.out.println("Total Revenue: " + revenue);

        System.out.print("Do you want to save this record? (yes/no): ");
        scanner.nextLine(); // Consume the newline character
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            // Create a comma-separated string with the daily item sale data
            String saleData = date + "," + itemCode + "," + itemName + "," + itemDescription + "," + quantity + "," + revenue;
            // Write the daily item sale data to the file
            bufferedWriter.write(saleData);
            bufferedWriter.newLine();
            System.out.println("\nRecord saved successfully.\n");
        } else {
            System.out.println("\nRecord not saved.\n");
        }

        } catch (IOException e) {
            System.out.println("\nAn error occurred while saving the record.\n");
        }
    }

    @Override
    public void delete(String itemCodeToDelete) {
    try (Scanner scanner = new Scanner(new File(FILENAME));
         FileWriter writer = new FileWriter(FILENAME + ".tmp");
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

        boolean itemDeleted = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] dailyItemInfo = line.split(",");
            String itemCodeFromFile = dailyItemInfo[1].trim(); // Assuming the item code is at index 1

            if (!itemCodeFromFile.equalsIgnoreCase(itemCodeToDelete)) {
                // Write the daily item data to the temporary file
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                itemDeleted = true;
            }
        }

        if (itemDeleted) {
            System.out.println("\nDaily item sale deleted successfully.\n");
        } else {
            System.out.println("\nDaily item sale not found.\n");
        }

    } catch (IOException e) {
        System.out.println("\nAn error occurred while deleting the daily item sale.\n");
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
    List<String> dailyItemList = ReadDailyItemListFromFile(FILENAME);

    int numColumns = 6;
    int[] maxColumnWidths = new int[numColumns];
    List<String[]> formattedDailyItemList;
        formattedDailyItemList = new ArrayList<>();
        
    for (String item : dailyItemList) {
        String[] itemInfo = item.split(",");
        formattedDailyItemList.add(itemInfo);

            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], itemInfo[i].length());
            }
    }

    String separator = " --------------------------------------------------------------------------------------------------------";
    String format = "| %-"+(maxColumnWidths[0]+3)+"s | %-"+(maxColumnWidths[1]+2)+"s | %-"+(maxColumnWidths[2]+2)+"s | %-"+(maxColumnWidths[3]+6)+"s | %-"+(maxColumnWidths[4]+2)+"s | %-"+(maxColumnWidths[5]+3)+"s |";

    System.out.println("Daily Item-Wise Sales List:");
    System.out.println(separator);
    System.out.printf(format,"Date", "Code", "Name", "Description", "Qty", "Revenue");
    System.out.println();
    System.out.println(separator);

    boolean itemsFound = false; // Initialize the flag

    for (String item : dailyItemList) {
        String[] dailyItemInfo = item.split(",");
        
        if (dailyItemInfo.length >= numColumns) {
            formattedDailyItemList.add(dailyItemInfo);
            
            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], dailyItemInfo[i].length());
            }
            
            System.out.printf(format, dailyItemInfo[0], dailyItemInfo[1], dailyItemInfo[2], dailyItemInfo[3], dailyItemInfo[4], dailyItemInfo[5]);
            System.out.println();
            itemsFound = true; // Set the flag if items are found
        } else {
            // Handle the case where there are not enough elements in the array
            // This might involve logging an error message or skipping the incomplete entry
            System.out.println("Incomplete entry detected: " + item);
        }
    }

    System.out.println(separator);
    
    if (!itemsFound) {
        System.out.println("No items found.");
    }
}

    public static List<String> ReadDailyItemListFromFile(String FILENAME) {
    List<String> dailyItemList = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
        String line;
        while ((line = reader.readLine()) != null) {
            dailyItemList.add(line);
        }
    } catch (IOException e) {
    }
    return dailyItemList;
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
    List<String> dailyItemList = ReadDailyItemListFromFile(FILENAME);

    int numColumns = 6;
    int[] maxColumnWidths = new int[numColumns];
    List<String[]> formattedDailyItemList = new ArrayList<>();

    boolean itemsFound = false; // Initialize the flag

    for (String item : dailyItemList) {
        String[] dailyItemInfo = item.split(",");

        if (dailyItemInfo.length >= numColumns) {
            formattedDailyItemList.add(dailyItemInfo);

            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], dailyItemInfo[i].length() + 2); // Adding extra padding
            }

            // Check if the filter keyword is present in the "Date" column
            if (dailyItemInfo[0].contains(filter)) {
                itemsFound = true; // Set the flag if items are found
            }
        } else {
            // Handle the case where there are not enough elements in the array
            // This might involve logging an error message or skipping the incomplete entry
            System.out.println("Incomplete entry detected: " + item);
        }
    }

    String separator = " " + "-".repeat(maxColumnWidths[0]);
    for (int i = 1; i < numColumns; i++) {
        separator += " " + "-".repeat(maxColumnWidths[i]);
    }

    System.out.println("Daily Item-Wise Sales List:");
    System.out.println(" ---------------------------------------------------------------------------------------------------");
    System.out.printf("| %-"+maxColumnWidths[0]+"s | %-"+maxColumnWidths[1]+"s | %-"+maxColumnWidths[2]+"s | %-"+maxColumnWidths[3]+"s | %-"+maxColumnWidths[4]+"s | %-"+maxColumnWidths[5]+"s |\n","Date", "Code", "Name", "Description", "Qty", "Rev");
    System.out.println(" ---------------------------------------------------------------------------------------------------");
            

    if (!itemsFound) {
        System.out.println("No items found.");
    } else {
        for (String[] dailyItemInfo : formattedDailyItemList) {
            if (dailyItemInfo[0].contains(filter)) {
                System.out.printf("| %-"+maxColumnWidths[0]+"s | %-"+maxColumnWidths[1]+"s | %-"+maxColumnWidths[2]+"s | %-"+maxColumnWidths[3]+"s | %-"+maxColumnWidths[4]+"s | %-"+maxColumnWidths[5]+"s |\n", dailyItemInfo[0], dailyItemInfo[1], dailyItemInfo[2], dailyItemInfo[3], dailyItemInfo[4], dailyItemInfo[5]);
                System.out.println(" ---------------------------------------------------------------------------------------\n");
            }
        }
    }

    return itemsFound;
}
    
    public void saveDailyToFile(String orderID, String productName, String quantitySold, String revenue) {
        try (FileWriter writer = new FileWriter(FILENAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            String supData = orderID + "," + productName + "," + quantitySold + "," + revenue + "\n";
            bufferedWriter.write(supData);
            System.out.println("\nDaily Items-wise Sale information saved Successfully.\n");
        } catch (IOException e) {
            System.out.println("\nAn error occurred while saving the Daily Items-wise information.\n");
        }
    }  

    public void edit(String itemCodeToEdit, String newName, String newDescription, String newQty, String newRev) {
    boolean itemFound = false;

    // First, check if the daily item sale exists
    // Check if the daily item sale exists
    try (Scanner scanner = new Scanner(new File(FILENAME));
         BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME + ".tmp", true))) { // Append mode

        DateTimeGenerator dateTimeGenerator = new DateTimeGenerator(); // Create an instance

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] dailyItemInfo = line.split(",");
            String itemCode = dailyItemInfo[1].trim(); // Assuming the item code is at index 1

            if (itemCode.equalsIgnoreCase(itemCodeToEdit)) {
                // Daily item sale found, update its information
                String currentDate = DateTimeGenerator.getCurrentDate(); // Get the current date
                String newItemData = currentDate + "," + itemCode + "," + dailyItemInfo[2] + "," + dailyItemInfo[3] + ","
                        + newQty + "," + newRev + "\n";
                writer.write(newItemData);
                itemFound = true;
            } else {
                writer.write(line);
                writer.newLine();
            }
        }
    } catch (IOException e) {
        System.out.println("\nAn error occurred while editing the daily item sale information.\n");
        return;
    }


        if (!itemFound) {
            System.out.println("\nThere's no such daily item sale to edit.\n");
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

    public boolean checkDailyExists(String supName) {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] supInfo = line.split(",");
                String dailyNameFromFile = supInfo[0].trim();
                if (dailyNameFromFile.equalsIgnoreCase(supName)) {
                    return true; // Supplier found
                }
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while checking supplier information.\n");
        }
        return false; // Supplier not found
    }
   
    public boolean check(String itemCodeToEdit) {
    try (Scanner scanner = new Scanner(new File(FILENAME))) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] dailyItemInfo = line.split(",");
            String itemCodeFromFile = dailyItemInfo[1].trim(); // Assuming the item code is at index 1

            if (itemCodeFromFile.equalsIgnoreCase(itemCodeToEdit)) {
                return true; // Daily item sale found
            }
        }
    } catch (IOException e) {
        System.out.println("\nAn error occurred while checking daily item sale information.\n");
    }
    return false; // Daily item sale not found
}

}
