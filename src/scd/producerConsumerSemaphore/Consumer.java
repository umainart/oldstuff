/*
 * Consumer.java
 *
 * Created on 14 de Outubro de 2006, 12:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.producerConsumerSemaphore;

import java.util.*;

public class Consumer extends Thread{
    private Vector<Buffer> buffer;
    
    /** Creates a new instance of Consumer */
    public Consumer(String id, Vector<Buffer> b) {
        super(id);
        buffer = b;
    }
    
    public void run(){        
        Buffer btemp;
        
        while(!ProducerConsumerWindow.STOP_THREADS){                        
            Iterator<Buffer> it = buffer.iterator();
            
            while(it.hasNext()){
                btemp = it.next();
                try{
                    Data data = btemp.remove();
                    data.setConsumer(getName()); 
                    System.out.println(getName()+" consumiu em: "+data.getDateCons());
                }catch(Exception e){
                    System.out.println(getName()+" NÃO consumiu.");
                    e.printStackTrace();
                }                
            }                        
        }
    }
    
}
