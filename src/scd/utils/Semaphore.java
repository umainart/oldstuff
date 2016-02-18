/*
 * Semaphore.java
 *
 * Created on 15 de Outubro de 2006, 18:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.utils;

public class Semaphore {
    private volatile int value;
    
    
    /** Creates a new instance of Semaphore */
    public Semaphore() {
        value = 0;
    }
    
    public Semaphore(int value){
        this.value = value;
    }
    
    public synchronized void down(){
        while(value <= 0){
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        
        value --;
    }
    
    public synchronized void up(){
        ++value;
        notify();
    }
}
