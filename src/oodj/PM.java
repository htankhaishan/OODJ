/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;
import java.util.Scanner;

/**
 *
 * @author BEN
 */
public class PM {
    
    Scanner sc = new Scanner(System.in);
    
    
    
    public PM() {
        System.out.println("Welcome back Purchase Manager!");
        System.out.println("What do you want to do : ");
        System.out.println(" 1. View Item List");
        System.out.println(" 2. View Supplier List");
        System.out.println(" 3. Purchase Order");
            System.out.print(" please enter the number only :  ");
            String choice = sc.nextLine();
            if(choice.equals("1")){
                Items item = new Items();
                //item.ViewItem();
                
                
            }
            else if(choice.equals("2")){
                Suppliers suppliers = new Suppliers();
                //suppliers.ViewSuppliers();
                
                
            }else if(choice.equals("3")){
                order order = new order();
                
                
                
            }else{
                System.out.println("Wrong choice please enter only number!");
                new PM();
            }
        
    }
    
   
    
    
    
    
    
    
    
}
