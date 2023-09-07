/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author htankhaishan
 //filepath
// "/Users/ben/Documents/OOPJAVA/OODJ/username.txt"
//"/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/username.txt"
*/


public class PR {
    private String itemCode, itemDes, quan;
    private static final String FILENAME = "/Users/htankhaishan/Documents/2nd Year 1st Sem/Java/OODJ/pr.txt";

    public PR(String itemCode, String itemDes, String quan) {
        
        this.itemCode = itemCode;
        this.itemDes = itemDes;
        this.quan = quan;
    }
    
    public void savePR(String itemCode, String itemDes, String quan){
        try (FileWriter writer = new FileWriter(FILENAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            String prData = itemCode + "," + itemDes + "," + quan + "\n";
            bufferedWriter.write(prData);
            System.out.println("\nPurchase Requisition saved Successfully.\n");
        } catch (IOException e) {
            System.out.println("\nAn error occurred while saving the Purchase Requisition information.\n");
        }
    }
    
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }
    
    
}
