/*
 * Parameter.java
 *
 * Created on 26 de Novembro de 2006, 15:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.cigaretteSmokers;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @since SCD2006.02 
 *
 * Esta classe contem parâmetros globais usados para controle da parte gráfica.
 * É usada também para comunicação entre as threads e seus observadores.
 */
public class Parameter {
    private Product p[];
    private String action;     
    private int indexImg=0;
        
    private volatile static int snooze = 1000;
    private volatile static int snoozeAfterDraft = 1000;
    private volatile static int snoozeWithProductOnTable = 1000;
    private volatile static int snoozeSmoking = 1000; 
    
    public static volatile boolean STOP = false;
    
        
    /** Creates a new instance of Parameter */
    public Parameter(Product p[], String action) {
        this.p = new Product[p.length];
        for(int i=0; i < p.length; i++) this.p[i] = p[i];
        this.action = action;
    }
    
    public Parameter(String action){
        this.action = action;
    }
        
    public Parameter(String action, int indexImg){
        this.action = action;        
        this.indexImg = indexImg;
    }
    
    public static void setSnooze(int snz){
        snooze = snz;
    }
           
    public static void snooze(){
        try{
            Thread.sleep(snooze);
        }catch(InterruptedException e){}
    }
    
    public static void snooze(int snz){
        try{
            Thread.sleep(snz);
        }catch(InterruptedException e){}
    }
    
    public static void snoozeAfterDraft() {
        snooze(snoozeAfterDraft);
    }

    public static void snoozeSmoking() {
        snooze(snoozeSmoking);
    }

    public static void snoozeWithProductOnTable() {
        snooze(snoozeWithProductOnTable);
    }
        
    public static void setSnoozeSmoking(int snooze) {
        snoozeSmoking = snooze;
    }
    
    public static void setSnoozeAfterDraft(int snooze) {
        snoozeAfterDraft = snooze;
    }

    public static void setSnoozeWithProductOnTable(int snooze) {
        snoozeWithProductOnTable = snooze;
    }
            
    public static int getSnooze(){
        return snooze;
    }
    
    public Product[] getProducts(){
        return p;
    }
    
    public String getAction(){
        return action;
    }
    
    public int getIndexImage(){
        return indexImg;
    }

    public static int getSnoozeAfterDraft() {
        return snoozeAfterDraft;
    }

    public static int getSnoozeSmoking() {
        return snoozeSmoking;
    }

    public static int getSnoozeWithProductOnTable() {
        return snoozeWithProductOnTable;
    }               
}
