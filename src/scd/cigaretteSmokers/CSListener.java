/*
 * CSListener.java
 *
 * Created on 27 de Novembro de 2006, 00:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.cigaretteSmokers;
import scd.utils.*;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @since SCD2006.02 
 *
 * Esta classe é responsável pela criação de threads, start, pause e stop.
 */
public class CSListener {
     private Table table;
     private Vendor v;
     private Smoker s[];
     private Product p[];
     private CigaretteSmokersWindow cs;
     private Thread[] t;
    
    /**
     * Creates a new instance of CSListener
     */
    public CSListener() {
        init();
    }
    
    private void init(){
        table = new Table();
        v = new Vendor(table);        
        s = new Smoker[3]; 
        p = new Product[] { new Product(Product.MATCHES),
                                      new Product(Product.PAPER),
                                      new Product(Product.TOBACCO)
                                    };
        s[0] = new Smoker(1, table, new Product(Product.MATCHES));
        s[1] = new Smoker(2, table, new Product(Product.PAPER));
        s[2] = new Smoker(3, table, new Product(Product.TOBACCO));                
        cs = new CigaretteSmokersWindow(s, v, p, this);  
        cs.setSize(1000, 700);
        cs.setVisible(true);  
        cs.setLocationRelativeTo(null);
    }
    
    public void start(){
        Parameter.STOP = false;
        t = new Thread[4];
        t[0] = new Thread(v);
        t[1] = new Thread(s[0]);
        t[2] = new Thread(s[1]);
        t[3] = new Thread(s[2]);
        for(int i=0; i < 4; i++) t[i].start();
        Parameter.snooze(100); 
    }
    
    public void stop(){
        Parameter.STOP = true;                                              
    }
    
     public static void main (String...args){
        new CSListener();
    }
}
