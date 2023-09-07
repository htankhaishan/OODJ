/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import java.util.*;

/**
 *
 * @author BEN
 */
public class Users {
    

    Scanner s = new Scanner(System.in);
    String filename = "/Users/ben/Desktop/OODJ/username.txt";
    
   
    private String generateRandomItemCode() {
        long nanoTime = System.nanoTime();
        String nanoTimeString = String.valueOf(nanoTime);
        String characters = "ABC0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(nanoTimeString);
        int randomCodeLength = 7;
        for (int i = 0; i < randomCodeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }
        return codeBuilder.toString().substring(13, 18);
    }
    
    public void viewUseraccount(){
        List<String> accountList = ReadItemListFromFile(filename);
        
        int numColumns = 4;
        int[] maxColumnWidths = new int[numColumns];
        List<String[]> formattedItemList = new ArrayList<>();

        for (String account : accountList) {
        String[] accountInfo = account.split(",");
        formattedItemList.add(accountInfo);

            for (int i = 0; i < numColumns; i++) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], accountInfo[i].length());
            }
        }

        String separator = " -------------------------------------------";
        String format = "| %-"+(maxColumnWidths[0]+1)+"s | %-"+(maxColumnWidths[1]+2)+"s | %-"+(maxColumnWidths[2]+2)+"s | %-"+(maxColumnWidths[3]+1)+"s |";

        System.out.println("User Account List: \n");
        System.out.println(separator);
        System.out.printf(format,"ID", "Username" , "Password" ," Role");
        System.out.println();
        System.out.println(separator);

        for (String[] accountInfo : formattedItemList) {
            System.out.printf(format,accountInfo[0], accountInfo[1], accountInfo[2], accountInfo[3]);
            System.out.println();
        }

        System.out.println(separator);
    
        }
    
    public static List<String> ReadItemListFromFile(String FILENAME){
            List<String> accountList = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))){
                String line;
                while ((line = reader.readLine()) != null) {
                    accountList.add(line);
                }
            } catch (IOException e) {
            }
            return accountList;
        }
    

    
    public void createaccount(){
        try{
            Path path = Paths.get(filename.toString());
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(path,APPEND));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            String UserCode = generateRandomItemCode();
            System.out.print("What is a username : ");
            String username = s.nextLine();
            System.out.print("What is a password : ");
            String password = s.nextLine();
            System.out.print("What is the role : ");
            String role = s.nextLine();
            
            writer.write(UserCode+ "," + username + "," + password + "," + role);
            writer.newLine();
            System.out.println("Account has been saved successfully");
            System.out.println("\n------------------------------------------------\n");
            viewUseraccount();
            writer.close();
            output.close();
            
 
        }catch(Exception ex){
            System.out.print(ex.getMessage());
        }
    }
    
    
    
    
    public void DeleteAccount(String accountCodeToDelete){
            
        try (Scanner sc = new Scanner(new File(filename));
    
                
         FileWriter writer = new FileWriter(filename + ".tmp");
         BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

        boolean accountDeleted = false;

       
        while (sc.hasNextLine()) {
            
            String line = sc.nextLine();
            String[] accountInfo = line.split(",");
            String accountCodeFromFile = accountInfo[0].trim(); // Assuming the product code is at index 4

            if (!accountCodeFromFile.equalsIgnoreCase(accountCodeToDelete)) {
                // Write the item data to the temporary file
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                accountDeleted = true;
            }
        }
        if (accountDeleted) {
            System.out.println("\nAccount deleted successfully.\n");
            
        } else {
            System.out.println("\nNot have the User Account.\n");
        }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while deleting the User Account.\n");
        }

        File originalFile = new File(filename);
        File temporaryFile = new File(filename + ".tmp");

        if (temporaryFile.renameTo(originalFile)) {
            System.out.println("Data File renamed successfully.\n");
        } else {
            System.out.println("Failed to rename Data file.\n");
        }
    }
    

    public void EditAccount(String accountCodeToEdit, String Username, String Password, String Role) {
        boolean accountFound = false;

        try (Scanner sc = new Scanner(new File(filename));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".tmp"))) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] accountInfo = line.split(",");
                String accountName = accountInfo[0].trim();

                if (accountName.equalsIgnoreCase(accountCodeToEdit)) {
                    String oldCode = accountInfo[0].trim();
                    String accountData = oldCode + "," + Username + "," + Password + "," + Role + "\n";
                    writer.write(accountData);
                    accountFound = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while editing the user account information.\n");
            return;
        }

        if (!accountFound) {
            System.out.println("\nNo user account with the given code found.\n");
            return;
        }

        File originalFile = new File(filename);
        File temporaryFile = new File(filename + ".tmp");

        if (temporaryFile.renameTo(originalFile)) {
            System.out.println("\nData File renamed successfully.\n");
        } else {
            System.out.println("\nFailed to rename Data file.\n");
        }
    }

    public boolean check(String accountCodeToEdit) {
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] accountInfo = line.split(",");
                String accountCodeFromFile = accountInfo[0].trim();

                if (accountCodeFromFile.equalsIgnoreCase(accountCodeToEdit)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while checking user account information.\n");
        }
        return false;
    }
}