/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author htankhaishan
 */
public class DailyItemsSale {
    private String orderID, productName, quantitySold, revenue;
    private static final String FILENAME = "/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/dailyItemsSales.txt";
    
    public DailyItemsSale(String orderID, String productName, String quantitySold, String revenue){
        this.orderID = orderID;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.revenue = revenue;
    }
    
    DailyItemsSale() {
        
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
    
    public void deleteDailyFromFile(String dailyItemToDelete) throws IOException {
    File inputFile = new File(FILENAME);
    File tempFile = new File(FILENAME + ".tmp");
    boolean dailyItemFound = false;

    try (Scanner scanner = new Scanner(inputFile);
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] dailyItemInfo = line.split(",");
            String dailyItemName = dailyItemInfo[0].trim(); // Assuming the item name is stored in the first column

            if (!dailyItemName.equalsIgnoreCase(dailyItemToDelete)) {
                writer.write(line);
                writer.newLine();
            } else {
                dailyItemFound = true;
            }
        }
    } catch (IOException e) {
        System.out.println("\nAn error occurred while deleting the Supplier Informanion.\n");
        return;
    }

    if (!dailyItemFound) {
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

    public void editDailyFromFile(String dailyNameToEdit, String orderID, String productName, String quantitySold, String revenue) throws IOException {
    File tempFile = new File(FILENAME + ".tmp");
    boolean supFound = false;

    try (Scanner scanner = new Scanner(new File(FILENAME));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] dailyInfo = line.split(",");
            String dailyName = dailyInfo[0].trim();

            if (dailyName.equalsIgnoreCase(dailyNameToEdit)) {
                String supData = orderID + "," + productName + "," + quantitySold + "," + revenue + "\n";
                writer.write(supData);
                supFound = true;
            } else {
                writer.write(line);
                writer.newLine();
            }
        }
    } catch (IOException e) {
        System.out.println("\nAn error occurred while editing the item information.\n");
        return;
    }

    if (!checkDailyExists(dailyNameToEdit)) {
        System.out.println("\nThere's no such item to edit.\n");
        return;
    }

    if (!supFound) {
        System.out.println("\nThere's no such item to edit.\n");
        return;
    }

    if (!tempFile.renameTo(new File(FILENAME))) {
        System.out.println("\nFailed to update the Daily Items Sale file after editing.\n");
    } else {
        System.out.println("\nDaily Items Sale information edited successfully.\n");
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

    boolean checkItemExists(String dailyNameToEdit) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
