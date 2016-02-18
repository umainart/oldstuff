/*
 * Writer.java
 *
 * Created on 6 de Novembro de 2006, 00:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters;

import scd.readersAndWriters.api.IResource;


import java.util.*;
import scd.utils.*;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see java.util.Observable
 * @see java.lang.Runnable
 * @see scd.readersAndWriters.api.IResource
 * @since SCD2006.02 
 */
public class Writer extends Observable implements Runnable{
    private IResource book;
    private int idWriter;
    private boolean pause;
    private boolean stop;
    
        
    /** Creates a new instance of Writer */
    public Writer(int idWriter, IResource res) {
        this.idWriter = idWriter;
        book = res;
        pause = false;
        stop = false;
    }
  
    /* métodos para controle do fluxo de execucao */
    public void pause(boolean p){ pause = p; }
    public boolean isPaused(){ return pause; }
    private void pause() { while(pause && !stop); }
    public void stop(){ stop = true; }    
    
    public void run() {      
        String txt = String.valueOf(idWriter+1);
        while(!stop){      
            
            book.snooze(); //parada para exibicao do estado atual.
                        
            System.out.println("Writer "+idWriter+" wants to write.");
            try{
                /* escritor tenta adquirir escrita */
                setChanged();       
                notifyObservers(new Status(txt + Status.BLOCKED));                
                book.acquireWrite();
                pause(); //verifica se o bot�o pause foi clicado
                clearChanged();
            }catch(InterruptedException e){}
                  
            /* escritor escreve no livro e notifica seus observadores */
            setChanged();
            notifyObservers(new Status(txt + Status.WRITING));
            System.out.println("Writer "+idWriter+" is writing.");            
            book.snooze();
            pause();
            clearChanged();
                        
            /* termina, atualiza seu estado notificando seu observadores */
            book.releaseWrite();            
            setChanged();            
            notifyObservers(new Status(txt + Status.FINISHED));
            System.out.println("Writer "+idWriter+" is write.");            
            pause();
            clearChanged();   
            book.rest();
       } 
    }
}
