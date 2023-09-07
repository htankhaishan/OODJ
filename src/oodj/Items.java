/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;
import java.io.*;
import java.util.*;

/**
 *
 * @author htankhaishan
 */

//filepath
// "/Users/ben/Documents/OOPJAVA/OODJ/items.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/items.txt"

public class Items extends manage {
    
    private static final String FILENAME = "/Users/ben/Desktop/OODJ//items.txt";
    private final UserInputUtility userInputUtility; // Composition OOD method
    
    public Items() {
        this.userInputUtility = new UserInputUtility(new Scanner(System.in)); // Initialize the UserInputUtility
    }

    private String Iname, Icate, Iprice, Iavi, Idec, Icode;

    public String getIname() {
        return Iname;
    }

    public void setIname(String Iname) {
        this.Iname = Iname;
    }

    public String getIcate() {
        return Icate;
    }

    public void setIcate(String Icate) {
        this.Icate = Icate;
    }

    public String getIprice() {
        return Iprice;
    }

    public void setIprice(String Iprice) {
        this.Iprice = Iprice;
    }

    public String getIavi() {
        return Iavi;
    }

    public void setIavi(String Iavi) {
        this.Iavi = Iavi;
    }

    public String getIdec() {
        return Idec;
    }

    public void setIdec(String Idec) {
        this.Idec = Idec;
    }

    public String getIcode() {
        return Icode;
    }

    public void setIcode(String Icode) {
        this.Icode = Icode;
    }

    @Override
    public String toString() {
        return Iname + Icate + Iprice + Iavi + Idec + Icode;
    }
    
    // Generate a random 8-digit item code
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
    private String getUserInput(String prompt) {
        return userInputUtility.getUserInput(prompt);
    }

    @Override
    public void add() {
    try (FileWriter writer = new FileWriter(FILENAME, true);
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {   
        // Prompt the user for item information
        Scanner scanner = new Scanner(System.in);
        // Generate a random 8-digit item code
        String itemCode = generateRandomItemCode();

        System.out.print("Enter item name: ");
        setIname(scanner.nextLine());
        
        System.out.print("Enter item category: ");
        setIcate(scanner.nextLine());
        
        System.out.print("Enter item price in RM: ");
        setIprice(scanner.nextLine());
        
        System.out.print("Enter item Availability (Available or NoStock): ");
        setIavi(scanner.nextLine());

        System.out.print("Enter item Description: ");
        setIdec(scanner.nextLine());

        // Display the entered data for confirmation
        System.out.println("\nPlease review the entered data:");
        System.out.println("Item Information:");
        System.out.println("Product Code: " + itemCode);
        System.out.println("Name: " + getIname());
        System.out.println("Category: " + getIcate());
        System.out.println("Price: " + getIprice() + " RM");
        System.out.println("Availability: " + getIavi());
        System.out.println("Description: " + getIdec());
        
        System.out.print("Do you want to save this item? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equals("yes") || confirm.equals("y")) {
            // Create a comma-separated string with the item data
            String itemData = itemCode + "," + getIname() + "," + getIcate() + "," + getIprice() + "," + getIavi() + "," + getIdec();
            // Write the item data to the file
            bufferedWriter.write(itemData);
            bufferedWriter.newLine();
            System.out.println("\nItem information saved successfully.\n");
        } else {
            System.out.println("\nItem not saved.\n");
        }

    } catch (IOException e) {
        System.out.println("\nAn error occurred while saving the item information.\n");
    }
    }

    @Override
    public void view() {
    List<String> itemList = ReadItemListFromFile(FILENAME);

    int numColumns = 6;
    int[] maxColumnWidths = new int[numColumns];
    List<String[]> formattedItemList = new ArrayList<>();

    String separator = " -------------------------------------------------------------------------------------------------------------------------";
    String format = "| %-"+(maxColumnWidths[0]+1)+"s | %-"+(maxColumnWidths[1]+2)+"s | %-"+(maxColumnWidths[2]+2)+"s | %-"+(maxColumnWidths[3]+1)+"s | %-"+(maxColumnWidths[4]+2)+"s | %-"+(maxColumnWidths[5]+5)+"s |";

    System.out.println("Item List:");
    System.out.println(separator);
    System.out.printf(format,"Code", "Name", "Category", "Price", "Status", "Description");
    System.out.println();
    System.out.println(separator);

    boolean itemsFound = false; // Initialize the flag

    for (String item : itemList) {
        String[] itemInfo = item.split(",");

        if (itemInfo.length >= numColumns) {
            formattedItemList.add(itemInfo);

            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], itemInfo[i].length());
            }

            System.out.printf(format, itemInfo[0], itemInfo[1], itemInfo[2], itemInfo[3], itemInfo[4], itemInfo[5]);
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
    
    public static List<String> ReadItemListFromFile(String FILENAME){
            List<String> itemList = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))){
                String line;
                while ((line = reader.readLine()) != null) {
                    itemList.add(line);
                }
            } catch (IOException e) {
            }
            return itemList;
        }

    public static String formatTableCell(String cellContent){
           int cellWidth = 15;
            if (cellContent.length() <= cellWidth) {
                return cellContent;
            } else {
                return cellContent.substring(0, cellWidth - 3) + "...";
            } 
    }    
    
    public boolean view(String filter) {
        List<String> itemList = ReadItemListFromFile(FILENAME);

        int numColumns = 6;
        int[] maxColumnWidths = new int[numColumns];
        List<String[]> formattedItemList = new ArrayList<>();

        boolean itemsFound = false; // Initialize the flag

        for (String item : itemList) {
            String[] itemInfo = item.split(",");
            formattedItemList.add(itemInfo);

            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], itemInfo[i].length());
            }
        }

        String separator = " -------------------------------------------------------------------------------------------------------------------------";
        String format = "| %-"+(maxColumnWidths[0]+1)+"s | %-"+(maxColumnWidths[1]+2)+"s | %-"+(maxColumnWidths[2]+2)+"s | %-"+(maxColumnWidths[3]+1)+"s | %-"+(maxColumnWidths[4]+2)+"s | %-"+(maxColumnWidths[5]+5)+"s |";

        System.out.println("Item List:");
        System.out.println(separator);
        System.out.printf(format,"Code", "Name", "Type", "Price", "Status", "Description");
        System.out.println();
        System.out.println(separator);

        for (String[] itemInfo : formattedItemList) {
            if (filter == null || filter.isEmpty() || itemInfo[1].toLowerCase().contains(filter.toLowerCase())) {
                System.out.printf(format,itemInfo[0], itemInfo[1], itemInfo[2], itemInfo[3], itemInfo[4], itemInfo[5]);
                System.out.println();
                itemsFound = true; // Set the flag if items are found
            }
        }

        System.out.println(separator);

        return itemsFound; // Return whether items were found
    }

    @Override
    public void delete(String itemCodeToDelete) {
    try (Scanner scanner = new Scanner(new File(FILENAME));
         FileWriter writer = new FileWriter(FILENAME + ".tmp");
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

        boolean itemDeleted = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] itemInfo = line.split(",");
            String itemCodeFromFile = itemInfo[0].trim(); // Assuming the product code is at index 4

            if (!itemCodeFromFile.equalsIgnoreCase(itemCodeToDelete)) {
                // Write the item data to the temporary file
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                itemDeleted = true;
            }
        }
        if (itemDeleted) {
            System.out.println("\nItem deleted successfully.\n");
        } else {
            System.out.println("\nItem not found.\n");
        }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while deleting the item.\n");
        }

        File originalFile = new File(FILENAME);
        File temporaryFile = new File(FILENAME + ".tmp");

        if (temporaryFile.renameTo(originalFile)) {
            System.out.println("Data File renamed successfully.\n");
        } else {
            System.out.println("Failed to rename Data file.\n");
        }
    }


    public void edit(String itemNameToEdit, String newName, String newCategory, String newPrice, String newAvailability, String newDescription) {
    boolean itemFound = false;

    // First, check if the item exists
    try (Scanner scanner = new Scanner(new File(FILENAME));
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME + ".tmp"))) {

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] itemInfo = line.split(",");
            String itemName = itemInfo[0].trim();

            if (itemName.equalsIgnoreCase(itemNameToEdit)) {
                // Item found, update its information
                String oldCode = itemInfo[0].trim(); // Get the existing item code
                String itemData = oldCode + "," + newName + "," + newCategory + "," + newPrice + "," + newAvailability + "," + newDescription + "\n";
                writer.write(itemData);
                itemFound = true;
            } else {
                writer.write(line);
                writer.newLine();
            }
        }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while editing the item information.\n");
            return;
        }

        if (!itemFound) {
            System.out.println("\nThere's no such item to edit.\n");
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

    public boolean check(String itemCodeToEdit) {
    try (Scanner scanner = new Scanner(new File(FILENAME))) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] itemInfo = line.split(",");
            String itemCodeFromFile = itemInfo[0].trim(); // Assuming the product code is at index 4

            if (itemCodeFromFile.equalsIgnoreCase(itemCodeToEdit)) {
                return true; // Item found
            }
        }
    } catch (IOException e) {
        System.out.println("\nAn error occurred while checking item information.\n");
    }
    return false; // Item not found
    }
}    