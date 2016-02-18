/*
 * Fork.java
 *
 * Created on 24 de Novembro de 2006
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.dinnerPhilosopher;

/**
 * @author Jôse Bandeira
 * @author Ulisses Mainart
 * @see scd.dinnerPhilosopher.Dinner
 * @see scd.dinnerPhilosopher.Philosopher
 * @see scd.dinnerPhilosopher.DinnerWindow
 * @since SCD2006.02 
 */

public class Fork{ 
    /* vetor de garfos - True(em uso) False(liberado) */
    private boolean[] catched;
    private int nforks;
    
    public Fork(int nforks) { 
        this.nforks = nforks;
        catched = new boolean[nforks]; 
    }


    /*	Pegar Garfos (sincronizado) */
    public synchronized void pick(Philosopher f){
        int key;
        key = f.getKey( ); //identifica qual filósofo quer pegar os garfos 

        /* Enquanto o seu garfo ou o do seu vizinho a direita estiver em uso, 
         * espera atw o(s) garfo(s) ser(em) liberado(s) 
         */		
        while (catched[key] || catched[((key+1)%nforks)]){
            f.setStatus(2); // passa para o estado Faminto 
            try{
                wait();     // espera
            } 	 
            catch(Exception e){
                System.out.println("Um problema ocorreu quando o filosófo " 
                        + key + " estava aguardando para pegar um garfo. \n" 
                        + e.getMessage());	
            }
        } 
        catched[key] = true; 
        catched[((key +1)%nforks)] = true; 
        f.setStatus(1); 	// passa para o estado Comendo 
    } 

    /* Liberar Garfos (sincronizado) */
    public synchronized void put(Philosopher f){
        int key; 
        int aux;

        key = f.getKey( );   // identifica qual filosofo quer liberar os garfos
        catched[key] = false;// libera o seu garfo e o de seu vizinho a direita
        aux = (key+1)%nforks;
        catched[aux] = false; 
        try{
            notifyAll();        // notifica que liberou os garfos
        } 			
        catch(Exception e){
                System.out.println("Um problema ocorreu durante a " +
                        "liberação dos garfos pelo filosófo " + 
                        key + ". \n" + e.getMessage());
        } 
    }

} 


