/*
 * Splash.java
 *
 * Created on 19 de Novembro de 2006, 19:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.utils;

import java.awt.*;
import javax.swing.*;

public class Splash extends Window implements Runnable{        
    private final String message = "Aguarde enquanto as threads são finalizadas.";
    private final String imageName = "wait.jpg";
    private Image splashImage;
    private Long millis;
    private JFrame frame;
        
    /** Creates a new instance of Splash */
    public Splash() {
        super(new Frame());
        setVisible(true);
        splashImage = null;        
    }
            
    private void initSplash() {                        
        splashImage = Graphics2DUtil.
                getBufferedImage(Graphics2DUtil.PATH_IMAGES+imageName, this);                        
        setSize(splashImage.getWidth(this), splashImage.getHeight(this));        
        setLocationRelativeTo(null);
        setVisible(true);		
    }
    
    public void openSplash(Long millis, JFrame frame){
        Thread t = new Thread(this);
        this.millis = millis;
        this.frame = frame;
        t.start();         
    }
    
    public void run(){
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        initSplash();
        frame.setEnabled(false);
        try{
            Thread.sleep(millis);
        }catch(InterruptedException e){}        
        frame.setEnabled(true);
        finish();
    }
	
    public void finish(){
        setVisible(false);
        dispose();
    }

    public void paint(Graphics g){        
        g.drawImage(splashImage, 0, 0, getBackground(), this);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString(message, (int)(splashImage.getWidth(this) / 8), 30);        
    }
}
