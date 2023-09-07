/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

/**
 *
 * @author htankhaishan
 */
import java.util.Scanner;
//Composition Method
public class UserInputUtility {
    private Scanner scanner;

    public UserInputUtility(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean getUserConfirmation() {
        System.out.print("\nConfirm before Proceed? (yes/no): ");
        String confirmation = scanner.nextLine();
        return confirmation.equalsIgnoreCase("yes");
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

