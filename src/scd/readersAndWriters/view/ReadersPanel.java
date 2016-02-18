/*
 * ReadersPanel.java
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
 * leitores.
 * Possui uma inner-class observadora de um leitor, sendo esta responsável
 * por atualizar a figura/texto que o representa ao receber um notificação
 * para isso.
 */
public class ReadersPanel extends JPanel{
    private String[] imageNames = Graphics2DUtil.getReaderImages();
    private Vector<Image> images;    
    
    /** Creates a new instance of ReadersPanel */
    public ReadersPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        setBorder(BorderFactory.createTitledBorder(" Leitores "));
        images = new Vector();        
        for(int i=0; i < imageNames.length; i++){
            images.add(Graphics2DUtil.getBufferedImage(
                    Graphics2DUtil.PATH_IMAGES+imageNames[i], 
                    this));
        }        
    }
    
    public ReaderComponent addReader(String status){        
        ReaderComponent r = new ReaderComponent(status);
        add(r);        
        updateUI();        
        return r;
    }
    
    public void removeAllReaders(){
        removeAll();
        updateUI();        
    }
    
    public void removeReader(int index){
        remove(index);     
        updateUI();
    }
            
    private class ReaderComponent extends JPanel implements Observer{        
        private JTextField tfStatus;
        private JLabel lblImg;
        
        ReaderComponent(String status){            
            tfStatus = new JTextField(10);            
            tfStatus.setBorder(null);
            tfStatus.setFont(new Font(null, Font.BOLD, 10));
            tfStatus.setBackground(null);
            tfStatus.setText(status);            
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
                
        public void setStatus(String status, int img){
            tfStatus.setText(status);
            lblImg.setIcon(new ImageIcon(images.get(img)));
            updateUI();            
        }                
    }
    
}
