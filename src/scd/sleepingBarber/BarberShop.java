/*
 * BarberShop.java
 *
 * Created on 3 de Dezembro de 2006, 02:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package scd.sleepingBarber;

import scd.utils.Semaphore;

/**
 * @author Jôse Bandeira
 * @author Ulisses Mainart
 * @see java.lang.Thread
 * @see scd.utils.Semaphore
 * @see scd.sleepingBarber.Barber
 * @see scd.sleepingBarber.Customer
 * @see scd.sleepingBarber.SleepingBarberWindow
 * @since SCD2006.02 
 */
public class BarberShop extends Thread{

    //Objetos compartilhados
    // Semáforo de clientes esperando pelo serviço
    public static Semaphore customers = new Semaphore(0);

    // Semáforo de barbeiros esperando por clientes
    public static Semaphore barbers = new Semaphore(0);

    // Semáforo para exclusão mútua
    public static Semaphore mutex = new Semaphore(1);

    // Clientes esperando para cortar o cabelo
    public static int waiting = 0;
    
    // clientes que foram embora
    public static int goAway = 0;

    // numero de cadeiras para os clientes esperarem
    public static int nChairs = 0;
    
    //numero de barbeiros configuravel atraves de SleepingBarberWindow
    public static int nBarbers = 0;        
    
    //flags para controles auxiliares de inicio, parada e fechamento da janela
    public static boolean stop = false;
    public static boolean pause = false;
    public static boolean windowClosing = false;        
    private boolean started = false;    
    
    //instancia da janela
    private SleepingBarberWindow sbw;
    
    public void init(){        
        sbw = new SleepingBarberWindow(this);
        sbw.setSize(800, 500);
        sbw.setVisible(true);
        sbw.setTitle(" Problema do Barbeiro Dorminho - com semáforo");
        sbw.setLocationRelativeTo(null);
    }
    
    public static void pause(){
        while(pause && ! stop) if(windowClosing) break;
    }
    
    public static void stop(boolean flag){
        stop = flag;
    }
    
    public boolean isStarted(){
        return started;
    }
                
    /*
     * Esta thread é responsável por criar:     
     * ==> barbeiros ao ser iniciada
     * ==> clientes consecutivamente
     */
    public void run(){     
        started = true;
        boolean stoped = false;
        
        Customer customer;                
        createBarbers();
        
        int customerNumber = 0;
        while(!windowClosing){
            pause(); //atributo PAUSE foi setado
            
            customer = new Customer(customerNumber++, sbw);            
            customer.start();

            //gap entre as criacoes de clientes
            try {
                sleep(1000);
            } catch(InterruptedException ie) { ie.printStackTrace(); }                                                                         

            /*
             * Enquanto o atributo stop estiver setado com true,
             * a thread libera cpu para outro. É parado o processamento, caso
             * ocorra o fechamento da janela.
             */
            while(stop) { 
                stoped = true; 
                yield(); 
                if(windowClosing){
                    stoped = false;
                    break;
                }                
            }
            
            /*
             * Caso tenha ocorrido um comando de stop, é necessário 
             * reconfigurar alguns atributos para evitar inconsistâncias na
             * exibição dos dados. Além da ocorrência da exceção 
             * IllegalThreadStateException, após reinício (comando start).
             */
            if(stoped) { 
                createBarbers(); 
                stoped = false; 
                customerNumber = 0;
                goAway = 0;
                sbw.setCustomerNotService(goAway);
            }                        
        }                    
    }

    private void createBarbers(){
        Barber barber;
        for(int i=0; i < nBarbers; i++) {            
            barber = new Barber(i, sbw);            
            barber.start();
        }
    }
        
       
    public static void main(String args[]) {
        BarberShop holder = new BarberShop();
        holder.init();        
    }

}

