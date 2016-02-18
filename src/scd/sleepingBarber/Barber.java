/*
 * Barber.java
 *
 * Created on 3 de Dezembro de 2006, 02:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.sleepingBarber;

/**
 * @author Jôse Bandeira
 * @author Ulisses Mainart
 * @see java.lang.Thread
 * @see scd.utils.Semaphore
 * @see scd.sleepingBarber.BarberShop
 * @see scd.sleepingBarber.Customer
 * @see scd.sleepingBarber.SleepingBarberWindow
 * @since SCD2006.02 
 */
public class Barber extends Thread {
        private int id;
        private SleepingBarberWindow sbw;

        public Barber(int id, SleepingBarberWindow sbw) {
            this.id = id;
            this.sbw = sbw;
        }

        public void run(){
            while(!BarberShop.stop) {     
                /* configura a tela */
                sbw.freeBarber(String.valueOf(id));
                /* este sera o ponto de sincronizacao apos comando pause */
                BarberShop.pause();                 
                
                BarberShop.customers.down(); /* espera a chegada de clientes */
                
                /* regiao critica p/ atualizacao de waiting e da tela */
                BarberShop.mutex.down(); 
                BarberShop.waiting = BarberShop.waiting - 1;
                sbw.setWaitingCustomers(BarberShop.waiting);             
                BarberShop.barbers.up(); /* acorda barbeiro dorminhoco */                
                BarberShop.mutex.up();
                
                cutHair();
            }
            sbw.freeBarber(String.valueOf(id));
        }

        /* corta o cabelo do cliente, atualizando a tela */
        public void cutHair(){
            System.out.println("Barbeiro " + id + " está cortando cabelo.");
            sbw.busyBarber("Barbeiro "+id+" corta o cabelo.");
            try {
                sleep(7500);
            } catch (InterruptedException ex){ }            
        }
}
