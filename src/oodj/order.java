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
import java.io.*;

/**
 *
 * @author BEN
 //filepath
// "/Users/ben/Documents/OOPJAVA/OODJ/order.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/order.txt"
*/
public class order {
    Scanner sc = new Scanner(System.in);
    String filename = "/Users/ben/Desktop/OODJ/order.txt";

    
    public order(){
        orderProcess();
    }
    
    public void orderProcess(){
        try{
            System.out.println("Purchase Order Process!");
            System.out.println("1. Add Order");
            System.out.println("2. Delete Order");
            System.out.println("3. Edit Order information");
            System.out.println("4. Go back to Main Menu");
            System.out.print("Enter your choice: ");
            
            String choice = sc.nextLine();
            if (choice.equals("1")){
                AddOrderToFile();
            }
            else if (choice.equals("2")){
                deleteOrderFromFile();
            }
            else if (choice.equals("3")){
                checkOrderExists();
                
            }
            else if (choice.equals("4")){
                
            }else{
            }
                
        }catch(Exception ex){
            
        }
        
    }
    
    
        
    public void AddOrderToFile(){
        
        System.out.println("Add New Order.");
        System.out.print("Enter Vendor name: ");
        String vendorname = sc.nextLine();
        System.out.print("Enter Address: ");
        String adress = sc.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNum = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Item code : ");
        String itemcode = sc.nextLine();
        System.out.print("Enter Description of the order: ");
        String descriptions = sc.nextLine();
        System.out.print("Enter Quantity of the order: ");
        String quantity = sc.nextLine();
        System.out.print("\nDo you want to save this Order? Please checks before you proceed. (Y/N): ");
        String confirm = sc.nextLine();
        
        if (confirm.equalsIgnoreCase("Y")){
            try (FileWriter writer = new FileWriter(filename,true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)){
                String Data =  vendorname + "," + adress + "," + phoneNum + ","+ email + ","+ itemcode + "," + descriptions+ "," + quantity + "\n";
                bufferedWriter.write(Data);
                System.out.println("\n Order information saved Successfully.\n");
            
            } catch(IOException e){
                System.out.println("\nSometing went wrong while saving order information!\n");
        }
            
        }else{
            System.out.println("The order not Saved");
        }
        
                 
    }
        
 
    
    public void deleteOrderFromFile() throws IOException {
        System.out.println("Delete order");
        System.out.print("Enter the VendorName : ");
        String VendorName = sc.nextLine();
        
        File inputFile = new File(filename);
        File tempFile = new File(filename + ".tmp");
        boolean orderFound = false;

        try (Scanner sc = new Scanner(inputFile);
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] orderInfo = line.split(",");
                String Vendorname = orderInfo[0].trim(); 

                if (!Vendorname.equalsIgnoreCase(VendorName)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    orderFound = true;
                }
            }
        } catch (IOException e) {
            System.out.println("\nSometing went wrong while deleting order information!\n");
            return;
        }

        if (!orderFound) {
            System.out.println("\nNot have order information that you want to delete!\n");
            return;
        }

    
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("\nDelete order Unsuccessfull!\n");
        } else {
            System.out.println("\nDelete order Successfully\n");
        }
    
    }
    
    public void editOrder() {
        
              
        System.out.print("Enter Vendor name: ");
        String vendorname = sc.nextLine();
        System.out.print("Enter Address: ");
        String address = sc.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNum = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Item code : ");
        String itemcode = sc.nextLine();
        System.out.print("Enter Description of the order: ");
        String descriptions = sc.nextLine();
        System.out.print("Enter Quantity of the order: ");
        String quantity = sc.nextLine();
        System.out.print("\nDo you want to save this Order Editing ? Please checks before you proceed. (Y/N): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            File tempFile = new File(filename+ ".tmp");
            boolean orderFound = false;

            try (Scanner sc = new Scanner(new File(filename));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] orderInfo = line.split(",");
                    String vendorNameFromFile = orderInfo[0].trim();

                    if (vendorname.equalsIgnoreCase(vendorNameFromFile)) {
                        String orderData = vendorname + "," + address + "," + phoneNum + ","+ email + ","+ itemcode + "," + descriptions+ "," + quantity + "\n";
                        writer.write(orderData);
                        orderFound = true;
                        
                    } else {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                System.out.println("\nSometing went wrong while editing order information!\n");
                return;
            
            }
            
            

            if (!orderFound) {
                System.out.println("\nNot have order information that you want to edit!\n");
                return;
            }

            if (!tempFile.renameTo(new File(filename))){
            System.out.println("\nEditing order Unsuccessfull!\n");
            
            } else {
            System.out.println("\nEditing order successfull!\n");
            }
            
            
        }else {
              System.out.println("Edit not changing");  
        }
    }

    


    public boolean checkOrderExists() {
        System.out.println("Edit order");
        System.out.print("Enter the order vendor name : ");
        String name = sc.nextLine();
        try (Scanner sc = new Scanner(new File(filename))){
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] orderInfo = line.split(",");
                String VendorNameFromFile = orderInfo[0].trim();
                if (VendorNameFromFile.equalsIgnoreCase(name)) {
                    editOrder();
                    return true; 
                    
                }
            }
        } catch (IOException e) {
                System.out.println("\nNot have order information that you want to edit!\n");
        }
        System.out.println("\nOrder not found.\n");
        return false;
    }
    
    
  

    
    
}
