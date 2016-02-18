/*
 * Reader.java
 *
 * Created on 6 de Novembro de 2006, 00:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters;

import java.util.*;
import scd.readersAndWriters.api.IResource;
import scd.utils.*;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see java.util.Observable
 * @see java.lang.Runnable
 * @see scd.readersAndWriters.api.IResource
 * @since SCD2006.02 
 */
public class Reader extends Observable implements Runnable{    
    private IResource book;
    private int idReader;
    private boolean pause;
    private boolean stop;
        
    /** Creates a new instance of Reader */
    public Reader(int idReader, IResource res) {
        this.idReader = idReader;
        book = res;
        pause = false;
        stop = false;
    }
    
    /* 
     * M�todos para controle da execu��o
     */
    public void pause(boolean p){ pause = p; }
    public boolean isPaused(){ return pause; }
    private void pause() { while(pause && !stop); }
    public void stop(){ stop = true; }
    
    public void run(){        
        int c=0;      
        String txt = String.valueOf(idReader+1);
        while(!stop){     
            
            book.snooze(); //permite a visualizacao do estado 'Querendo Ler'           
            System.out.println("Reader "+idReader+" wants to read.");
            try{
                /* leitor tenta acessar a regiao critica */
                setChanged();                
                notifyObservers(new Status(txt + Status.BLOCKED));                
                c = book.acquireRead();
                book.snooze(); //parada para visualizacao do estado na tela
                pause();
                clearChanged();                                
            }catch(InterruptedException ie){}

            /** notifica aos seus observadores que este leitor esta lendo */
            setChanged();
            notifyObservers(new Status(txt + Status.READING));                        
            System.out.println("Reader "+idReader+" is reading. Count = "+c);
            book.snooze();
            pause();
            clearChanged();
                        
            /** notifica aos seus observadores que terminou de ler */
            c = book.releaseRead(); 
            setChanged();
            notifyObservers(new Status(txt + Status.FINISHED));            
            System.out.println("Reader "+idReader+" is done reading. Count = "+c);
            pause();
            clearChanged();            
        }
    }
    
}
