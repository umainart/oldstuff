/*
 * WritersPanel.java
 *
 * Created on 11 de Novembro de 2006, 22:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import scd.utils.*;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see java.util.Observer
 * @see scd.utils.Graphics2DUtil
 * @since SCD2006.02 
 * 
 * Esta classe é responsável pela renderização/atualização do painel de 
 * escritores.
 * Possui uma inner-class observadora de um escritor, sendo esta responsável
 * por atualizar a figura/texto que o representa ao receber um notificação
 * para isso.
 */
public class WritersPanel extends JPanel{        
    private String[] imageNames = Graphics2DUtil.getWriterImages();
    private Vector<Image> images;    
    
    /** Creates a new instance of WritersPanel */
    public WritersPanel() {              
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        setBorder(BorderFactory.createTitledBorder(" Escritores "));
        images = new Vector();
        for(int i=0; i < imageNames.length; i++){
            images.add(Graphics2DUtil.getBufferedImage(
                    Graphics2DUtil.PATH_IMAGES+imageNames[i], this));
        }                          
    }
    
    public WriterComponent addWriter(String status){
        WriterComponent wc = new WriterComponent(status);
        this.add(wc);
        updateUI();        
        return wc;
    }
    
    public void removeAllWriters(){
        removeAll();
        updateUI();
    }
        
    public void removeWriter(int index){
        remove(index);
        updateUI();
    }
    
    private class WriterComponent extends JPanel implements Observer{
        private JTextField tfStatus;
        private JLabel lblImg;
        
        WriterComponent(String status){                                     
            tfStatus = new JTextField(10);                                                
            tfStatus.setText(status);      
            tfStatus.setFont(new Font(null, Font.BOLD, 10));
            tfStatus.setBorder(null);
            tfStatus.setBackground(null);
            lblImg = new JLabel(new ImageIcon(images.get(0)));
            setLayout(new BorderLayout());
            add(lblImg, BorderLayout.CENTER);                        
            add(tfStatus, BorderLayout.SOUTH);                                                
            updateUI();
        }

        public void update(Observable o, Object arg) {
            Status s = (Status)arg;
            setStatus(s.getStatus(), s.getStatusImage());
        }
                
        private void setStatus(String status, int img){
            tfStatus.setText(status);            
            lblImg.setIcon(new ImageIcon(images.get(img)));
            updateUI();            
        }                
    }
}
