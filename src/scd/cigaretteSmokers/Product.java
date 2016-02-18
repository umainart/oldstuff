/*
 * Product.java
 *
 * Created on 19 de Novembro de 2006, 23:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.cigaretteSmokers;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @since SCD2006.02 
 */
public class Product {    
    public static final int MATCHES = 0;
    public static final int PAPER = 1;    
    public static final int TOBACCO = 2;
    
    private int type;
    
    /** Creates a new instance of Product */
    public Product() {
        type = -1;
    }
    
    public Product(int type) {
        this.type = type;
    }
    
    public int getType(){ 
        return type; 
    }
    
    public void setType(int type){  
        this.type = type; 
    }
    
    public String getDescription(){
        switch(type){
            case 0: return new String("Fósforos");
            case 1: return new String("Papel");
            case 2: return new String("Fumo");
            default: return null;
        }
    }
}
