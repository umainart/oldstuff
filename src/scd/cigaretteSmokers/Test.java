/*
 * Test.java
 *
 * Created on 20 de Novembro de 2006, 02:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.cigaretteSmokers;

/**
 *
 * @author user
 */
public class Test {
    
    /** Creates a new instance of Test */
    public Test() {
        Table table = new Table();
        Vendor v = new Vendor(table);        
        Smoker s1 = new Smoker(1, table, new Product(Product.MATCHES));
        Smoker s2 = new Smoker(2, table, new Product(Product.PAPER));
        Smoker s3 = new Smoker(3, table, new Product(Product.TOBACCO));        
        
        new Thread(v).start();
        new Thread(s1).start();
        new Thread(s2).start();
        new Thread(s3).start();
    }
    
    public static void main(String...args){
        new Test();
    }
}
