/*
 * MutualExclusion.java
 *
 * Created on 8 de Outubro de 2006, 00:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.bakery;


public abstract class MutualExclusion {
    public static final int TURN_0 = 0;
    public static final int TURN_1 = 1;
    private volatile static int TIME = 1000;
    private volatile static int BEFORECS_TIME = 1000;
    
    public static void criticalSection(){
        try{
            Thread.sleep(TIME);
        }catch(InterruptedException e){}
    }
    
    public static void nonCriticalSection(){
        try{
            Thread.sleep(TIME);       
        }catch(InterruptedException e){}
    }
    
    public static void changeEnteringTime(int time){
        BEFORECS_TIME = time;
    }
    
    public static void changeInCSTime(int time){
        TIME = time;
    }
    
    public static int enteringTime(){
        return BEFORECS_TIME;
    }
    
    public abstract void catchTicket(int t);
    public abstract void enteringCriticalSection(int t);
    public abstract void leavingCriticalSection(int t);
  
    
}
