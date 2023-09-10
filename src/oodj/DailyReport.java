/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author BEN
 */
public final class DailyReport {
    private static final String FILENAME = "/Users/ben/Desktop/OODJ/dailyItemsSales.txt";
    Scanner sc = new Scanner(System.in);
    
    public DailyReport(){
        Scanner sc = new Scanner(System.in);
        String choice;

        do {
            System.out.println("\n--------------- Daily Report Menu ---------------\n");
            System.out.println("1. View all Reports");
            System.out.println("2. Find Reports according to Date");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("\nView All Reports.\n");
                    view();
                    break;
                case "2":
                    System.out.print("\nWhat day do you want to view the report (dd-mm-yyyy) :  ");
                    String date = sc.nextLine();
                    view(date);  
                    break;
                case "0":
                    System.out.println("\nExiting Daily Report Menu.");
                    break;
                default:
                    System.out.println("\nInvalid Input. Please try again.");
                    break;
            }
        } while (!choice.equals("0")); 
    }
    
    public static void view(String filter) {
        List<String> dailyItemList = readDailyItemListFromFile(FILENAME);

        int numColumns = 6;
        int[] maxColumnWidths = new int[numColumns];
        List<String[]> formattedDailyItemList = new ArrayList<>();

        boolean itemsFound = false;
        String day = "";// Initialize the flag
        int totalQty = 0;
        double totalRev = 0;

        for (String item : dailyItemList) {
            String[] dailyItemInfo = item.split(",");
            formattedDailyItemList.add(dailyItemInfo);

                for (int i = 0; i < numColumns; i++) {
                    maxColumnWidths[i] = Math.max(maxColumnWidths[i], dailyItemInfo[i].length() + 2); // Adding extra padding
            }
        }

     
        String separator = " ----------------------------------------------------------------------------------------------------";
        String format = "| %-"+(maxColumnWidths[0]+3)+"s | %-"+(maxColumnWidths[1]+2)+"s | %-"+(maxColumnWidths[2]+2)+"s | %-"+(maxColumnWidths[3]+6)+"s | %-"+(maxColumnWidths[4]+2)+"s | %-"+(maxColumnWidths[5]+3)+"s |";
        System.out.println( " \n\n-------------------------------------------------- DAILY ITEM-WISSE SALES REPORT  ---------------------------------------------------------");
        System.out.println("\nDaily Item-Wise Sales List:");
        System.out.println("Date : " + day);
        System.out.println(separator);
        System.out.printf(format,"Date", "Code", "Name", "Description", "Qty", "Revenue");
        System.out.println();
        System.out.println(separator);

        //boolean itemsFound = false; // Initialize the flag

        for (String item : dailyItemList) {
            String[] dailyItemInfo = item.split(",");

            if (filter == null || filter.isEmpty() || dailyItemInfo[0].toLowerCase().contains(filter.toLowerCase())){
                System.out.printf(format, dailyItemInfo[0], dailyItemInfo[1], dailyItemInfo[2], dailyItemInfo[3], dailyItemInfo[4], dailyItemInfo[5]);
                System.out.println();
                itemsFound = true; // Set the flag if items are found
            }
        // Check if the filter keyword is present in the "Date" column
                if (dailyItemInfo[0].contains(filter)) {
                    itemsFound = true; // Set the flag if items are found
                    day = dailyItemInfo[0];
                    totalQty += Integer.parseInt(dailyItemInfo[4]);
                    totalRev += Double.parseDouble(dailyItemInfo[5]);                    
                }
            
        }
        System.out.println(separator);

        if (!itemsFound) {
            System.out.println("No items found.");
        }
        System.out.println("\n------------------ Summary of a day -----------------\n");
        System.out.println("Total Quantity : " + totalQty);
        System.out.println("Total Revenue : " + totalRev);
        System.out.println( " \n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
    
    public static List<String> readDailyItemListFromFile(String FILENAME) {
     List<String> dailyItemList = new ArrayList<>();
     try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
         String line;
         while ((line = reader.readLine()) != null) {
             dailyItemList.add(line);
         }
     } catch (IOException e) {
         // Handle the IOException, e.g., log an error message
         e.printStackTrace();
     }
     return dailyItemList;
 }

    public void view(){
        List<String> itemList = ReadItemListFromFile(FILENAME);
        
        int numColumns = 6;
        int[] maxColumnWidths = new int[numColumns];
        List<String[]> formattedItemList = new ArrayList<>();

        for (String item : itemList) {
        String[] itemInfo = item.split(",");
        formattedItemList.add(itemInfo);

            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], itemInfo[i].length());
            }
        }
        String separator = " -----------------------------------------------------------------------------------";
        String format = "| %-"+(maxColumnWidths[0]+1)+"s | %-"+(maxColumnWidths[1]+2)+"s | %-"+(maxColumnWidths[2]+2)+"s | %-"+(maxColumnWidths[3]+1)+"s | %-"+(maxColumnWidths[4]+2)+"s | %-"+(maxColumnWidths[5]+5)+"s |";

        System.out.println("Item List:");
        System.out.println(separator);
        System.out.printf(format,"Date", "Code", "Name", "Description", "Qty", "Revenue");
        System.out.println();
        System.out.println(separator);
        
        for (String[] itemInfo : formattedItemList) {
            System.out.printf(format,itemInfo[0], itemInfo[1], itemInfo[2], itemInfo[3], itemInfo[4], itemInfo[5]);
            System.out.println();
        }

        System.out.println(separator);
    
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
     
}
