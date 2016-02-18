/*
 * BakeryWindow.java
 *
 * Created on 16 de Outubro de 2006, 02:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.bakery;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import scd.utils.*;

public class BakeryWindow extends JFrame{
    public static boolean STOP_THREADS = false;
    
    private JPanel pnlComandos;
    private JPanel pnlCashDesk;
    private JPanel pnlQueue;
    private JPanel pnlGetTicket;
    private JPanel pnlGrpSouth;
    
    private JSpinner spNumThreads;
    private JButton btnInit, btnStop;
    private JSlider sldVelocity;
    
    private JRadioButton[] rbProcesses;
    private Worker processes[];
    private MutualExclusion mutex;
    
    private final int MAX_VELOCITY = 5000;
    private final int MIN_VELOCITY = 100;
    private final int DEFAULT_VELOCITY = 1000;
    
    /** Creates a new instance of BakeryWindow */
    public BakeryWindow() {
        this.addWindowListener(new WindowListener());
        STOP_THREADS = false;
        initComponents();
        setTitle("Representação - Bakery Algorithm - Lamport");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public JRadioButton getProcess(int id){
        return rbProcesses[id];
    }
    
    public JPanel getPnlGetTicket(){
        return pnlGetTicket;
    }
    
    public JPanel getPnlQueue(){
        return pnlQueue;
    }
    
    public JPanel getPnlCashDesk(){
        return pnlCashDesk;
    }
    
    private void initComponents(){
        setLayout(new BorderLayout(10, 10));
        
        pnlCashDesk = new JPanel();
        pnlQueue = new JPanel();
        pnlGetTicket = new JPanel();
        pnlGrpSouth = new JPanel();
        
        pnlCashDesk.setBorder(BorderFactory.
                createTitledBorder(" Caixa da Padaria (RC)"));
        pnlQueue.setBorder(BorderFactory.createTitledBorder(" Fila "));
        pnlGetTicket.setBorder(BorderFactory.
                createTitledBorder(" Pegando um ticket "));
        
        pnlGrpSouth.setLayout(new BorderLayout());
        pnlGrpSouth.add(createComandos(), BorderLayout.SOUTH);
        pnlGrpSouth.add(pnlGetTicket, BorderLayout.CENTER);
        
        Icon img = new ImageIcon("images/cashdesk.jpg");
        JLabel lblImg = new JLabel();
        lblImg.setIcon(img);
        pnlCashDesk.add(lblImg);
        
        add(pnlCashDesk, BorderLayout.WEST);
        add(pnlQueue, BorderLayout.CENTER);
        add(pnlGrpSouth, BorderLayout.SOUTH);
        
    }
    
    private JPanel createComandos(){
        JPanel pnlVelocity = new JPanel();
        pnlComandos = new JPanel();
        spNumThreads = new JSpinner();
        spNumThreads.setToolTipText("Número de Processos");
        spNumThreads.setValue(4);                        
        btnInit = new JButton("Iniciar");
        btnStop = new JButton("Parar");
        btnStop.setEnabled(false);
        sldVelocity = new JSlider(JSlider.HORIZONTAL, MIN_VELOCITY, 
                MAX_VELOCITY, DEFAULT_VELOCITY);
        sldVelocity.setToolTipText("<< lento      rápido >>");
        sldVelocity.addChangeListener(new SliderListener());        
        pnlVelocity.add(new JLabel("Velocidade:"));
        pnlVelocity.add(sldVelocity);
        pnlComandos.add(spNumThreads);
        pnlComandos.add(btnInit);
        pnlComandos.add(btnStop);
        pnlComandos.add(pnlVelocity);
        pnlComandos.setBorder(BorderFactory.createRaisedBevelBorder());
        Handler h = new Handler(this);
        btnInit.addActionListener(h);
        btnStop.addActionListener(h);
        return pnlComandos;
    }
            
    private class Handler implements ActionListener{
        BakeryWindow bw;
        public Handler(BakeryWindow bw){
            this.bw = bw;
        }
        
        public void actionPerformed(ActionEvent evt){
            
            if(evt.getSource() == btnInit){
                int numThreads = 0;
                try{
                    numThreads = Integer.parseInt(
                            spNumThreads.getValue().toString());
                    if(numThreads < 1){
                        JOptionPane.showMessageDialog(null,
                                "Número de Processos deve ser maior que zero!");
                    }else{
                        STOP_THREADS = false;
                        pnlGetTicket.removeAll();
                        mutex = new BakeryAlgorithm2(numThreads);
                        rbProcesses = new JRadioButton[numThreads];
                        processes = new Worker[numThreads];
                        for(int j=0; j < numThreads; j++){
                            rbProcesses[j] = new JRadioButton();
                            rbProcesses[j].setName("P"+String.valueOf(j));
                            rbProcesses[j].setText(rbProcesses[j].getName());
                            processes[j] = new Worker(rbProcesses[j].getName(),
                                    j, mutex);
                            processes[j].setUpdater(bw);
                            pnlGetTicket.add(rbProcesses[j]);
                            pnlGetTicket.updateUI();
                        }
                        btnInit.setEnabled(false);
                        btnStop.setEnabled(true);
                        for(int j=0; j < numThreads; j++){
                            processes[j].start();
                        }
                    }
                } catch(NumberFormatException nfe){JOptionPane.
                        showMessageDialog(null, "Número de Processos inválidos!");
                } catch(Exception e){ e.printStackTrace();}
            }
            
            else{
                STOP_THREADS = true;    
                JOptionPane.showMessageDialog(
                        null, "Espere até que todas as threads tenham terminado!");
                btnStop.setEnabled(false);
                btnInit.setEnabled(true);
                System.gc();                                
            }
            
        }
    }
    
    private class SliderListener implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            MutualExclusion.changeEnteringTime( 
                    (int)((MIN_VELOCITY*MAX_VELOCITY)/sldVelocity.getValue()));
            MutualExclusion.changeInCSTime(
                    (int)((MIN_VELOCITY*MAX_VELOCITY)/sldVelocity.getValue()));
        }
    }
    
    private class WindowListener extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            STOP_THREADS = true;
            System.gc();
            dispose();
        }
    }
    
    
    public static void main(String args[]){
        new BakeryWindow();
    }
}
