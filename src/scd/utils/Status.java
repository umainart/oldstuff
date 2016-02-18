/*
 * Status.java
 *
 * Created on 15 de Novembro de 2006, 22:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.utils;

public class Status {
    //-- readers and writers
    public static final String CREATED     = "º criado.";
    public static final String BLOCKED  = "º bloqueado.";
    public static final String WAITING  = "º esperando.";
    public static final String FINISHED = "º terminou.";
    public static final String READING  = "º lendo.";
    public static final String WRITING  = "º escrevendo.";
                    
    private String status;    
            
    public Status(String status){ 
        this.status = status;         
    }    
    public String getStatus(){ return status; }
    public void setStatus(String status) { this.status = status; }
    public int getStatusImage(){
        if(status.endsWith(CREATED)) return 0;
        if(status.endsWith(BLOCKED)) return 1;
        if(status.endsWith(WAITING)) return 1;
        if(status.endsWith(READING)) return 0;
        if(status.endsWith(WRITING)) return 0;
        if(status.endsWith(FINISHED)) return 2;
        return -1;
    }
}
