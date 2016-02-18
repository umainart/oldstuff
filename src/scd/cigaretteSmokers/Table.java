/*
 * Table.java
 *
 * Created on 20 de Novembro de 2006, 00:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.cigaretteSmokers;

import scd.utils.Semaphore;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see java.util.Observable
 * @see java.lang.Runnable
 * @since SCD2006.02 
 *
 * Esta classe representa a mesa e tem como responsabilidade, por ser 
 * o recurso compartilhado, encapsular o algoritmo de teste dos produtos,
 * e garantir a exclusão mútua quando o vendedor coloca o produto na mesa,
 * ou quando algum fumanta pega o produto da mesa.
 */
public class Table {
    private volatile Product product1, product2;
    private Semaphore mutex, sync;
    
    /** Creates a new instance of Table */
    public Table() {
        mutex = new Semaphore(1);
        sync = new Semaphore(1);
        product1 = null;
        product2 = null;
    }
    
    public void put(Product p1, Product p2){
        /* regiao critica para colocar o produto na mesa */
        mutex.down();
        product1 = p1;
        product2 = p2;            
        mutex.up();
        sync.down();
    }
    
    /* testa os produtos na mesa */
    public boolean test(Product p){
        boolean ret = false;
        mutex.down();
        if(product1!=null && product2!=null){
            if(p.getType() != product1.getType() 
                && p.getType() != product2.getType())        
                ret = true;
        }        
        mutex.up();
        return ret;
    }
 
    public Product[] get(){        
        /* regiao critica para pegar os produtos da mesa */
        mutex.down();
        Product[] p = new Product[]{ product1, product2 };
        product1 = null; product2 = null;
        mutex.up();        
        return p;
    }
        
    public void notice(){
        /* libera vendedor */
        sync.up();
    }
    
    public boolean isEmpty(){
        return (product1==null && product2==null);
    }
}
