/*
 * WritersHavePriority.java
 *
 * Created on 8 de Novembro de 2006, 01:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters.impl.monitor;

import scd.readersAndWriters.*;
import scd.readersAndWriters.api.IResource;


/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see scd.readersAndWriters.api.IResource
 * @since SCD2006.02 
 */
public class WritersHavePriority implements IResource{
    private volatile int snoozeTime = 50;
    private volatile int rest = 5000;
    private int nreaders;
    private boolean isWriting;    
    private int nWaitWriting;
    
        
    /**
     * Creates a new instance of WritersHavePriority
     */
    public WritersHavePriority() {
        nreaders = 0;
        isWriting = false;
        nWaitWriting = 0;
    }
         
    public synchronized int acquireRead() 
    throws InterruptedException{ 
        /* 
         * Leitor espera enquanto houver alguem escrevendo
         * ou escritores esperando.
         */
        while(isWriting || nWaitWriting > 0) wait();                                       
        nreaders++;   
        return nreaders;
    }
        
    public synchronized int releaseRead(){
        /* 
         * Atualiza o numero de leitores e notifica
         * possiveis escritores que estejam aguardando para escrever.
         */
        nreaders--;
        if(nreaders==0) notify();         
        return nreaders;
    }
            
    public synchronized void acquireWrite()
    throws InterruptedException{
        /* 
         * Atualiza o numero de escritores esperando pela escrita.
         * Enquanto existir outro escritor escrevendo ou leitores lendo,
         * devera esperar.
         */
        nWaitWriting++;                          
        while(isWriting || nreaders > 0) wait();
        nWaitWriting--;                          
        isWriting = true;                        
    }
    
    public synchronized void releaseWrite(){
        isWriting = false;
        notifyAll();
    }
          
    public void snooze(){
        try{ Thread.sleep(snoozeTime); }
        catch(InterruptedException e){ e.printStackTrace(); }
    }
    
    public void setSnooze(int snz){
        snoozeTime = snz;
    }
    
    public void rest(){
         try{ Thread.sleep(rest); }
         catch(InterruptedException e){ e.printStackTrace(); }
    }
    
    public void setRest(int r){
        rest = r;
    }
}
