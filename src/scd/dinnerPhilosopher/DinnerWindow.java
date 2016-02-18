/*
 * DinnerWindow.java
 *
 * Created on 24 de Novembro de 2006
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.dinnerPhilosopher;

import java.awt.event.WindowAdapter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Jôse Bandeira
 * @author Ulisses Mainart
 * @see scd.dinnerPhilosopher.Dinner
 * @see scd.dinnerPhilosopher.Yoke
 * @see scd.dinnerPhilosopher.Philosopher
 * @since SCD2006.02 
 */
public class DinnerWindow extends JFrame{    
    Dinner pnlDinner;
    private JButton btnInit;    
    private JButton btnStop;    
    
    /** Creates a new instance of DinnerWindow */
    public DinnerWindow() {
        setLayout(new BorderLayout());                
        setTitle("Jantar dos Filósofos - Com Monitor");
        
        Dinner pnlDinner = new Dinner();
        Control pnlControl = new Control(pnlDinner);
        add(pnlDinner, BorderLayout.CENTER);                       
        add(pnlControl, BorderLayout.SOUTH);
                
        this.addWindowListener(new WindowHandler());        
        setSize(800, 400);        
        setLocationRelativeTo(null);
        setVisible(true);                
    }
    
    /* ao fechar a janela as threads sao terminadas */
    private class WindowHandler extends WindowAdapter{          
        public void windowClosing(WindowEvent e){
            Dinner.finish();            
            dispose();
        }
    }
    
    public static void main(String args[]){
        new DinnerWindow();
    }
}
