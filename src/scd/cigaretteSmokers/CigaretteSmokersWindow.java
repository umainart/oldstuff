/*
 * CigaretteSmokersWindow.java
 *
 * Created on 25 de Novembro de 2006, 23:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.cigaretteSmokers;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import scd.utils.*;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see java.util.Observer
 * @since SCD2006.02 
 *
 * Esta classe é responsável pela renderização dos componentes gráficos e
 * recepção dos eventos, delegando à classe tratadora as ações.
 */
public class CigaretteSmokersWindow extends JFrame{
    
    private JPanel pnlSmokers;
    private JPanel pnlTable, pnlMiddle;
    private JPanel pnlVendor;    
    private JPanel pnlButtons;
    private JPanel pnlGroup;
    private JPanel pnlVelocity;
    private JButton btnStart, btnStop;
    private JLabel lblSmokeVelocity, lblProductOnTableVelocity,
                    lblVendorDraftVelocity;    
    private JSlider sldSmokeVelocity, sldProductOnTableVelocity,
                    sldVendorDraftVelocity;    
    private Picture picSmokers[], picProducts[];
    private Picture picVendor;
    private Vector<Image> imgSmokers, imgVendors, imgProducts;
    private CSListener cslist;
    
    private final int MAX_VELOCITY = 10000;
    private final int MIN_VELOCITY = 5;
                                
    public CigaretteSmokersWindow(Smoker[] s, Vendor v, Product[] p, CSListener csl) {                        
        setTitle("Problema dos Fumantes / Cigarette Smokers Problem - com Semáforo");
        this.addWindowListener(new WindowListener());
        cslist = csl;
        pnlSmokers = new JPanel();
        pnlVendor = new JPanel();
        pnlTable = new JPanel();
        pnlMiddle = new JPanel();
        pnlGroup = new JPanel();
                
        picSmokers = new Picture[s.length];        
        picProducts = new Picture[p.length];        
        
        //carrega imagens
        imgSmokers = new Vector();
        imgVendors = new Vector();     
        imgProducts = new Vector();
        String[] stemp = Graphics2DUtil.getSmokerImages();                
        for(int i=0; i < stemp.length; i++){
            imgSmokers.add(Graphics2DUtil.getBufferedImage(
                    Graphics2DUtil.PATH_IMAGES+stemp[i], pnlSmokers));
        }
        stemp = Graphics2DUtil.getVendorImages();
        for(int i=0; i < stemp.length; i++){
            imgVendors.add(Graphics2DUtil.getBufferedImage(
                    Graphics2DUtil.PATH_IMAGES+stemp[i], pnlVendor));
        }
        stemp = Graphics2DUtil.getProductImages();
        for(int i=0; i < stemp.length; i++){
            imgProducts.add(Graphics2DUtil.getBufferedImage(
                    Graphics2DUtil.PATH_IMAGES+stemp[i], pnlTable));
        }  
                
        pnlGroup.setLayout(new GridLayout(3, 1, 10, 10));
        pnlGroup.add(createSmokersPanel(s));
        pnlGroup.add(createTablePanel());
        pnlGroup.add(createVendorPanel(v));
        
        setLayout(new BorderLayout());
        add(pnlGroup, BorderLayout.CENTER);
        add(createCommandButtons(), BorderLayout.SOUTH);
        //setLocationRelativeTo(null);                      
    }
    
    private JPanel createSmokersPanel(Smoker[] s){                        
        pnlSmokers.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLoweredBevelBorder(), " Fumantes "));                        
        for(int i=0 ; i < picSmokers.length; i++){
            picSmokers[i] = new Picture(
                    imgSmokers.get(0),s[i].getProductDescription(), "Quero fumar!");
            s[i].addObserver(picSmokers[i]);
            pnlSmokers.add(picSmokers[i]);
        }         
        return pnlSmokers;
    }
    
    private JPanel createVendorPanel(Vendor v){        
        JPanel pnls[];
        //pnlVendor.setLayout(new BorderLayout());
        pnlVendor.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLoweredBevelBorder(), " Vendedor "));                        
        picVendor = new Picture(imgVendors.get(0), null, "");
        v.addObserver(picVendor);
        pnlVendor.add(picVendor);//, BorderLayout.CENTER);
                
        pnlVelocity = new JPanel(new GridLayout(3, 1));
        pnlVelocity.setBorder(BorderFactory.createTitledBorder(" Velocidades "));
        lblSmokeVelocity          = new JLabel("Fumando:");
        lblProductOnTableVelocity = new JLabel("Produtos na mesa:");
        lblVendorDraftVelocity    = new JLabel("Sorteando:");    
        sldSmokeVelocity = 
                new JSlider(JSlider.HORIZONTAL, MIN_VELOCITY, 
                            MAX_VELOCITY, Parameter.getSnoozeSmoking()
                );
        sldProductOnTableVelocity = 
                new JSlider(JSlider.HORIZONTAL, MIN_VELOCITY, 
                MAX_VELOCITY, Parameter.getSnoozeWithProductOnTable()
                );
        sldVendorDraftVelocity =
                new JSlider(JSlider.HORIZONTAL, MIN_VELOCITY, 
                            MAX_VELOCITY, Parameter.getSnoozeAfterDraft()
                );
        SliderListener sl = new SliderListener();
        sldProductOnTableVelocity.addChangeListener(sl);
        sldSmokeVelocity.addChangeListener(sl);
        sldVendorDraftVelocity.addChangeListener(sl);
        sldProductOnTableVelocity.setToolTipText("<< lento      rápido >>");
        sldSmokeVelocity.setToolTipText("<< lento      rápido >>");
        sldVendorDraftVelocity.setToolTipText("<< lento      rápido >>");
        pnls = new JPanel[3];
        pnls[0] = new JPanel(); 
        pnls[1] = new JPanel(); 
        pnls[2] = new JPanel();
        pnls[0].add(lblSmokeVelocity); 
        pnls[0].add(sldSmokeVelocity);
        pnls[1].add(lblProductOnTableVelocity); 
        pnls[1].add(sldProductOnTableVelocity);
        pnls[2].add(lblVendorDraftVelocity); 
        pnls[2].add(sldVendorDraftVelocity);
        for(int i=0; i < 3; i++) pnlVelocity.add(pnls[i]);
        pnlVendor.add(pnlVelocity); //, BorderLayout.EAST);
                
        return pnlVendor;
    }
    
    private JPanel createTablePanel(){
        //pnlMiddle.setLayout(new GridLayout(1, 3, 0, 0));        
        pnlMiddle.setLayout(new BorderLayout());        
        pnlMiddle.add(new JPanel(), BorderLayout.EAST);        
        pnlTable.setBackground(new Color(150, 95, 00)); //brown
        pnlTable.setBorder(BorderFactory.createRaisedBevelBorder());                
        pnlMiddle.add(pnlTable, BorderLayout.CENTER);        
        pnlMiddle.add(new JPanel(), BorderLayout.WEST);                
        return pnlMiddle;
    }
    
    private JPanel createCommandButtons(){
        pnlButtons = new JPanel();
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        btnStop.setEnabled(false);
        ButtonListener l = new ButtonListener();
        btnStart.addActionListener(l);
        btnStop.addActionListener(l);
        pnlButtons.setBorder(BorderFactory.createRaisedBevelBorder());
        pnlButtons.add(btnStart);
        pnlButtons.add(btnStop);        
        return pnlButtons;
    }
                   
    private class Picture extends JPanel implements Observer{
        JLabel lblImg;        
        JTextField tfdText, tfdProd;
        Picture(Image img, String prod, String text){
           this.setLayout(new BorderLayout());
           this.setBorder(BorderFactory.createRaisedBevelBorder()); 
           lblImg = new JLabel(new ImageIcon(img));     
           tfdText = new JTextField(16);
           tfdProd = new JTextField(16);
           tfdText.setBorder(null);
           tfdText.setFont(new Font(null, Font.BOLD, 12));
           tfdText.setBackground(null);           
           tfdText.setText(text);
           tfdProd.setBorder(null);
           tfdProd.setFont(new Font(null, Font.BOLD, 10));
           tfdProd.setBackground(null);           
           tfdProd.setText(prod);
           this.add(tfdProd, BorderLayout.NORTH);
           this.add(lblImg, BorderLayout.CENTER);    
           this.add(tfdText, BorderLayout.SOUTH);
        }
        
        public void setImage(Image img){
            lblImg.setIcon(new ImageIcon(img));
        }
        
        public void setText(String txt){
            tfdText.setText(txt);
        }
        
        public void update(Observable o, Object arg) {
            Parameter param = (Parameter)arg;                                    
            if(o.getClass().getSimpleName().equals(
                    Vendor.class.getSimpleName())){ 
                int indImg = 0;
                if(param.getProducts() != null){  //put products                                                                        
                    indImg = 1;                    
                    Product[] p = param.getProducts();
                    Picture pic1 = new Picture(
                            imgProducts.get(p[0].getType()),null, 
                                            p[0].getDescription());
                    Picture pic2 = new Picture(
                            imgProducts.get(p[1].getType()), null, 
                                            p[1].getDescription());                    
                    pnlTable.add(pic1);
                    pnlTable.add(pic2);
                    pnlTable.updateUI();
                } 
                picVendor.setImage(imgVendors.get(indImg));                    
                picVendor.setText(param.getAction());
                pnlVendor.updateUI();
                
            }else{ //smoker                
                int indImg = 0;
                if(param.getAction().equals(Smoker.SMOKING)){
                    indImg = 1;
                    pnlTable.removeAll();
                    pnlTable.updateUI();                    
                }            
                picSmokers[param.getIndexImage()].setImage(imgSmokers.get(indImg));
                picSmokers[param.getIndexImage()].setText(param.getAction());
                pnlSmokers.updateUI();                                
            }
        }
                        
    }  
    
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            if(evt.getSource() == btnStart){
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                cslist.start();
            }else{ // stop
                cslist.stop();
                btnStop.setEnabled(false);
                btnStart.setEnabled(true);
            }
        }
    }
    
    private class SliderListener implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            if(e.getSource() == sldProductOnTableVelocity){
                Parameter.setSnoozeWithProductOnTable(
                        (int)((MIN_VELOCITY*MAX_VELOCITY)/
                        sldProductOnTableVelocity.getValue()));
            }else if(e.getSource() == sldSmokeVelocity){
                Parameter.setSnoozeSmoking(
                        (int)((MIN_VELOCITY*MAX_VELOCITY)/
                        sldSmokeVelocity.getValue()));                
            }else if(e.getSource() == sldVendorDraftVelocity){
                Parameter.setSnoozeAfterDraft(
                        (int)((MIN_VELOCITY*MAX_VELOCITY)/
                        sldVendorDraftVelocity.getValue()));                
            }
        }
    }
    
    private class WindowListener extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            cslist.stop();
            System.gc();
            dispose();
        }
    }
    
}

