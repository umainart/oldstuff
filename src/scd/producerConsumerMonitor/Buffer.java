/*
 * Buffer.java
 *
 * Created on 14 de Outubro de 2006, 11:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.producerConsumerMonitor;

import javax.swing.*;
import java.awt.*;

//monitor
public class Buffer extends JProgressBar{
    public static int BUFFER_SIZE = 10;
    private volatile int acc, in, out;
    private Data[] data;
    private String name;      
    
    /** Creates a new instance of Buffer */
    public Buffer(String name) {               
        this.name = name; 
        data = new Data[BUFFER_SIZE];
        acc = 0; in = 0; out = 0;        
        setMinimum(0);
        setMaximum(BUFFER_SIZE);
        setValue(0);
    }
    
    public synchronized void insert(Data dt){
        while(acc == BUFFER_SIZE) 
            try{ wait(); }
            catch(InterruptedException e){ e.printStackTrace();}
        
        data[in] = dt;
        in = (in + 1) % BUFFER_SIZE;
        acc++;
        this.setValue(acc);
        
        try{
            Thread.sleep(1000);            
        }catch(InterruptedException e){ e.printStackTrace();}
                
        if (acc==1){ notify(); System.out.println("Produtor acordou consumidor");}                            
        System.out.println("Produtor produziu cont="+acc);
        
    }
    
    public synchronized Data remove(){
                
        while(acc == 0)
            try{ wait(); }
            catch(InterruptedException e){ e.printStackTrace(); }
        
        acc--;
        Data d = data[out];
        out = (out + 1) % BUFFER_SIZE;
        this.setValue(acc);               
        try{
            Thread.sleep(100);            
        }catch(InterruptedException e){ e.printStackTrace();}
        
        if(acc==BUFFER_SIZE-1) {notify(); System.out.println("Consumidor acordou produtor");}
        System.out.println("Consumidor consumiu cont="+acc);
        
        return d;
    }
    
    public String getName(){
        return name;
    }
    
}
