/*
 * Smoker.java
 *
 * Created on 19 de Novembro de 2006, 23:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.cigaretteSmokers;

import java.util.*;


/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see java.util.Observable
 * @see java.lang.Runnable
 * @since SCD2006.02 
 *
 * Esta classe representa o fumante e tem como responsabilidade,
 * verificar se os produtos na mesa são úteis para seu intento.
 */
public class Smoker extends Observable implements Runnable{
    public static final String SMOKING = "Estou fumando.";
    private int id;
    private Table table;    
    private Product product;    
    
    /** Creates a new instance of Smoker */
    public Smoker(int id, Table table, Product product) {                
        this.id = id;
        this.table = table;
        this.product = product;
    }
            
    public String getProductDescription(){
        return product.getDescription();
    }
    
    public void smoking(Product[] p){
        System.out.println("Smoker "+id+" fumando.");
        Parameter.snoozeSmoking();
    }
                
    public void run(){        
        while(!Parameter.STOP){                        
            setChanged();
            notifyObservers(new Parameter("Estou testando os produtos.", id-1));
            System.out.println("Fumante "+id+" testa produto.");
            clearChanged();     
            
            if(table.test(product)){                
                setChanged();               
                Product[] p = table.get();                            
                                
                //fumando
                System.out.println("Fumante "+id+" fumando.");
                notifyObservers(new Parameter(
                        new String(SMOKING), id-1));
                smoking(p);    
                clearChanged();
                
                //sincroniza vendedor, ao notifica-lo
                setChanged();
                table.notice();
                notifyObservers(new Parameter("Avisei ao vendedor.", id-1));                                
                clearChanged();                
            }
        }
    }
    
}
