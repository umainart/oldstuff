/*
 * Worker.java
 *
 * Created on 8 de Outubro de 2006, 00:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.bakery;

import javax.swing.*;

public class Worker extends Thread{
    private int id;
    private String name;
    private MutualExclusion shared;
    private BakeryWindow updater;
    
    /** Creates a new instance of Worker */
    public Worker(String n, int i, MutualExclusion s) {
        name = n;
        id = i;
        shared = s;        
    }
    
    public void setUpdater(BakeryWindow updater){
        this.updater = updater;
    }
    
    public void run(){        
        while(!BakeryWindow.STOP_THREADS){     
            System.out.println(name + " pegou ticket.");            
            shared.catchTicket(id);
            
            System.out.println(name + " entrou no protocolo de entrada.");            
            updater.getPnlGetTicket().remove(updater.getProcess(id));            
            updater.getPnlQueue().add(updater.getProcess(id));
            updater.getPnlGetTicket().updateUI();            
            updater.getPnlQueue().updateUI();
            shared.enteringCriticalSection(id);
            
            try{
                Thread.sleep(MutualExclusion.enteringTime());       
            }catch(InterruptedException e){}
                                    
            System.out.println(name + " entrou na região crítica.");                        
            updater.getPnlQueue().remove(updater.getProcess(id));            
            updater.getPnlCashDesk().add(updater.getProcess(id));
            updater.getPnlQueue().updateUI();
            updater.getPnlCashDesk().updateUI();                        
            MutualExclusion.criticalSection();
            
            System.out.println(name + " entrou no protocolo de saída.");                        
            shared.leavingCriticalSection(id);
            
            updater.getPnlCashDesk().remove(updater.getProcess(id));            
            updater.getPnlGetTicket().add(updater.getProcess(id));            
            updater.getPnlCashDesk().updateUI();
            updater.getPnlGetTicket().updateUI();            
                       
            System.out.println(name + " saiu da região crítica.");                        
            MutualExclusion.nonCriticalSection();                      
        }
    }
    
    
}
