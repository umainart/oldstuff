/*
 * Producer.java
 *
 * Created on 14 de Outubro de 2006, 12:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.producerConsumerSemaphore;

import java.util.*;

public class Producer extends Thread{
    Vector<Buffer> buffer;
    
    /** Creates a new instance of Producer */
    public Producer(String id, Vector<Buffer> b) {
        super(id);
        buffer = b;
    }
    
    public void run(){        
        Buffer btemp;
        
        while(!ProducerConsumerWindow.STOP_THREADS){                                    
            Iterator<Buffer> it = buffer.iterator();            
            while(it.hasNext()){
                btemp = it.next();
                Data data = new Data();
                data.setProducer(getName(), btemp.getName());            
                btemp.insert(data);
                System.out.println(getName()+" produziu em: "+data.getDateProd());
            }                            
        }
    }
    
}
