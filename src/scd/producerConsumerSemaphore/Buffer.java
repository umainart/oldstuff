/*
 * Buffer.java
 *
 * Created on 14 de Outubro de 2006, 11:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.producerConsumerSemaphore;

import javax.swing.*;
import java.awt.*;
import scd.utils.Semaphore;

public class Buffer extends JProgressBar{
    public static int BUFFER_SIZE = 2; //is max available
    private int acc, in, out;    
    private Data[] data;
    private String name;    
            
    private Semaphore mutex = new Semaphore(1);
    private Semaphore empty = new Semaphore(BUFFER_SIZE); 
    private Semaphore full = new Semaphore(0); 
        
    /** Creates a new instance of Buffer */
    public Buffer(String name) {               
        this.name = name; 
        data = new Data[BUFFER_SIZE];
        acc = 0; in = 0; out = 0;       
        setMinimum(0);
        setMaximum(BUFFER_SIZE);
        setValue(0);
    }
    
    public void  insert(Data d){
        empty.down();
        mutex.down();
        
        acc++;
        data[in] = d;        
        in = (in+1) % BUFFER_SIZE;
        this.setValue(acc);
        
        try{
            Thread.sleep(1000);            
        }catch(InterruptedException e){ e.printStackTrace();}
        
        if(acc == BUFFER_SIZE){
            System.out.println(d.getProducer()+" produziu. Buffer cheio");            
        }else{
            System.out.println(d.getProducer()+" produziu dado Buffer ="+acc);
        }
        
        mutex.up();
        full.up();
    }
    
    public Data remove(){
        Data d;
        
        full.down();
        mutex.down();
        
        --acc;
        d = data[out];
        out = (out + 1)%BUFFER_SIZE;
        
        if(acc == 0){
            System.out.println("Consumidor consumiu dado. Buffer vazio.");            
        }else{
            System.out.println("Consumidor consumiu dado Buffer= "+acc);
        }
        
        mutex.up();
        empty.up();
        
        return d;
    }
    
    public String getName(){
        return name;
    }
    
}
