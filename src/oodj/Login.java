/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package oodj;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author BEN
//filepath
// "/Users/ben/Desktop/OODJ/username.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/username.txt"
*/

public final class Login {
    
    Scanner s = new Scanner(System.in);
    String filename = "/Users/ben/Desktop/OODJ/username.txt";
    String user; // Encapsulate the user attribute
    
    
    // Getter method for the user attribute
    public String getUser() {
        return user;
    }

    // Setter method for the user attribute
    public void setUser(String user) {
        this.user = user;
    }
    
    public Login() {
        LoginProcess();
    }

    public void LoginProcess() {
        System.out.println("Login page");
        System.out.print("Username : ");
        String username = s.nextLine();
        System.out.print("Password : ");
        String password = s.nextLine();

        try{
            Path path = Paths.get(filename);
            InputStream input = Files.newInputStream(path);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                String _temp;
                String _user;
                String _pass;
                String _role = "";
                
                boolean found = false;
                while((_temp=reader.readLine()) != null){
                    String[] account = _temp.split(",");
                    _user = account[1];
                    _pass = account[2];
                    _role = account[3];
                    if(_user.equals(username) && _pass.equals(password)){
                        found = true;
                        break;
                    }
                }
                
                if(found){
                    System.out.println("Login Successfully!\n ");
                    setUser(_role); // Use the setter method to set the user attribute
                } else {
                    System.out.println("Invalid username or password or both\n");
                    LoginProcess();
                }
            }
        } catch(IOException ex){
            System.out.print("Invalid username or password or both2\n");
            Login login = new Login();
        }
    }
}

