/*
 * ReadersHavePriority.java
 *
 * Created on 6 de Novembro de 2006, 00:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters.impl.monitor;

import java.util.*;
import scd.readersAndWriters.*;
import scd.readersAndWriters.api.IResource;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see scd.readersAndWriters.api.IResource
 * @since SCD2006.02 
 */
public class ReadersHavePriority          
        implements IResource{
    private volatile int snoozeTime = 50;
    private volatile int rest = 5000;
    private volatile int nreaders;
    private volatile boolean isWriting;    
    
    /**
     * Creates a new instance of ReaderPriorityMonitor
     */
    public ReadersHavePriority() {       
        nreaders = 0;
        isWriting = false;             
    }
        
    public synchronized int acquireRead() 
    throws InterruptedException{ 
        /* 
         * Leitor espera enquanto houver escritor escrevendo 
         * para depois atualizar o numero de leitores.
         */
        while(isWriting) wait();                                       
        nreaders++;        
        return nreaders;
    }
        
    public synchronized int releaseRead(){
        /* 
         * Atualiza o numero de leitores e notifica outros threads (escritores)
         * que estiverem esperando.
         */
        nreaders--;
        if(nreaders==0) notifyAll();        
        return nreaders;
    }
    
    public synchronized void acquireWrite()
    throws InterruptedException{
        //enquanto existir escritor escrevendo ou leitor lendo, espera
        while(isWriting || nreaders > 0) wait();        
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
