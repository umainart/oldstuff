/*
 * Data.java
 *
 * Created on 14 de Outubro de 2006, 11:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.producerConsumerSemaphore;

import javax.swing.*;
import java.util.*;

public class Data {
    private String nameProducer = null;
    private String nameConsumer = null; 
    private String nameBuffer = null;
    private Date dateProd = null;
    private Date dateCons = null;
    private StringBuffer text;
    
    /** Creates a new instance of Data */
    public Data() {    
        text = new StringBuffer();       
    }
    
    public void setProducer(String name, String nameBuffer){
        nameProducer = name;
        nameBuffer = nameBuffer;
        dateProd = new Date(System.currentTimeMillis());
        text.append(nameProducer);        
        text.append(" - "+dateProd);
        text.append(" - "+nameBuffer);
    }
    
    public void setConsumer(String name){
        nameConsumer = name;
        dateCons = new Date(System.currentTimeMillis());
        text.append(" - "+nameConsumer);
        text.append(" - "+dateCons);
    }
    
    //-- getters
    public String getProducer(){
        return nameProducer;
    }
    
    public Date getDateProd(){
        return dateProd;
    }
    
    public String getConsumer(){
        return nameConsumer;
    }
    
    public Date getDateCons(){
        return dateCons;
    }

}
