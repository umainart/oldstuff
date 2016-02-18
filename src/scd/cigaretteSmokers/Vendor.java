/*
 * Vendor.java
 *
 * Created on 20 de Novembro de 2006, 00:43
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
 * Esta classe representa o vendedor e tem como responsabilidade,
 * sortear os produtos e colocá-los na mesa.
 */
public class Vendor extends Observable implements Runnable{
    private Table table;
    private int p1, p2;
    private Random random;
        
    public Vendor(Table table) {
        this.table = table;
        random = new Random(Calendar.DATE);
    }
    
    /* sorteia os produtos aleatoriamente */
    public Product[] draft(){        
        p1 = random.nextInt() % 3;
        if (p1 <0) p1 *= -1;        
        
        p2 = random.nextInt() %3;
        if (p2 <0) p2 *= -1;
        
        while (p2 == p1){
            p2 = random.nextInt() %3;
            if (p2 <0) p2 *= -1;
        }
        
        return (new Product[]{ new Product(p1), new Product(p2) });         
    }
    
    
            
    public void run(){        
        while(!Parameter.STOP){                       
            /* sorteia os produtos e notifica observadores para atualizacao */
            Product p[] = draft();             
            setChanged();            
            notifyObservers(new Parameter("Sorteei os produtos."));            
            System.out.println("Vendedor sorteou os produtos: "+
                    p[0].getDescription() + " e "+p[1].getDescription());                        
            Parameter.snoozeAfterDraft();
            clearChanged();                                                  
            
            /* coloca os produtos na mesa, notificando seus observadores */
            setChanged();             
            notifyObservers(new Parameter(p, 
                    new String("Coloquei produtos na mesa.")));                                                            
            Parameter.snoozeWithProductOnTable();
            table.put(p[0], p[1]); 
            System.out.println("Vendedor colocou na mesa os produtos: "+
                    p[0].getDescription() + " e "+p[1].getDescription());                        
            clearChanged();                                                       
        }        
    }       
}
