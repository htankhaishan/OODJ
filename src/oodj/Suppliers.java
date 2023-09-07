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
// "/Users/ben/Documents/OOPJAVA/OODJ/suppliers.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/suppliers.txt"

public class Suppliers extends manage{
    
    private static final String FILENAME = "/Users/htankhaishan/Desktop/OODJ/suppliers.txt";
    
    private final UserInputUtility userInputUtility; // Composition OOD method
    
    public Suppliers() {
        this.userInputUtility = new UserInputUtility(new Scanner(System.in)); // Initialize the UserInputUtility
    }
    
    private String supname, conname, supemail, supphone, supaddr, supweb;

    public String getSupname() {
        return supname;
    }

    public void setSupname(String supname) {
        this.supname = supname;
    }

    public String getConname() {
        return conname;
    }

    public void setConname(String conname) {
        this.conname = conname;
    }

    public String getSupemail() {
        return supemail;
    }

    public void setSupemail(String supemail) {
        this.supemail = supemail;
    }

    public String getSupphone() {
        return supphone;
    }

    public void setSupphone(String supphone) {
        this.supphone = supphone;
    }

    public String getSupaddr() {
        return supaddr;
    }

    public void setSupaddr(String supaddr) {
        this.supaddr = supaddr;
    }

    public String getSupweb() {
        return supweb;
    }

    public void setSupweb(String supweb) {
        this.supweb = supweb;
    }
    
    @Override
    public String toString() {
        return supname + conname + supemail + supphone + supaddr + supweb;
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
    
    private String getUserInput(String prompt) {
        return userInputUtility.getUserInput(prompt);
    }
    
    @Override
    public void add() {
        try (FileWriter writer = new FileWriter(FILENAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            // Prompt the user for item information
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Supplier Company Name: ");
            String supname = scanner.nextLine();
            System.out.print("Enter Contact Person: ");
            String conname = scanner.nextLine();
            String supCode = generateRandomItemCode();
            System.out.print("Enter Email: ");
            String supemail = scanner.nextLine();
            System.out.print("Enter Phone: ");
            String supphone = scanner.nextLine();
            System.out.print("Enter Address: ");
            String supaddr = scanner.nextLine();
            System.out.print("Enter Website: ");
            String supweb = scanner.nextLine();
            
            System.out.println("\nPlease review the entered data:");
            System.out.println("Supplier ID: " + supCode);
            System.out.println("Name: " + supname);
            System.out.println("Category: " + conname);
            System.out.println("Price: " + supemail);
            System.out.println("Availability: " + supphone);
            System.out.println("Description: " + supaddr);
            System.out.println("Product Code: " + supweb);
            
            
            System.out.print("\nDo you want to save this Supplier Information? Please checks before you proceed. (Y/N): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equals("yes") || confirm.equals("y")) {
               // Create a comma-separated string with the item data
               String itemData = supCode + "," + supname + "," + conname + "," + supemail + "," + supphone + "," + supaddr + "," + supweb;
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
    List<String> suppliersList = ReadSuppliersListFromFile(FILENAME);

    int numColumns = 7; // Updated to 7 columns
    int[] maxColumnWidths = new int[numColumns];
    List<String[]> formattedSuppliersList = new ArrayList<>();

    for (String supplier : suppliersList) {
        String[] supplierInfo = supplier.split(",");
        formattedSuppliersList.add(supplierInfo);

        for (int i = 0; i < numColumns; i++) {
            maxColumnWidths[i] = Math.max(maxColumnWidths[i], supplierInfo[i].length());
        }
    }

    String separator = " -----------------------------------------------------------------------------------------------------------------------------";
    String format = "| %-"+(maxColumnWidths[0]+2)+"s | %-"+(maxColumnWidths[1]+3)+"s | %-"+(maxColumnWidths[2]+2)+"s | %-"+(maxColumnWidths[3]+2)+"s | %-"+(maxColumnWidths[4]+2)+"s | %-"+(maxColumnWidths[5]+5)+"s | %-"+(maxColumnWidths[6]+6)+"s |"; // Adjusted to 7 columns

    System.out.println("Suppliers List:");
    System.out.println(separator);
    System.out.printf(format, "SupplierID", "Company", "Supplier", "Email", "Phone Number", "Address", "Website"); // Added "Supplier ID" column
    System.out.println();
    System.out.println(separator);

    for (String[] supplierInfo : formattedSuppliersList) {
        System.out.printf(format, supplierInfo[0], supplierInfo[1], supplierInfo[2], supplierInfo[3], supplierInfo[4], supplierInfo[5], supplierInfo[6]); // Displaying "Supplier ID"
        System.out.println();
    }

    System.out.println(separator);
    }

    public static List<String> ReadSuppliersListFromFile(String FILENAME){
            List<String> SuppliersList = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))){
                String line;
                while ((line = reader.readLine()) != null) {
                    SuppliersList.add(line);
                }
            } catch (IOException e) {
            }
            return SuppliersList;
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
    List<String> supplierList = ReadSuppliersListFromFile(FILENAME);

    int numColumns = 7; // Updated to 7 columns
    int[] maxColumnWidths = new int[numColumns];
    List<String[]> formattedSupplierList = new ArrayList<>();

    boolean suppliersFound = false;

    for (String supplier : supplierList) {
        String[] supplierInfo = supplier.split(",");
        formattedSupplierList.add(supplierInfo);

        for (int i = 0; i < numColumns; i++) {
            maxColumnWidths[i] = Math.max(maxColumnWidths[i], supplierInfo[i].length());
        }
    }

    String separator = " -------------------------------------------------------------------------------------------------------------------------------";
    String format = "| %-"+(maxColumnWidths[0]+2)+"s | %-"+(maxColumnWidths[1]+3)+"s | %-"+(maxColumnWidths[2]+2)+"s | %-"+(maxColumnWidths[3]+2)+"s | %-"+(maxColumnWidths[4]+2)+"s | %-"+(maxColumnWidths[5]+5)+"s | %-"+(maxColumnWidths[6]+6)+"s |"; // Adjusted to 7 columns

    System.out.println("Supplier List:");
    System.out.println(separator);
    System.out.printf(format, "SupplierID", "Company", "Supplier", "Email", "Phone", "Address", "Website"); // Added "Supplier ID" column
    System.out.println();
    System.out.println(separator);

    for (String[] supplierInfo : formattedSupplierList) {
        if (filter == null || filter.isEmpty() || supplierInfo[1].toLowerCase().contains(filter.toLowerCase())) {
            System.out.printf(format, supplierInfo[0], supplierInfo[1], supplierInfo[2], supplierInfo[3], supplierInfo[4], supplierInfo[5], supplierInfo[6]); // Displaying "Supplier ID"
            System.out.println();
            suppliersFound = true;
        }
    }

    System.out.println(separator);

    return suppliersFound;
    }

        
    @Override
        public void delete(String supNameToDelete) {
            try (Scanner scanner = new Scanner(new File(FILENAME));
                 FileWriter writer = new FileWriter(FILENAME + ".tmp");
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

                boolean supDeleted = false;
                
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] itemInfo = line.split(",");
                    String itemNameFromFile = itemInfo[0].trim();

                    if (!itemNameFromFile.equalsIgnoreCase(supNameToDelete)) {
                        // Write the item data to the temporary file
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    } else {
                        supDeleted = true;
                    }
                }

                if (supDeleted) {
                    System.out.println("\nSupplier Information deleted successfully.\n");
                } else {
                    System.out.println("\nSupplier Information not found.\n");
                }
            } catch (IOException e) {
                System.out.println("\nAn error occurred while deleting the item.\n");
            }

            // After the deletion is done, you should rename the temporary file to replace the original file.
            File originalFile = new File(FILENAME);
            File temporaryFile = new File(FILENAME + ".tmp");

            if (temporaryFile.renameTo(originalFile)) {
                System.out.println("File renamed successfully.\n");
            } else {
                System.out.println("Failed to rename the file.\n");
            }
        }
        
    public void edit(String supplierIdToEdit, String newCompany, String newContact, String newEmail, String newPhone, String newAddress, String newWebsite) {
    boolean supplierFound = false;

    // First, check if the supplier exists
    try (Scanner scanner = new Scanner(new File(FILENAME));
         BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME + ".tmp"))) {

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] supplierInfo = line.split(",");
            String supplierId = supplierInfo[0].trim(); // Assuming Supplier ID is at index 0

            if (supplierId.equalsIgnoreCase(supplierIdToEdit)) {
                // Supplier found, update its information
                String oldSupplierId = supplierInfo[0].trim(); // Get the existing Supplier ID
                String supplierData = oldSupplierId + "," + newCompany + "," + newContact + "," + newEmail + "," + newPhone + "," + newAddress + "," + newWebsite + "\n";
                writer.write(supplierData);
                supplierFound = true;
            } else {
                writer.write(line);
                writer.newLine();
            }
        }
    } catch (IOException e) {
        System.out.println("\nAn error occurred while editing the supplier information.\n");
        return;
    }

    if (!supplierFound) {
        System.out.println("\nThere's no such supplier to edit.\n");
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

    public boolean check(String supplierIdToEdit) {
    try (Scanner scanner = new Scanner(new File(FILENAME))) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] supplierInfo = line.split(",");
            String supplierIdFromFile = supplierInfo[0].trim(); // Assuming Supplier ID is at index 0

            if (supplierIdFromFile.equalsIgnoreCase(supplierIdToEdit)) {
                return true; // Supplier found
            }
        }
    } catch (IOException e) {
        System.out.println("\nAn error occurred while checking supplier information.\n");
    }
    return false; // Supplier not found
}

}
