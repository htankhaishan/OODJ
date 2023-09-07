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
public class DailyReport {
    private static final String FILENAME = "/Users/ben/Desktop/OODJ/dailyItemsSales.txt";
    Scanner sc = new Scanner(System.in);
    
    public DailyReport(){
        System.out.print("What day do you want to view the reprot (07-09-2023) :  ");
        String date = sc.nextLine();
        view(date);
        
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

            if (dailyItemInfo.length >= numColumns) {
                formattedDailyItemList.add(dailyItemInfo);

                for (int i = 0; i < numColumns; i++) {
                    maxColumnWidths[i] = Math.max(maxColumnWidths[i], dailyItemInfo[i].length() + 2); // Adding extra padding
                }

                // Check if the filter keyword is present in the "Date" column
                if (dailyItemInfo[0].contains(filter)) {
                    itemsFound = true; // Set the flag if items are found
                    day = dailyItemInfo[0];
                    totalQty += Integer.parseInt(dailyItemInfo[4]);
                    totalRev += Double.parseDouble(dailyItemInfo[5]); 
                    
                }
            } else {
                // Handle the case where there are not enough elements in the array
                // This might involve logging an error message or skipping the incomplete entry
                System.out.println("Incomplete entry detected: " + item);
            }
        }

     
        String separator = " ---------------------------------------------------------------------------------------------------------------------";
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

        System.out.println("\n------------------ Summary of a day -----------------\n");
        System.out.println("Total Quantity : " + totalQty);
        System.out.println("Total Revenue : " + totalRev);
        System.out.println( " \n--------------------------------------------------------------------------------------------------------------------------------------------");

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

    
}
