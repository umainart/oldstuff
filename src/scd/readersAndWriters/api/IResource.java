/*
 * IResource.java
 *
 * Created on 6 de Novembro de 2006, 00:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.readersAndWriters.api;

/**
 * @author Ulisses Mainart
 * @author Jôse Bandeira
 * @since SCD2006.02 
 */
public interface IResource{    
    public int acquireRead() throws InterruptedException;
    public int releaseRead();
    public void acquireWrite()throws InterruptedException;
    public void releaseWrite();   
    public void snooze();
    public void rest();
    public void setSnooze(int snooze);
    public void setRest(int rest);    
}
