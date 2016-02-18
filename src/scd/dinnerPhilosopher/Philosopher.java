/*
 * Philosopher.java
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
 * @see java.lang.Thread
 * @see scd.dinnerPhilosopher.Dinner
 * @see scd.dinnerPhilosopher.Yoke
 * @see scd.dinnerPhilosopher.DinnerWindow
 * @since SCD2006.02 
 */

//Cada filosofo é visto como um processo
public class Philosopher extends Thread{ 
	private static int thinkTime=2000, eatTime=2500;
	private int key; 	// Codigo p/ identificar o filosofo
	private int status;     // 0 = pensando , 1 = comendo , 2 = faminto 
	private Dinner dinner; 	 

	public Philosopher(int key,Dinner dinner){ 
		this.key = key; 
		this.dinner = dinner;
	}

	public int getKey(){ 
		return key;
	}

	public void setStatus(int i){
		status = i; 
		switch (i){ 
		case 0 : dinner.setInfo(key, 0); 
		break; 
		case 1 : dinner.setInfo(key, 1); 
		break; 
		case 2 : dinner.setInfo(key, 2); 
		break; 
		} 
	} 

	/* Qdo esta pensando, dorme/bloqueia por determinado tempo: 2000ms)*/
	private void thinking() { 
		try{
			Thread.sleep(thinkTime);
		}
		catch(Exception e){
			System.out.println("Um problema ocorreu quando o filos�fo " 
					+ this.getKey() + " estava pensando. \n" 
					+ e.getMessage());
		}
	}

	/* Qdo esta comendo, dorme/bloqueia por determinado tempo: 2500ms)*/
	private void eating(){
		try{
			Thread.sleep(eatTime);
		} 
		catch(Exception e){
			System.out.println("Um problema ocorreu quando o filosófo " + 
					this.getKey() + " estava comendo. \n" 
					+ e.getMessage());
		} 
	} 

	private void pause(){
		while(dinner.isPaused());
	}

	public static void setThinkTime(int t){
		thinkTime = t;
	}

	public static void setEatTime(int t){
		eatTime = t;
	}

	public void run(){ 
		while(!dinner.isStopped()){	
			setStatus(0); 		// define status: filosofo pensando 
			thinking();                  
			dinner.getFork().pick(this); 	// filosofo pega os garfos
			pause();
			eating(); 				
			dinner.getFork().put(this); 	// filosofo libera os garfos 
		}            
		boolean pass = false;
		while(dinner.getNumPhilosophers()!=0 && !pass){
			dinner.exit();
			pass = true;
		}                 
		System.out.println("Filósofo: "+key+" saiu.");
	}
}