package scd.utils;

import java.awt.*;
import java.awt.image.*;
import java.awt.print.*;
import java.awt.geom.*;
import javax.swing.*;
import java.net.URL;

public class Graphics2DUtil {
  public static final String PATH_IMAGES = ".//images//"; 
  
  //obtem imagem do arquivo retornando um BufferedImage
  public static BufferedImage getBufferedImage(String fileName, Component c){
      Image img = c.getToolkit().getImage(fileName);
      if(!waitForImage(img, c)) System.out.println("Erro ao carregar imagem "+fileName);
      BufferedImage buffImg = new BufferedImage(
                                    img.getWidth(c), 
                                    img.getHeight(c),
                                    BufferedImage.TYPE_INT_RGB
                                 );
      Graphics2D g2d = buffImg.createGraphics();
      g2d.drawImage(img, 0, 0, c);
      return buffImg;
  }

  //espera que uma imagem seja carregada
  public static boolean waitForImage(Image image, Component c){
    MediaTracker tracker = new MediaTracker(c);
    tracker.addImage(image, 0);
    try {
      tracker.waitForAll();
    }
    catch (InterruptedException e) {}
    return (!tracker.isErrorAny());
  }

  //obtem um recurso do sistema   
  public static URL getResource(String name) {    
    ClassLoader classLoader = Graphics2DUtil.class.getClassLoader();
    return classLoader.getResource(name);
  }
  
  //cria um icone com o nome associado
  public static ImageIcon createImageIcon(String imageName) {
    ClassLoader classLoader = Graphics2DUtil.class.getClassLoader();    
    return new ImageIcon(Graphics2DUtil.getResource(imageName));
  }
  
  public static void centralize(JFrame frm){      
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frame  = frm.getSize();
      frm.setLocation( (screen.width  - frame.width)/2, 
                       (screen.height - frame.height)/2);
  }
  
  public static String[] get1stSentenceImages(){
      String[] imgs = new String[7];
      for(int i=0; i < 7; i++){
          imgs[i] = "s1"+String.valueOf(i) + ".jpg";
      }
      return imgs;
  }
  
  public static String[] get2ndSentenceImages(){
      String[] imgs = new String[5];
      for(int i=0; i < 5; i++){
          imgs[i] = "s2"+String.valueOf(i) + ".jpg";
      }
      return imgs;
  }
  
  public static String[] getReaderImages(){
      String[] imgs = new String[3];
      for(int i=0; i < 3; i++){
          imgs[i] = "reader"+String.valueOf(i) + ".jpg";
      }
      return imgs;
  }    
  
  public static String[] getWriterImages(){
      String[] imgs = new String[3];
      for(int i=0; i < 3; i++){
          imgs[i] = "quill"+String.valueOf(i) + ".jpg";
      }
      return imgs;
  }
  
  public static String[] getSmokerImages(){
      String[] imgs = new String[2];
      for(int i=0; i < 2; i++){
          imgs[i] = "smoker"+String.valueOf(i) + ".jpg";
      }
      return imgs;
  }    
  
  public static String[] getProductImages(){
      String[] imgs = new String[3];
      for(int i=0; i < 3; i++){
          imgs[i] = "product"+String.valueOf(i) + ".jpg";
      }
      return imgs;
  }
  
  public static String[] getVendorImages(){
      String[] imgs = new String[2];
      for(int i=0; i < 2; i++){
          imgs[i] = "vendor"+String.valueOf(i) + ".jpg";
      }
      return imgs;
  }
}
