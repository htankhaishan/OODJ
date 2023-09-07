/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

/**
 * @author BEN
 */

//filepath
// "/Users/ben/Desktop/OODJ/username.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/username.txt"


import java.util.Scanner;

public class Admin extends Users{
    
    Scanner sc = new Scanner(System.in);
    String filename = "/Users/htankhaishan/Desktop/OODJ/username.txt";
    private final UserInputUtility userInputUtility;
    
 

    
    public Admin(){
        this.userInputUtility = new UserInputUtility(sc);
        
        try{
            System.out.println("\n Welcome back ADMIN \n");
            System.out.println("---------------------------------------------------\n");
            System.out.println(" What do you what to do today! \n");
            System.out.println("---------------------------------------------------\n");
            System.out.println(" 1. Account Managment ");
            System.out.println(" 2. Item Entry ");
            System.out.println(" 3. Supplier Entry ");
            System.out.println(" 4. Daily Item-wise Sales Entry ");
            System.out.println(" 5. Purchase Requisition ");
            System.out.println(" 6. Purchase Order ");
            System.out.println(" 7. View Daily Report ");
            
            System.out.print(" \nplease enter  choice \n( the number only ) :  ");
            String choice = sc.nextLine();
            if(choice.equals("1")){ 
                System.out.println("---------------------------------------------");
                System.out.println("1. Registeration");
                System.out.println("2. Delete User Account");
                System.out.println("3. Edit User Account");
                System.out.println("4. View User Account List");
                System.out.println("---------------------------------------------");
                System.out.print("Enter number here : ");
                int select = sc.nextInt();
                switch(select){
                    case 1 : {
                        super.viewUseraccount();
                        super.createaccount();
                        super.viewUseraccount();
                        break;
                    }
                    case 2 :{
                        System.out.println("\n------------ Delete User Account ------------\n");
                        super.viewUseraccount();               
                        sc.nextLine();
                        System.out.print("Enter the Code of the Account User to delete : ");
                        String accountCodeToDelete = sc.nextLine().trim();
                        super.DeleteAccount(accountCodeToDelete);
                        super.viewUseraccount();
                        break;
                    }
                    case 3: {
                        System.out.println("\n------------ Edit User Account ------------\n");
                        super.viewUseraccount();
                        sc.nextLine();
                        System.out.print("\nEnter the Code of the user account to edit: ");
                        String accountCodeToEdit = sc.nextLine();

                        // Check if the item exists before asking for new information
                        if (super.check(accountCodeToEdit)) {
                            boolean confirmed = true;
                            // Assuming you have a confirmed boolean variable set appropriately
                            if (confirmed) {
                                String newName = getUserInput("Enter new name: ");
                                String newPassword = getUserInput("Enter new password: ");
                                String newRole = getUserInput("Enter new role: ");

                                // Proceed with the edit
                                super.EditAccount(accountCodeToEdit, newName, newPassword, newRole);
                            } else {
                                System.out.println("\nEdit process canceled.\n");
                            }
                        } else {
                            System.out.println("\nThere's no such user account to edit.\n");
                        }
                        super.viewUseraccount();
                        break;
                    }

                    
                    case 4 :{
                        super.viewUseraccount();
                        break;
                    }
                 
                    default:
                        System.out.println("Wrong Choice please enter only number");                       
                 
                }
            
            }
            else if(choice.equals("2")){ 
                
            }
            else if(choice.equals("3")){ 
                
            }
            else if(choice.equals("4")){ 
                
            }
            else if(choice.equals("5")){ 
                
            }
            else if(choice.equals("6")){ 
                
            }
            else if(choice.equals("7")){ 
                
            }
            
            else{
                System.out.println("wrong chose please inter the number only ");
                new Admin();
            }
            
        sc.nextLine();

        System.out.println("Do you want to continue as an Admin?");
        System.out.print(" (Yes or No): ");
        String con = sc.nextLine();
        if (con.equalsIgnoreCase("yes")) {
            new Admin();
        }else {
            System.out.println("Bye Admin, Have a nice day");
            
        }  
            
              
                
        }catch(Exception ex){
            
        }
        
   
        
        
    }

    private String getUserInput(String prompt) {
        return userInputUtility.getUserInput(prompt);
    }
    
    
}