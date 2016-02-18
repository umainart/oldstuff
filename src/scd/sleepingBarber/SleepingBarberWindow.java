/*
 * SleepingBarberWindow.java
 *
 * Created on 3 de Dezembro de 2006, 02:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.sleepingBarber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Jôse Bandeira
 * @author Ulisses Mainart 
 * @see scd.sleepingBarber.BarberShop
 * @see scd.sleepingBarber.Barber
 * @see scd.sleepingBarber.Customer
 * @since SCD2006.02 
 */
public class SleepingBarberWindow extends JFrame{
    
    private JPanel pnlBarbersShop;
    private JPanel pnlChairs;
    private JPanel pnlBarber;
    private JPanel pnlCustomer;
    private JPanel pnlInfo;
    private JPanel pnlControls;
    private JPanel pnlUp;
    
    private JButton btnStartStop, btnPauseResume;
    
    private JSpinner spnBarbers, spnChairs;
    private JLabel lblBarbers, lblChairs;
    private JLabel lblWaiting1, lblWaiting2;
    private JLabel lblCustNotService1, lblCustNotService2;
    
    private Picture[] picChairs, picBarbers, picCustomers;
    private int nBarbers, nChairs;
    private BarberShop barberShop;
    
    /** Creates a new instance of SleepingBarberWindow */
    public SleepingBarberWindow(BarberShop bs) {
        barberShop = bs;
        nBarbers = BarberShop.nBarbers;
        nChairs = BarberShop.nChairs;  
        
        this.addWindowListener(new WindowHandler(this));
        initComponents();
        
    }
    
    /* Realiza a renderização dos componentes */
    private void initComponents(){
        pnlBarbersShop = new JPanel();
        pnlChairs = new JPanel();
        pnlBarber = new JPanel();
        pnlCustomer = new JPanel();
        pnlInfo = new JPanel();
        pnlControls = new JPanel();
        pnlUp = new JPanel();        
        
        pnlBarbersShop.setLayout(new GridLayout(3, 1, 2, 2));
        pnlBarbersShop.add(pnlChairs);
        pnlBarbersShop.add(pnlBarber);
        pnlBarbersShop.add(pnlCustomer);
                                        
        pnlChairs.setBorder(BorderFactory.createTitledBorder("Espera"));
        pnlBarber.setBorder(BorderFactory.createTitledBorder("Barbeiros"));
        pnlCustomer.setBorder(BorderFactory.createTitledBorder("Clientes"));        
        pnlControls.setBorder(BorderFactory.createRaisedBevelBorder());
                              
        btnStartStop = new JButton("Start");
        btnPauseResume = new JButton("Pause");
        lblBarbers = new JLabel("Barbeiros: ");
        lblChairs = new JLabel("Cadeiras: ");
        lblWaiting1 = new JLabel("Clientes esperando: ");
        lblWaiting2 = new JLabel("0");
        lblCustNotService1 = new JLabel("Clientes não atendidos: ");
        lblCustNotService2 = new JLabel("0");        
        spnBarbers = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        spnChairs = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));        
        btnPauseResume.setEnabled(false);
        pnlControls.add(lblBarbers);        
        pnlControls.add(spnBarbers);
        pnlControls.add(lblChairs);
        pnlControls.add(spnChairs);
        pnlControls.add(btnStartStop);
        pnlControls.add(btnPauseResume);
        pnlControls.add(lblWaiting1);
        pnlControls.add(lblWaiting2);
        pnlControls.add(lblCustNotService1);
        pnlControls.add(lblCustNotService2);
        ButtonHandler bh = new ButtonHandler();
        btnStartStop.addActionListener(bh);
        btnPauseResume.addActionListener(bh);        
        
        this.setLayout(new BorderLayout());        
        this.add(pnlBarbersShop, BorderLayout.CENTER);
        this.add(pnlControls, BorderLayout.SOUTH);
    }
    
    /* remove os componentes para nova renderizacao */
    private void removeComponents(){
        pnlChairs.removeAll();
        pnlChairs.updateUI();
        pnlBarber.removeAll();
        pnlBarber.updateUI();
        pnlCustomer.removeAll();
        pnlCustomer.updateUI();               
    }
    
    /* inicializa componentes 'dinamicos' como Barbeiros, Clientes e Cadeiras */
    private void init(){        
        removeComponents();
        picBarbers = new Picture[nBarbers];
        picCustomers = new Picture[nBarbers];
        for(int i=0; i < nBarbers; i++){
            picBarbers[i] = new Picture("./images/barberWithoutClient.jpg", 
                    "Barbeiro dormindo");
            picCustomers[i] = new Picture("./images/chair.jpg", 
                    "Sem cliente");
            pnlBarber.add(picBarbers[i]);
            pnlCustomer.add(picCustomers[i]);
        }
        
        //pnl nChairs
        picChairs = new Picture[nChairs];
        for(int i=0; i < nChairs; i++){
            picChairs[i] = new Picture("./images/chairFree.jpg", 
                    "Cadeira Desocupada");
            pnlChairs.add(picChairs[i]);
        }          
    }
                       
    //-- operacoes para atualizacao dos atributos da classe BarberShop
    private void setNBarbers(){
        barberShop.nBarbers = Integer.parseInt(spnBarbers.getValue()
                                                                .toString());
        nBarbers = barberShop.nBarbers;        
    }
    
    private void setNChairs(){
        barberShop.nChairs =  Integer.parseInt(spnChairs.getValue()
                                                                .toString());
        nChairs = barberShop.nChairs;
    }
    
    // -- operacoes para atualizacao do estado de componentes no frame
    public void setCustomerNotService(int i){
        lblCustNotService2.setText(String.valueOf(i));
        lblCustNotService2.updateUI();
    }
    
    public void setWaitingCustomers(int i){
        lblWaiting2.setText(String.valueOf(i));
        lblWaiting2.updateUI();
    }
    
    public void busyChair(String msg){
        for(int i=0; i < picChairs.length; i++){
            if(picChairs[i].getStatus().
                    equalsIgnoreCase("Cadeira Desocupada")){
                picChairs[i].setPicture(
                        "./images/chairBusy.jpg", 
                        msg);
                break;
            }
        }
    }
    
    public void freeChair(String id){
        for(int i=0; i < picChairs.length; i++){
            if(picChairs[i].getStatus().contains(id)){
                picChairs[i].setPicture(
                        "./images/chairFree.jpg", 
                        "Cadeira Desocupada");
            }
        }
    }
    
    public void busyBarber(String msg){
        for(int i=0; i < picBarbers.length; i++){
            if(picBarbers[i].getStatus().
                    equalsIgnoreCase("Barbeiro dormindo")){
                picBarbers[i].setPicture(
                        "./images/barberWithClient.jpg", 
                        msg);
                break;
            }
        }
    }
    
    public void freeBarber(String id){
        for(int i=0; i < picBarbers.length; i++){
            if(picBarbers[i].getStatus().contains(id)){
                picBarbers[i].setPicture(
                        "./images/barberWithoutClient.jpg", 
                        "Barbeiro dormindo");
            }
        }
    }
    
    public void busyCustomer(String msg){
        for(int i=0; i < picCustomers.length; i++){
            if(picCustomers[i].getStatus().
                    equalsIgnoreCase("Sem cliente")){
                picCustomers[i].setPicture(
                        "./images/customer.jpg", 
                        msg);
                break;
            }
        }
    }
    
    public void freeCustomer(String id){
        for(int i=0; i < picCustomers.length; i++){
            if(picCustomers[i].getStatus().contains(id)){
                picCustomers[i].setPicture(
                        "./images/chair.jpg", 
                        "Sem cliente");
            }
        }
    }
    
    /* inner class que abstrai a figura utilizada para representar
     * Babeiros, Clientes e Cadeiras.
     */
    private class Picture extends JPanel{
        private JLabel lblImg;
        private JTextField tfStatus;
        
        Picture(String path, String text){
            this.setLayout(new BorderLayout());
            lblImg = new JLabel(new ImageIcon(path));
            this.setBorder(BorderFactory.createRaisedBevelBorder()); 
            tfStatus = new JTextField(14);
            tfStatus.setBorder(null);
            tfStatus.setFont(new Font(null, Font.BOLD, 10));
            tfStatus.setBackground(null);           
            tfStatus.setText(text);
            tfStatus.setEditable(false);
            this.add(lblImg, BorderLayout.CENTER);
            this.add(tfStatus, BorderLayout.SOUTH);            
        }
        
        public void setPicture(String path, String txt){
            lblImg.setIcon(new ImageIcon(path));
            tfStatus.setText(txt);
            this.updateUI();
        }  
        
        public String getStatus(){
            return tfStatus.getText();
        }
    } 
    
    //-- tratamento de eventos    
    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            if(evt.getSource() == btnStartStop){ 
                if(btnStartStop.getText().equals("Start")){ 
                    barberShop.stop(false);                    
                    setNBarbers();
                    setNChairs();
                    init();                    
                    btnPauseResume.setEnabled(true);
                    btnStartStop.setText("Stop");
                    spnBarbers.setEnabled(false);
                    spnChairs.setEnabled(false);
                    if(!barberShop.isStarted()) barberShop.start();                    
                }else{
                    barberShop.stop(true);                    
                    btnStartStop.setText("Start");
                    btnPauseResume.setEnabled(false);
                    spnBarbers.setEnabled(true);
                    spnChairs.setEnabled(true);
                }
                
            }else if(evt.getSource() == btnPauseResume){                
                if(btnPauseResume.getText().equals("Pause")){
                    BarberShop.pause = true;
                    btnStartStop.setEnabled(false);                
                    btnPauseResume.setText("Resume");
                }else{
                    BarberShop.pause = false;
                    btnStartStop.setEnabled(true);                
                    btnPauseResume.setText("Pause");
                }
            }
        }
    }
    
    private class WindowHandler extends WindowAdapter{
        SleepingBarberWindow sbw;
        public WindowHandler(SleepingBarberWindow sbw){
            this.sbw = sbw;
        }
        
        public void windowClosing(WindowEvent e){
            BarberShop.windowClosing = true;
            System.gc();
            sbw.dispose();
        }
    }
}
