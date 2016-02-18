/*
 * Control.java
 *
 */

package scd.dinnerPhilosopher;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Jôse Bandeira
 * @author Ulisses Mainart
 * @see scd.dinnerPhilosopher.Philosopher
 * @see scd.dinnerPhilosopher.Yoke
 * @see scd.dinnerPhilosopher.DinnerWindow
 * @since SCD2006.02 
 */
public class Control extends JPanel{
    private Dinner dinner;
    private JButton btnStartStop, btnPauseResume;
    private JSpinner spnNumPhil;
    private JSlider sldThinkVelocity, sldEatVelocity;
    private final int MAX_VELOCITY = 7000;
    private final int MIN_VELOCITY = 500;
    
    /** Creates a new instance of Control */
    public Control(Dinner dinner) {
        this.dinner = dinner;
        btnStartStop = new JButton("Start");
        btnPauseResume = new JButton("Pause");
        spnNumPhil = new JSpinner(new SpinnerNumberModel(5, 1, 12, 1));
        SliderHandler sldHandler = new SliderHandler();
        sldThinkVelocity = new JSlider(
                JSlider.HORIZONTAL, MIN_VELOCITY, MAX_VELOCITY, 2000);
        sldEatVelocity = new JSlider(
                JSlider.HORIZONTAL, MIN_VELOCITY, MAX_VELOCITY, 2500);
        sldEatVelocity.setToolTipText("<< lento      rápido >>");
        sldThinkVelocity.setToolTipText("<< lento      rápido >>");
        sldEatVelocity.addChangeListener(sldHandler);
        sldThinkVelocity.addChangeListener(sldHandler);
        btnPauseResume.setEnabled(false);
        ButtonHandler btnHandler = new ButtonHandler();
        btnStartStop.addActionListener(btnHandler);
        btnPauseResume.addActionListener(btnHandler);
        this.add(btnStartStop);
        this.add(btnPauseResume);
        this.add(spnNumPhil);
        this.add(new JLabel("Pensando:"));
        this.add(sldThinkVelocity);
        this.add(new JLabel("Comendo:"));
        this.add(sldEatVelocity);
        this.setVisible(true);
    }    
    
    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == btnStartStop){
                
                if(btnStartStop.getText().equalsIgnoreCase("Start")){
                    dinner.init(Integer.parseInt(
                        spnNumPhil.getValue().toString()));                    
                    dinner.start();                     
                    btnStartStop.setText("Stop");
                    btnPauseResume.setEnabled(true);
                    spnNumPhil.setEnabled(false);
                }else{//stop
                    dinner.stop();
                    btnStartStop.setText("Start");
                    btnPauseResume.setEnabled(false);
                    spnNumPhil.setEnabled(true);
                }
                
            }else{ //pause ou resume                
                if(btnPauseResume.getText().equalsIgnoreCase("Pause")){                    
                    dinner.pause(true);
                    btnPauseResume.setText("Resume");
                    btnStartStop.setEnabled(false);
                }else{
                    dinner.pause(false);
                    btnPauseResume.setText("Pause");
                    btnStartStop.setEnabled(true);
                }
                
            }
        }
    }
    
    private class SliderHandler implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            if(e.getSource() == sldEatVelocity){
                Philosopher.setEatTime(                        
                 (int)((MIN_VELOCITY*MAX_VELOCITY)/sldEatVelocity.getValue()));
            }else{ // think
               Philosopher.setThinkTime(
               (int)((MIN_VELOCITY*MAX_VELOCITY)/sldThinkVelocity.getValue()));
                
            }
        }
    }
}
