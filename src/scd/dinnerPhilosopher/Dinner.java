/*
 * Dinner.java
 *
 * Created on 24 de Novembro de 2006
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.dinnerPhilosopher;

import java.awt.*; 
import java.awt.Color; 
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.*;

/**
 * @author Jôse Bandeira
 * @author Ulisses Mainart
 * @see scd.dinnerPhilosopher.Philosopher
 * @see scd.dinnerPhilosopher.Fork
 * @see scd.dinnerPhilosopher.DinnerWindow
 * @since SCD2006.02
 */
public class Dinner extends JPanel { 
	private static boolean STOP_THREADS = false;
	private static boolean PAUSE_THREADS = false;

	private final int MenuBarHeight = 0;
	private final int MAX_PHILS = 12;

	private volatile int numPhilosophers, fixNP;
	private Philosopher phils[];
	private Fork fork;
	private Panel pnlPhils[]; 
	private Panel legF, legP, legC; 
	private Label labels[];
	private Label labelLeg, labelP, labelF, labelC;	
	private StopDialog stopdlg=null;

	public Dinner(){            
	}
        
	public void init(int nPhils){
            numPhilosophers = nPhils;
            fixNP = nPhils;
            System.out.println("init");
            setForeground(Color.black);
            setBackground(Color.lightGray);
            setFont(new Font("Dialog",Font.BOLD,12)); 
            setLayout(null); 
		                        
            phils    = new Philosopher[nPhils];                        
            pnlPhils = new Panel[MAX_PHILS];
            labels   = new Label[MAX_PHILS];            
            for(int i=0; i < nPhils; i++){
                phils[i] = new Philosopher(i,this);
            }            
            for(int i=0; i < MAX_PHILS; i++){
                pnlPhils[i] = new Panel(); 
                pnlPhils[i].setLayout(null);
                pnlPhils[i].setForeground(Color.black);
                pnlPhils[i].setBackground(Color.yellow);
                pnlPhils[i].setFont(new Font("Dialog",Font.BOLD,12));
                labels[i] = new Label();
                labels[i].setFont(new Font("Dialog",Font.BOLD,12));
            }
                                    
            //legenda
            legF = new Panel();
            legF.setLayout(null);
            legF.setForeground(Color.black); 
            legF.setBackground(Color.red);      

            legP = new Panel();
            legP.setLayout(null);
            legP.setForeground(Color.black); 
            legP.setBackground(Color.yellow);      

            legC = new Panel();
            legC.setLayout(null);
            legC.setForeground(Color.black); 
            legC.setBackground(Color.green);      

            labelLeg = new Label("Situação do Filósofo",Label.CENTER);
            labelLeg.setFont(new Font("Dialog",Font.BOLD,12));           
            labelP = new Label("Pensando",Label.LEFT); 
            labelP.setFont(new Font("Dialog",Font.ITALIC,12));  
            labelF = new Label("Faminto",Label.LEFT); 
            labelF.setFont(new Font("Dialog",Font.ITALIC,12));  
            labelC = new Label("Comendo",Label.LEFT); 
            labelC.setFont(new Font("Dialog",Font.ITALIC,12));  

            for(int i=0; i < MAX_PHILS; i++ ){
                add(labels[i]);
                add(pnlPhils[i]);
            }
            add(labelLeg); 
            add(legF); add(labelF);
            add(legP); add(labelP);
            add(legC); add(labelC);
                                              
            initialPositionSet();

            // cria objeto garfos
            fork = new Fork(nPhils);                                                           
	}
        
        public void start(){
            STOP_THREADS = false;
            if(phils==null){ 
                phils = new Philosopher[numPhilosophers];
                for(int i=0; i < numPhilosophers; i++)
                    phils[i] = new Philosopher(i, this);
            }
            for(int i=0; i < numPhilosophers; i++){
                phils[i].start();
            }
        }
        
        public void pause(boolean p){
            PAUSE_THREADS = p;
        }
        
        public void stop() {
            STOP_THREADS = true;
            phils = null;            
            this.removeAll();
            stopdlg = new StopDialog();
            System.gc();
        }    
        
        public synchronized void exit(){
            numPhilosophers--;
            if(stopdlg!=null){
                stopdlg.update();
                if(numPhilosophers==0) 
                    stopdlg.dispose();
            }
        }
        
        public static void finish() {            
            PAUSE_THREADS = false; //sem depender da validação na GUI
            STOP_THREADS = true;            
            System.gc();
        }
	                
        public boolean isStopped(){
            return STOP_THREADS;
        }
        public boolean isPaused(){
            return PAUSE_THREADS;
        }
        
        public int getNumPhilosophers(){
            return numPhilosophers;
        }
        
        public Fork getFork(){
            return fork;
        }
        
	public void setInfo(int key, int status){
           Color color=null;
           System.out.println("setInfo");
           switch(status){
               case 0: color = Color.yellow; break;
               case 1: color = Color.green;  break;
               case 2: color = Color.red;    break;
           }           
           setColor(pnlPhils[order[fixNP-1][key]], color, 
                   labels[order[fixNP-1][key]], String.valueOf(key+1));           
	}
	
	public void setColor(Panel p, Color c, Label l, String t){ 
            System.out.println("setCor");
            p.setBackground(c); 
            System.out.println("setText " + t);
            l.setText(t); 
	}   
                                        
        /*--------> renderização <-------*/
        private int bounds[][] = {
            {300, 40, 310, 20}, {340, 70, 380, 80}, {380, 110, 420, 120},
            {400, 150, 440, 160}, {380, 190, 420, 200}, {340, 230, 380, 240},
            {300, 260, 310, 290}, {260, 230, 240, 240}, {220, 190, 200, 200},
            {200, 150, 170, 160}, {220, 110, 190, 120}, {260, 70, 230, 80}
        };
        
        private int order[][] = {
          {0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
          {0, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
          {0, 3, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1},
          {0, 3, 6, 9, -1, -1, -1, -1, -1, -1, -1, -1},
          {0, 3, 5, 7, 9, -1, -1, -1, -1, -1, -1, -1},
          {0, 1, 3, 6, 9, 11, -1, -1, -1, -1, -1, -1},
          {0, 1, 3, 5, 7, 9, 11, -1, -1, -1, -1, -1},
          {0, 1, 3, 5, 6, 7, 9, 11, -1, -1, -1, -1},
          {0, 1, 2, 3, 5, 6, 7, 9, 11, -1, -1, -1},
          {0, 1, 2, 3, 5, 6, 7, 9, 10, 11, -1, -1},
          {0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, -1},
          {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}            
        };
        
	public void initialPositionSet(){
            int hPhil, wPhil;
            int hLbl, wLbl;
            System.out.println("initialPositionSet");             
            setSize(700, 300);
                
            hPhil = 30; wPhil = 30;            
            hLbl = 19; wLbl = 17;
                        
            for(int j=0; j < fixNP; j++){
                if(order[(fixNP-1)][j]==-1) break;                
                pnlPhils[order[(fixNP-1)][j]].setBounds(
                        bounds[order[(fixNP-1)][j]][0], 
                        bounds[order[(fixNP-1)][j]][1], wPhil, hPhil);                 
                labels[order[(fixNP-1)][j]].setBounds(
                        bounds[order[(fixNP-1)][j]][2], 
                        bounds[order[(fixNP-1)][j]][3], wLbl, hLbl);
                labels[order[(fixNP-1)][j]].setText(String.valueOf(j+1));
                labels[order[(fixNP-1)][j]].setAlignment(Label.LEFT);
            }            
                        
            labelLeg.setBounds(600,100,150,19); 
            legP.setBounds(600,120,10,30); 
            labelP.setBounds(630,120,100,30);
            legF.setBounds(600,150,10,30); 
            labelF.setBounds(630,150,100,30); 
            legC.setBounds(600,180,10,30); 
            labelC.setBounds(630,180,100,30);
	}
	                	               
        private class StopDialog extends JDialog{
            private JLabel lblPhilsExit;
            public StopDialog(){
                setTitle("Aviso");
                lblPhilsExit = new JLabel("Espere até que o número de " +
                        "filósofos ativos seja 0." + 
                        " Número de filósofos ativos: "+numPhilosophers);
                add(lblPhilsExit);
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
            }
            public void update(){
                lblPhilsExit.setText("Espere até que o número de filósofos " +
                        "ativos seja 0." +
                        " Número de filósofos ativos: "+numPhilosophers);
            }
        }
}
