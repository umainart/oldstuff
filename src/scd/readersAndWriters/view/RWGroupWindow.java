/*
 * RWGroupWindow.java
 *
 * Created on 11 de Novembro de 2006, 22:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import scd.readersAndWriters.api.*;

import scd.utils.Graphics2DUtil;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see java.util.Observer
 * @see scd.readersAndWriters.api.IReceiver
 * @since SCD2006.02 
 *
 * Esta classe agrupa os componentes visuais do pacote view.
 */
public class RWGroupWindow extends JFrame{    
    CommandPanel pnlCommand;
    ResourcePanel pnlResource;
    ReadersPanel pnlReaders;
    WritersPanel pnlWriters;
    JPanel pnlGrp1, pnlGrp2;
    JScrollPane sp;
    
    private IReceiver receiver;
    
    /** Creates a new instance of RWGroupWindow */
    public RWGroupWindow(IReceiver r, String tittle) {
        setTitle(tittle);
        setLayout(new BorderLayout());        
        receiver = r;
        this.addWindowListener(new WindowListener());
        pnlGrp1 = new JPanel(new GridLayout(2, 1, 1, 1));
        pnlGrp2 = new JPanel(new GridLayout(1, 2, 1, 1));
        
        pnlReaders = new ReadersPanel();
        pnlGrp2.add(pnlReaders);
        
        pnlWriters = new WritersPanel();        
        pnlGrp2.add(pnlWriters);
                        
        pnlResource = new ResourcePanel();
        pnlGrp1.add(pnlResource);
        pnlGrp1.add(pnlGrp2);        
        
        pnlCommand = new CommandPanel(r);        
        add(pnlGrp1, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);   
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
        
    public ReadersPanel getReaderPanel(){ return pnlReaders; }    
    public WritersPanel getWriterPanel(){ return pnlWriters; }   
    public ResourcePanel getResourcePanel(){ return pnlResource; }    
    public CommandPanel getCommandPanel(){ return pnlCommand; }
    
    private class WindowListener extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            if(receiver.isStarted()){
                receiver.stop();
            }
            System.gc();
            dispose();
        }
    }
}
