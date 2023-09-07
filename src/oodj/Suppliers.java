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
    private static final String FILENAME = "/Users/htankhaishan/Desktop/Backup OODJ/build/classes/oodj/suppliers.txt";
    
    @Override
    public void add() {
        try (FileWriter writer = new FileWriter(FILENAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            // Prompt the user for item information
            Scanner scanner = new Scanner(System.in);
            System.out.println("Add New Supplier.");
            System.out.print("Enter Supplier Company Name: ");
            String supname = scanner.nextLine();
            System.out.print("Enter Contact Person: ");
            String conname = scanner.nextLine();
            System.out.print("Enter Email: ");
            String supemail = scanner.nextLine();
            System.out.print("Enter Phone: ");
            String supphone = scanner.nextLine();
            System.out.print("Enter Address: ");
            String supaddr = scanner.nextLine();
            System.out.print("Enter Website: ");
            String supweb = scanner.nextLine();
            
            System.out.println("\nPlease review the entered data:");
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
               String itemData = supname + "," + conname + "," + supemail + "," + supphone + "," + supaddr + "," + supweb;
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
    public void view(){
        List<String> SuppliersList = ReadSuppliersListFromFile(FILENAME);
        
        int numColumns = 6;
        int[] maxColumnWidths = new int[numColumns];
        List<String[]> formattedSuppliersList = new ArrayList<>();

        for (String Suppliers : SuppliersList) {
        String[] SuppliersInfo = Suppliers.split(",");
        formattedSuppliersList.add(SuppliersInfo);

            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], SuppliersInfo[i].length());
            }
        }
        
        String separator = "-------------------------------------------------------------------------------------------------------";
        String format = "| %-"+(maxColumnWidths[0]+2)+"s | %-"+(maxColumnWidths[1]+2)+"s | %-"+(maxColumnWidths[2]+2)+"s | %-"+(maxColumnWidths[3]+2)+"s | %-"+(maxColumnWidths[4]+2)+"s | %-"+(maxColumnWidths[5]+2)+"s |";

        System.out.println("Suppliers List:");
        System.out.println(separator);
        System.out.printf(format, "Supplier", "Contact", "Email", "Phone Number", "Address", "Website");
        System.out.println();
        System.out.println(separator);
        

        for (String[] SuppliersInfo : formattedSuppliersList) {
            System.out.printf(format, SuppliersInfo[0], SuppliersInfo[1], SuppliersInfo[2], SuppliersInfo[3], SuppliersInfo[4], SuppliersInfo[5]);
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
    
    /* 
    public void deleteSuppliersFromFile(String supNameToDelete) throws IOException {
    File inputFile = new File(FILENAME);
    File tempFile = new File(FILENAME + ".tmp");
    boolean supFound = false;

    try (Scanner scanner = new Scanner(inputFile);
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] supInfo = line.split(",");
            String supName = supInfo[0].trim(); // Assuming the item name is stored in the first column

            if (!supName.equalsIgnoreCase(supNameToDelete)) {
                writer.write(line);
                writer.newLine();
            } else {
                supFound = true;
            }
        }
    } catch (IOException e) {
        System.out.println("\nAn error occurred while deleting the Supplier Informanion.\n");
        return;
    }

    if (!supFound) {
        System.out.println("\nInput Supplier not available.\n");
        return;
    }

    // Delete the original file and rename the temporary file to the original file name
    if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
        System.out.println("\nFailed to update the Supplier file after deletion.\n");
    } else {
        System.out.println("\nSupplier Information deleted Successfully.\n");
    }
    }
    
    public void editSupplierFromFile(String supNameToEdit, String supname, String conname, String supemail, String supphone, String supaddr, String supweb) throws IOException {
    File tempFile = new File(FILENAME + ".tmp");
    boolean supFound = false;

    try (Scanner scanner = new Scanner(new File(FILENAME));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] supInfo = line.split(",");
            String supName = supInfo[0].trim();

            if (supName.equalsIgnoreCase(supNameToEdit)) {
                String supData = supname + "," + conname + "," + supemail + "," + supphone + "," + supaddr + "," + supweb + "\n";
                writer.write(supData);
                supFound = true;
            } else {
                writer.write(line);
                writer.newLine();
            }
        }
    } catch (IOException e) {
        System.out.println("\nAn error occurred while editing the supplier information.\n");
        return;
    }

    if (!checkSupplierExists(supNameToEdit)) {
        System.out.println("\nThere's no such supplier to edit.\n");
        return;
    }

    if (!supFound) {
        System.out.println("\nThere's no such supplier to edit.\n");
        return;
    }

    if (!tempFile.renameTo(new File(FILENAME))) {
        System.out.println("\nFailed to update the supplier file after editing.\n");
    } else {
        System.out.println("\nSupplier information edited successfully.\n");
    }
}

    public boolean checkSupplierExists(String supName) {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] supInfo = line.split(",");
                String supNameFromFile = supInfo[0].trim();
                if (supNameFromFile.equalsIgnoreCase(supName)) {
                    return true; // Supplier found
                }
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while checking supplier information.\n");
        }
        return false; // Supplier not found
    }

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
    
    */

    @Override
    public void edit(String edit, String name, String category, String price, String availability, String descriptions) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
