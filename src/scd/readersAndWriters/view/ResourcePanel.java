/*
 * ResourcePanel.java
 *
 * Created on 12 de Novembro de 2006, 17:58
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
 * Esta classe é responsável pela renderização/atualização do painel que  
 * exibe o recurso compartilhado.
 * Possui uma inner-class observadora do recurso, sendo esta responsável
 * por atualizar a figura/texto que o representa ao receber um notificação
 * para isso.
 */
public class ResourcePanel extends JPanel implements Observer{    
    private String imageNames1[] = Graphics2DUtil.get1stSentenceImages();
    private String imageNames2[] = Graphics2DUtil.get2ndSentenceImages();
    private String currentImageNames[];
    private Vector<Image> images1, images2, currentImages;        
    private int index;        
    
    /** Creates a new instance of ResourcePanel */
    public ResourcePanel() {        
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createRaisedBevelBorder(), " Recurso (Banco de Dados) "));     
        images1 = new Vector();                
        images2 = new Vector();
        index = 0;        
        for(int i=0; i < imageNames1.length; i++){
            images1.add(Graphics2DUtil.getBufferedImage(
                    Graphics2DUtil.PATH_IMAGES+imageNames1[i], 
                    this));                        
        }
        
        for(int i=0; i < imageNames2.length; i++){
            images2.add(Graphics2DUtil.getBufferedImage(
                    Graphics2DUtil.PATH_IMAGES+imageNames2[i], 
                    this));                        
        }
        currentImages = images1;
        currentImageNames = imageNames1;
        repaint();               
    }

    public void update(Observable o, Object arg) {
        if(((Status)arg).getStatus().endsWith(Status.WRITING))            
            write();      
    }
    
    public void write(){
        for(int i=0; i < currentImageNames.length; i++){
            this.repaint();                        
            try{                
                Thread.currentThread().sleep(500);
            }catch(InterruptedException e){ e.printStackTrace(); }
            if (index < (currentImageNames.length-1)) index++; 
            else index = 0;            
        }
        currentImages = (currentImages == images1) ? images2 : images1;         
        currentImageNames = 
                (currentImageNames == imageNames1) ? imageNames2: imageNames1;
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;                
        g2d.drawImage(currentImages.get(index), (int)(this.getWidth()/4), 20, this);
    }       
        
}
