/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

/**
 * @author BEN
 */

//filepath
// "/Users/ben/Documents/OOPJAVA/OODJ/username.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/username.txt"

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.*;
import java.util.Scanner;

public class Admin {
    
    Scanner s = new Scanner(System.in);
    String filename = "/Users/ben/Documents/OOPJAVA/OODJ/username.txt";
    
    public Admin(){
        
        try{
            System.out.println(" Welcome back ADMIN ");
            System.out.println(" What do you what to do today! ");
            System.out.println(" 1. Register Account ");
            System.out.print(" please enter the number only :  ");
            String choice = s.nextLine();
            if(choice.equals("1")){
                createaccount();
                
            }
        }catch(Exception ex){
            
        }
    }
    
    public void createaccount(){
        try{
            Path path = Paths.get(filename.toString());
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(path,APPEND));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            System.out.print("What is a username : ");
            String username = s.nextLine();
            System.out.print("What is a password : ");
            String password = s.nextLine();
            System.out.print("What is the role : ");
            String role = s.nextLine();
            
            writer.write(username + "," + password + "," + role);
            writer.newLine();
            System.out.println("Account has been saved successfully");
            
            writer.close();
            output.close();
            
            System.out.print(" Do you want to continue the work (yes or no) : ");
            String choice = s.nextLine();
            if(choice.equals("yes"))
                new Admin();
                
            else if(choice.equals("no"))
                System.out.print(" Thank you have a nice day! ");
            
            else 
                System.out.print(" wrong answer! ");
            
            
            
            
        }catch(Exception ex){
            System.out.print(ex.getMessage());
        }
    }
    
    
    
    
    
    
    
}
