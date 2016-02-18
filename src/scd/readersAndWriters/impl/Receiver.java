/*
 * Receiver.java
 *
 * Created on 15 de Novembro de 2006, 19:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters.impl;

import java.util.*;
import scd.readersAndWriters.*;
import scd.readersAndWriters.view.*;
import scd.readersAndWriters.api.*;
import scd.utils.*;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see scd.readersAndWriters.api.IReceive
 * @see scd.readersAndWriters.view.RWGroupWindow
 * @see scd.readersAndWriters.Reader
 * @see scd.readersAndWriters.Writer
 * @see scd.utils.Splash
 * @since SCD2006.02 
 * 
 * Esta classe trata os eventos solicitados pela classe RWGroupWindow 
 */
public final class Receiver implements IReceiver{    
    private IResource resource;
    private RWGroupWindow rwwindow;
    //private Thread[] threadPool;
    private Reader[] readers;
    private Writer[] writers;
    private int nThreads;
    private int nActiveThreads;
    private int nReaders, nWriters;
    private int nActiveReaders, nActiveWriters;   
    private boolean start;
        
    /**
     * Creates a new instance of Receiver
     */
    public Receiver(IResource resource) {
        nThreads = 0;
        nReaders = 0;
        nWriters = 0;        
        this.resource = resource;
        rwwindow = new RWGroupWindow(this, resource.getClass().getName());
        rwwindow.setSize(1000, 725);
        //rwwindow.setLocationRelativeTo(null);
        rwwindow.setVisible(true);
        start = false;
    }
        
    /*
     * Realiza a criacao das threads leitoras e escritoras, associando-as
     * a elementos graficos que serao observadores.
     */
    public void init(int nReaders, int nWriters){                
        Reader r; Writer w;
        this.nReaders = nReaders;        
        this.nWriters = nWriters; 
        this.nThreads = nReaders + nWriters;
        
        readers = new Reader[nReaders];
        writers = new Writer[nWriters];

        rwwindow.getReaderPanel().removeAllReaders();
        rwwindow.getWriterPanel().removeAllWriters();
        
        for(int i=0; i < nReaders; i++){
            readers[i] = new Reader(i, resource);                         
            
            
            readers[i].addObserver(rwwindow.getReaderPanel().
                    addReader(String.valueOf(i+1)+Status.CREATED));                                    
        }
        
        for(int i=0; i < nWriters; i++){
            writers[i] = new Writer(i, resource);
                                                            
            //esta ordem eh importante: metodo notifyObservers - FILO
            writers[i].addObserver(rwwindow.getResourcePanel());
            
            writers[i].addObserver(rwwindow.getWriterPanel().
                    addWriter(String.valueOf(i+1)+Status.CREATED));                                                
        }        
        start = false;
    }
            
    /*
     * Faz o start das threads de acordo com a opcao 'comecar com' selecionada
     * na tela.
     */
    public void start(int ind, String s) {
        start = true;
        //throws Exception, ArrayIndexOutOfBoundsException{                            
        if(ind == IReceiver.START_ALL){
            if(s.equals(CommandPanel.INIT_READER)){
                for(int i=0; i < readers.length; i++){               
                   Thread t = new Thread(readers[i]);
                   t.start();               
                }  

                for(int i=0; i < writers.length; i++){
                    Thread t = new Thread(writers[i]);            
                    t.start();
                }
            }else{
                for(int i=0; i < writers.length; i++){
                    Thread t = new Thread(writers[i]);            
                    t.start();
                }
                
                for(int i=0; i < readers.length; i++){               
                   Thread t = new Thread(readers[i]);
                   t.start();               
                }  
            }
        }
    }
            
    /*
     * Faz uma pausa em todas as threads, chamando o metodo pause() 
     * do leitor ou do escritor.
     */
    public void pause(int index, boolean val){
        if(index == IReceiver.PAUSE_ALL){
            for(int i=0; i < readers.length; i++)
                readers[i].pause(val);
            
            for(int i=0; i < writers.length; i++)
                writers[i].pause(val);
        }
    }
    
    /*
     * Realiza a parada das threads, exibindo um splash com tempo determinado
     * evitando que o usuario clique no botao start, dispando uma
     * IllegalThreadStateException.
     */
    public void stop(int ind){
        if(ind == IReceiver.STOP_ALL){
            Splash s = new Splash();
            s.openSplash(new Long(10000), rwwindow);
            for(int i=0; i < readers.length; i++){                
                 readers[i].stop();                          
            }
            
            for(int i=0; i < writers.length; i++){                
                writers[i].stop();
            }                       
        }                
    }
    
     /*
     * Realiza a parada das threads.     
     */
    public void stop(){                    
        for(int i=0; i < readers.length; i++){                
             readers[i].stop();                          
        }

        for(int i=0; i < writers.length; i++){                
            writers[i].stop();
        }                                            
    }
       
    /* 
     * Modifica a velocidade de acordo com a selecao do componente (JSlider)
     * na janela RWGroupWindow.
     */
    public void modifyVelocity(int time){
        resource.setSnooze(time);
    }
    
    /* 
     * Modifica o tempo de espera do escritor para continuar a executar seu 
     * loop.     
     */
    public void modifyRestTime(int time){
        resource.setRest(time);
    }
    
     public IResource getResource(){
        return resource;
    }
    
    public void setResource(IResource r){
        resource = r;
    }        
    
    public boolean isStarted(){
        return start;
    }
}
