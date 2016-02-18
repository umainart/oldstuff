/*
 * MainApp.java
 *
 * Created on 17 de Outubro de 2006, 03:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd;


import javax.swing.*;
import java.awt.event.*;

public class MainApp extends JFrame{
    JMenuItem mBak, mPCM, mPCS, mSB, mDP;
    JMenu mRW;
    JMenuItem mRHPSema, mWHPSema, mRHPMonit, mWHPMonit;
    JMenuItem mCS;
    JMenu mnuWork1, mnuWork2;
    JMenuBar jmb;
    
    /** Creates a new instance of MainApp */
    public MainApp() {
        jmb = new JMenuBar();
        MenuHandler mh = new MenuHandler();
        
        //menu referente ao primeiro trabalho
        mnuWork1 = new JMenu("Trabalho 1");                        
        mBak = new JMenuItem("Bakery");        
        mBak.addActionListener(mh);
        mPCM = new JMenuItem("Produtor x Consumidor - com Monitor");
        mPCM.addActionListener(mh);
        mPCS = new JMenuItem("Produtor x Consumidor - com Semáforos");
        mPCS.addActionListener(mh);                       
        mnuWork1.add(mBak);
        mnuWork1.add(mPCM);
        mnuWork1.add(mPCS);        
        
        //menu referente ao segundo trabalho
        mnuWork2 = new JMenu(" Trabalho 2");
        mRW = new JMenu("Leitores e Escritores");
        mRHPSema = new JMenuItem("Leitores têm prioridade - com semáforo");        
        mRHPMonit = new JMenuItem("Leitores têm prioridade - com monitor");
        mWHPSema = new JMenuItem("Escritores têm prioridade - com semáforo");
        mWHPMonit = new JMenuItem("Escritores têm prioridade - com monitor");        
        mDP = new JMenuItem("Jantar dos Filósofos");
        mCS = new JMenuItem("Problema dos Fumantes");
        mSB = new JMenuItem("Barbeiro Dorminhoco");
        mSB.addActionListener(mh);
        mDP.addActionListener(mh);
        mRHPSema.addActionListener(mh);
        mRHPMonit.addActionListener(mh);
        mWHPSema.addActionListener(mh);
        mWHPMonit.addActionListener(mh);
        mCS.addActionListener(mh);
        mRW.add(mRHPSema);
        mRW.add(mRHPMonit);
        mRW.add(mWHPSema);
        mRW.add(mWHPMonit);        
        
        mnuWork2.add(mSB);
        mnuWork2.add(mDP);
        mnuWork2.add(mRW);                
        mnuWork2.add(mCS);                
        
        jmb.add(mnuWork1);
        jmb.add(mnuWork2);
        setJMenuBar(jmb);
        setTitle("Sistemas Concorrentes e Distribuídos");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private class MenuHandler implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == mBak)
                new scd.bakery.BakeryWindow();
            else if (evt.getSource() == mPCM)
                new scd.producerConsumerMonitor.ProducerConsumerWindow();
            else if (evt.getSource() == mPCS)
                new scd.producerConsumerSemaphore.ProducerConsumerWindow();            
            else if (evt.getSource() == mRHPSema)
                new scd.readersAndWriters.impl.Receiver(
                new scd.readersAndWriters.impl.semaphore.ReadersHavePriority());
            else if (evt.getSource() == mRHPMonit)
                new scd.readersAndWriters.impl.Receiver(
                new scd.readersAndWriters.impl.monitor.ReadersHavePriority());
            else if (evt.getSource() == mWHPSema)
                new scd.readersAndWriters.impl.Receiver(
                new scd.readersAndWriters.impl.semaphore.WritersHavePriority());
            else if (evt.getSource() == mWHPMonit)
                new scd.readersAndWriters.impl.Receiver(
                new scd.readersAndWriters.impl.monitor.WritersHavePriority());
            else if (evt.getSource() == mSB)
                new scd.sleepingBarber.BarberShop().init();
            else if (evt.getSource() == mDP)
                new scd.dinnerPhilosopher.DinnerWindow();
            else if (evt.getSource() == mCS)
                new scd.cigaretteSmokers.CSListener();
        }
    }
                
    public static void main(String args[]){
        new MainApp();
    }
}
