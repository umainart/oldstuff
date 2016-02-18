/*
 * IReceiver.java
 *
 * Created on 18 de Novembro de 2006, 22:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters.api;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @see scd.readersAndWriters.impl.Receive
 * @since SCD2006.02 
 */
public interface IReceiver {
    public static final int START_ALL = -1;
    public static final int PAUSE_ALL = -1;
    public static final int STOP_ALL = -1;
    
    public void init(int r, int w); 
    public void start(int index, String str);
    public void pause(int index, boolean value);
    public void stop(int index);   
    public void stop();
    public void modifyVelocity(int time);
    public void modifyRestTime(int time);
    public boolean isStarted();
}
