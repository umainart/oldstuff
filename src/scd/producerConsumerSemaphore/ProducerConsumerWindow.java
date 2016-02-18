/*
 * ProducerConsumerWindow.java
 *
 * Created on 14 de Outubro de 2006, 20:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.producerConsumerSemaphore;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ProducerConsumerWindow extends JFrame{
    public static volatile boolean STOP_THREADS = false;
    
    private JPanel pnlProducers;
    private JPanel pnlConsumers;
    private JPanel pnlBuffers;
    
    private JPanel pnlDemo;
    private JPanel pnlComandos;
    private JButton btnInit;    
    private JButton btnStop;
        
    private JLabel[] jlblStatus;
    
    private JTextField tfc, tfp, tfb, tftam;
    private JLabel lblc, lblp, lblb, lbltam;
    
    private Vector<Producer> producers;    
    private Vector<Consumer> consumers;
    private Vector<Buffer> jpbBuffers;
    
    private int numBuffers;
    private int numProducers;
    private int numConsumers;
    
    
    /** Creates a new instance of ProducerConsumerWindow */
    public ProducerConsumerWindow() {   
        setTitle("Produtor x Consumidor - com Semáforos");
        this.addWindowListener(new WindowListener());
        initAtributes();        
        initComponents();                
    }
        
    private void initAtributes(){
        //valores iniciais
        numBuffers = 2;
        numProducers = 2;
        numConsumers = 2;
    }
    
    private void initComponents(){                       
        pnlDemo = new JPanel(new GridLayout(1, 3));                                
                        
        setLayout(new BorderLayout());
        
        pnlDemo.add(createPanelProducers());
        pnlDemo.add(createPanelBuffers());
        pnlDemo.add(createPanelConsumers());
        add(pnlDemo, BorderLayout.CENTER);
        add(createComandos(), BorderLayout.SOUTH);
        
        setSize(850, 500);
        setLocationRelativeTo(null);
        setVisible(true); 
        
    }
    
    private void initProducersAndConsumers(){
        producers = new Vector<Producer>();
        consumers = new Vector<Consumer>();

        for(int i=0; i < numProducers; i++){            
            producers.add(new Producer("Produtor "+String.valueOf(i), jpbBuffers));                        
        }
        
        for(int i=0; i < numConsumers; i++){            
            consumers.add(new Consumer("Consumidor "+String.valueOf(i), jpbBuffers));
        }
        
        for(int i=0; i < numProducers; i++){            
            producers.get(i).start();
        }
        
        for(int i=0; i < numConsumers; i++){            
            consumers.get(i).start();
        }
        
    }
 
    private JPanel createPanelBuffers(){               
        pnlBuffers = new JPanel();        
        pnlBuffers.setBorder(BorderFactory.createTitledBorder(" Buffers "));       
        return pnlBuffers;
    }
    
    private JPanel createPanelProducers(){                                
        pnlProducers = new JPanel();        
        pnlProducers.setBorder(BorderFactory.createTitledBorder(" Produtores "));                               
        return pnlProducers;       
    }
    
    private JPanel createPanelConsumers(){                                        
        pnlConsumers = new JPanel();        
        pnlConsumers.setBorder(BorderFactory.createTitledBorder(" Consumidores "));                                                                                                                                                        
        return pnlConsumers;
    }
    
    private void addProducerPicture(){
        Icon img = new ImageIcon("images/hamburguer.jpg") ;                
        pnlProducers.add(new JPanel().add(new JLabel(img)));                
        pnlProducers.updateUI();
    }
    
    private void addConsumerPicture(){
        ImageIcon img = new ImageIcon("images/homer.jpg");                                
        pnlConsumers.add(new JPanel().add(new JLabel(img)));        
        pnlConsumers.updateUI();        
    }        
    
    private void addBuffer(int num){        
        jpbBuffers.add(new Buffer("Buffer no. "+String.valueOf(num)));                        
        pnlBuffers.add(jpbBuffers.get(num));               
        pnlBuffers.updateUI();
    }
        
    private JPanel createComandos(){
        JPanel pnl1, pnl2;
        
        pnlComandos = new JPanel(new BorderLayout());
        
        btnInit = new JButton("Iniciar");               
        btnStop = new JButton("Parar");
        btnStop.setEnabled(false);
        
        Handler h = new Handler();
        btnInit.addActionListener(h);        
        btnStop.addActionListener(h);
        
        lblp = new JLabel("Produtores");
        lblc = new JLabel("Consumidores");
        lblb = new JLabel("Buffers");
        lbltam = new JLabel("Tamanho do Buffer");
        tfp = new JTextField(10);
        tfc = new JTextField(10);
        tfb = new JTextField(10); 
        tftam = new JTextField(10);
        tfb.setText(String.valueOf(numBuffers));
        tfp.setText(String.valueOf(numProducers));
        tfc.setText(String.valueOf(numConsumers));
        tftam.setText(String.valueOf(Buffer.BUFFER_SIZE));
        
        tfp.setName("Produtores");
        tfb.setName("Buffers");
        tfc.setName("Consumidores");
        tftam.setName("Tamanho do Buffer");
        
        pnl1 = new JPanel();
        pnl2 = new JPanel();
        
        pnl1.add(lblb);
        pnl1.add(tfb);
        pnl1.add(lblp);
        pnl1.add(tfp);
        pnl1.add(lblc);
        pnl1.add(tfc);
        pnl1.add(lbltam);
        pnl1.add(tftam);
        
        pnl2.add(btnInit);        
        pnl2.add(btnStop);
        
        pnlComandos.add(pnl1, BorderLayout.CENTER);
        pnlComandos.add(pnl2, BorderLayout.SOUTH);
        
        return pnlComandos;
    }
    
    private class Handler implements ActionListener{
        public void actionPerformed(ActionEvent evt){
           if(evt.getSource() == btnInit) init();           
           else if(evt.getSource() == btnStop) stopAll();
        }
    }
            
    private void init(){
        int np, nb, nc, ntam;
        
        if((nb=validateTextField(tfb)) > 0)
            if((np = validateTextField(tfp)) >=0)
                if((nc = validateTextField(tfc)) >=0)
                    if((ntam = validateTextField(tftam)) >= 0){
                        STOP_THREADS = false;
                        numBuffers = nb;
                        numProducers = np;
                        numConsumers = nc;
                        Buffer.BUFFER_SIZE = ntam;
                        
                        jpbBuffers = new Vector<Buffer>(); 
                        for(int i=0; i < numBuffers; i++){
                           addBuffer(i);
                        }

                        for(int i=0; i < numProducers; i++){           
                           addProducerPicture();
                        } 

                        for(int i=0; i < numConsumers; i++){           
                           addConsumerPicture();
                        }    
                                  
                        btnInit.setEnabled(false);
                        btnStop.setEnabled(true);
                        pnlDemo.updateUI();                           
                        initProducersAndConsumers();                    
                    }                                                                                            
    }
    
    private int validateTextField(JTextField tf){                
        int n = -1;
        try{
            n = Integer.parseInt(tf.getText());  
            if(n < 0){
                JOptionPane.showMessageDialog(null, "Número negativo no campo "+tf.getName());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Número inválido no campo "+tf.getName());
        }
        
        return n;
    }
    
    private void stopAll(){
        STOP_THREADS = true;
        pnlBuffers.removeAll();
        pnlBuffers.updateUI();
        pnlConsumers.removeAll();
        pnlConsumers.updateUI();
        pnlProducers.removeAll();
        pnlProducers.updateUI();
        btnInit.setEnabled(true);
        btnStop.setEnabled(false);
        System.gc();        
    }
    
    private class WindowListener extends WindowAdapter{
        public void windowClosing(WindowEvent e){            
            stopAll();         
            dispose();
        }
    }
                
    public static void main(String args[]){
        new ProducerConsumerWindow();
    }
    
}

