/*
 * CommandPanel.java
 *
 * Created on 11 de Novembro de 2006, 22:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters.view;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;        
import scd.readersAndWriters.api.*;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see java.util.Observer
 * @see scd.readersAndWriters.api.IReceiver
 * @since SCD2006.02 
 * 
 * Esta classe é responsável pelo controle dos eventos provenientes de 
 * solicitações dos usuários.
 * Delega à classe implementadora de IReceiver as ações de start, stop, pause,
 * etc.
 */
public class CommandPanel extends JPanel{
    private static final String START = "Iniciar";    
    private static final String STOP = "Parar";
    private static final String PAUSE = "Pause";
    private static final String RESUME = "Resume";
    public static String INIT_READER = "Leitores";
    public static String INIT_WRITER = "Escritores";
    
    private final int MAX_VELOCITY = 1000;
    private final int MIN_VELOCITY = 5;
    
    private JPanel pnlEntry, pnlBtn, pnlSld, pnlRest;    
    private JButton btnStartStop;
    private JButton btnPauseResume;      
    private JSpinner spnReaders;
    private JSpinner spnWriters; 
    private JSpinner spnRestWriter;
    private JComboBox cmbInitWith;
    private JLabel lblReaders;
    private JLabel lblWriters;
    private JLabel lblInitWith;      
    private JSlider sldSnooze;

    private IReceiver receiver;
    private ActionListener listener;
        
        
    /** Creates a new instance of CommandPanel */
    public CommandPanel(IReceiver r) {
        receiver = r;
        
        setBorder(BorderFactory.createRaisedBevelBorder());
        pnlEntry = new JPanel();
        pnlBtn   = new JPanel();        
        pnlSld   = new JPanel();
        pnlRest  = new JPanel();
                
        lblReaders = new JLabel("Leitores: ");
        lblWriters = new JLabel("Escritores: ");        
                      
        spnReaders = new JSpinner(new SpinnerNumberModel(5, 1, 15, 1));
        spnWriters = new JSpinner(new SpinnerNumberModel(3, 1, 15, 1));
        spnRestWriter = new JSpinner(new SpinnerNumberModel(10000, 1, 50000, 1));
        
        btnStartStop = new JButton(START);
        btnPauseResume = new JButton(PAUSE);        
        
        lblInitWith = new JLabel(" Começar com: ");
        cmbInitWith = new JComboBox(new Object[]{INIT_READER, INIT_WRITER});
        
        sldSnooze = new JSlider(
                JSlider.HORIZONTAL, MIN_VELOCITY, MAX_VELOCITY, MIN_VELOCITY);
        sldSnooze.setToolTipText("<< lento      rápido >>");
        listener = new CommandListener();        
        btnStartStop.addActionListener(listener);
        btnPauseResume.addActionListener(listener);        
        sldSnooze.addChangeListener(new SliderListener());
        r.modifyVelocity(MAX_VELOCITY);        
        spnRestWriter.addChangeListener(new SpinnerListener());
        
        pnlEntry.add(lblReaders);
        pnlEntry.add(spnReaders);
        pnlEntry.add(lblWriters);
        pnlEntry.add(spnWriters);        
        pnlBtn.add(btnStartStop);
        pnlBtn.add(btnPauseResume);        
        pnlBtn.add(lblInitWith);
        pnlBtn.add(cmbInitWith);
        pnlSld.add(new JLabel("Velocidade: "));
        pnlSld.add(sldSnooze);       
        pnlRest.add(spnRestWriter);
                        
        pnlSld.setBorder(BorderFactory.createLoweredBevelBorder());
        btnPauseResume.setEnabled(false);        
                                                        
        add(pnlEntry);
        add(pnlBtn);  
        add(pnlSld);
        add(pnlRest);
    }
             
    private class CommandListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){ //execute()
            
            if(evt.getSource().hashCode() == btnStartStop.hashCode()){
                if(btnStartStop.getText().equals(START)){
                    int nr = Integer.parseInt(spnReaders.getValue().toString());
                    int nw = Integer.parseInt(spnWriters.getValue().toString());
                    receiver.init(nr, nw);
                    receiver.start(IReceiver.START_ALL, 
                            cmbInitWith.getSelectedItem().toString());
                    btnStartStop.setText(STOP);                    
                    btnPauseResume.setEnabled(true);
                    cmbInitWith.setEnabled(false);
                    lblInitWith.setEnabled(false);
                    spnReaders.setEnabled(false);
                    spnWriters.setEnabled(false);
                    lblReaders.setEnabled(false);
                    lblWriters.setEnabled(false);
                }
                else{ 
                    System.out.println("Botão stop apertado.");
                    receiver.stop(IReceiver.STOP_ALL);
                    btnStartStop.setText(START);
                    btnPauseResume.setText(PAUSE);                    
                    btnPauseResume.setEnabled(false);
                    cmbInitWith.setEnabled(true);
                    lblInitWith.setEnabled(true);
                    spnReaders.setEnabled(true);
                    spnWriters.setEnabled(true);
                    lblReaders.setEnabled(true);
                    lblWriters.setEnabled(true);
                }                
            }
            
            if(evt.getSource().hashCode() == btnPauseResume.hashCode()){
                if(btnPauseResume.getText().equals(PAUSE)){
                    receiver.pause(IReceiver.PAUSE_ALL, true);
                    btnPauseResume.setText(RESUME);
                }
                else{ 
                    receiver.pause(IReceiver.PAUSE_ALL, false);
                    btnPauseResume.setText(PAUSE);
                }                                
            }
                        
                                        
        }
    } //fim inner-class CommandListener
    
    private class SliderListener implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            System.out.println("Slider "+
                    (int)((MIN_VELOCITY*MAX_VELOCITY)/sldSnooze.getValue()));
            receiver.modifyVelocity(
                    (int)((MIN_VELOCITY*MAX_VELOCITY)/sldSnooze.getValue()));
        }
    }
    
    private class SpinnerListener implements ChangeListener{
        public void stateChanged(ChangeEvent e){
           try{
            int time = Integer.parseInt(spnRestWriter.getValue().toString());
            receiver.modifyRestTime(time);
           }catch(Exception ex){
               JOptionPane.showMessageDialog(null, "Valor inválido!");
           }
           
        }
    }
            
}
