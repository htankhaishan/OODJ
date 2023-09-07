/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package oodj;

import java.io.IOException;

/**
 *
 * @author ben
 */
public class OODJ {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException  {

       Login login = new Login();
       String username = login.user;
       
       if ("admin".equals(username)){
           System.out.println("Welcome!");
           Admin admin = new Admin();
       }
       
       else if ("sales".equals(username)){
           System.out.println("Welcome!");
           Sales sales = new Sales(); 
           
       }
           
       else if("pm".equals((username))){
           System.out.println("Welcome!");
           PM pm = new PM(); 
       
           
       }else{
           System.out.println("bye");
           
       }
             
       
    }


}
