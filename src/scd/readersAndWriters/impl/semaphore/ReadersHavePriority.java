/*
 * ReadersHavePriority.java
 *
 * Created on 6 de Novembro de 2006, 01:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters.impl.semaphore;

import scd.readersAndWriters.*;
import scd.readersAndWriters.api.IResource;
import java.util.concurrent.Semaphore;


/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see scd.readersAndWriters.api.IResource
 * @see java.util.concurrent.Semaphore
 * @since SCD2006.02 
 */
public class ReadersHavePriority implements IResource{
    private volatile int snoozeTime = 50;
    private int nreaders;
    private Semaphore mutex, wsema;
    private volatile int rest = 5000;
    
    /**
     * Creates a new instance of ReadersHavePriority
     */
    public ReadersHavePriority() {
        super();
        nreaders=0;
        mutex = new Semaphore(1);
        wsema = new Semaphore(1);
    }
    
    public void snooze(){
        try{ Thread.sleep(snoozeTime); }
        catch(InterruptedException e){ e.printStackTrace(); }
    }
 
    public int acquireRead(){
        /* Regiao critica para atualizacao do leitor */
        try{
            mutex.acquire();
        }catch(InterruptedException e){}
        nreaders++;
        if(nreaders==1) 
            try{
                wsema.acquire(); //entrada do 1o. leitor
            }catch(InterruptedException e){}
        mutex.release();
        /* Regiao critica para atualizacao do leitor */
        return nreaders;
    }
    
    public void acquireWrite(){
        try{
            wsema.acquire();
        }catch(InterruptedException e){}
    }
    
    public int releaseRead(){
        /* Regiao critica para atualizacao do leitor */
        try{
            mutex.acquire();
        }catch(InterruptedException e){}
        nreaders--;
        if(nreaders==0) wsema.release(); //indica saida do ultimo leitor
        mutex.release();
        /* Regiao critica para atualizacao do leitor */
        return nreaders;
    }
    
    public void releaseWrite(){
        wsema.release();
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
