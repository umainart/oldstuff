/*
 * WritersHavePriority.java
 *
 * Created on 8 de Novembro de 2006, 02:01
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
public class WritersHavePriority implements IResource{
    private volatile int snoozeTime = 50;
    private volatile int rest = 10000;
    
    private int nreaders, nwriters;
    private Semaphore wmutex, rmutex;
    private Semaphore wsema, rsema, rqueue;
    
    /**
     * Creates a new instance of WritersHavePriority
     */
    public WritersHavePriority() {
        nreaders = 0;
        nwriters = 0;
        wmutex = new Semaphore(1);
        rmutex = new Semaphore(1);
        wsema = new Semaphore(1);;
        rsema = new Semaphore(1);
        rqueue = new Semaphore(1);
    }
    
    public int acquireRead(){
        try{
            rqueue.acquire();
            rsema.acquire(); /* bloqueia enquanto houver escritor */
        }catch(InterruptedException e){}
        
        /* regiao critica para atualizacao do numero de leitores */
        try{
            rmutex.acquire(); 
        }catch(InterruptedException e){}
        nreaders++;
        if(nreaders==1) 
            try{
                wsema.acquire(); /* indica entrada do 1o. leitor */
            }catch(InterruptedException e){}
        rmutex.release();
        /* regiao critica para atualizacao do numero de leitores */
        
        rsema.release();
        rqueue.release();
        return nreaders;
    }
    
    public int releaseRead(){
        /* regiao critica para atualizacao do numero de leitores */
        try{
            rmutex.acquire();
        }catch(InterruptedException e){}
        nreaders--;
        if(nreaders==0) wsema.release();
        rmutex.release();
        /* regiao critica para atualizacao do numero de leitores */
        return nreaders;
    }
    
    public void acquireWrite(){
        try{
            /* regiao critica para atualizacao do numero de escritores */        
            wmutex.acquire();
        }catch(InterruptedException e){}
        nwriters++;
        if(nwriters==1) 
            try{
                rsema.acquire();
            }catch(InterruptedException e){}
        wmutex.release();        
        try{
            wsema.acquire();
        }catch(InterruptedException e){}
        /* regiao critica para atualizacao do numero de escritores */
    }
    
    public void releaseWrite(){
        /* regiao critica para atualizacao do numero de escritores */
        wsema.release();
        try{
            rmutex.acquire();
        }catch(InterruptedException e){}
        nwriters--;
        if(nwriters==0) rsema.release();
        rmutex.release();
        /* regiao critica para atualizacao do numero de escritores */                        
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
