/*
 * BakeryAlgorithm.java
 *
 * Created on 8 de Outubro de 2006, 12:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.bakery;


public class BakeryAlgorithm2 extends MutualExclusion{    
    private int nThreads=0;
    private volatile boolean choosing[];
    private volatile int number[];
    
    /** Creates a new instance of BakeryAlgorithm */
    public BakeryAlgorithm2(int nThreads) {
        this.nThreads = nThreads;
        choosing = new boolean[nThreads];
        number = new int[nThreads];
        for(int i=0; i < nThreads; i++){
            choosing[i] = false;
            number[i] = 0;
        }
    }
    
    private int max(int n[]){
        int m=0;
        for(int i=0; i < nThreads; i++)
            if(m < n[i]) m = n[i];
        return m;
    }
    
    public void catchTicket(int tId){
        choosing[tId] = true;        
        number[tId] = max(number) + 1;        
        choosing[tId] = false;
    }
    
    public void enteringCriticalSection(int tId){                    
        for(int j=0; j < nThreads; j++){
            while(choosing[j]);            
            while((number[j]!=0) &&
                      (number[j] < number[tId]||
                        (number[j] == number[tId]&&
                          j < tId
                         )
                  ));
        }
    }
    
    public void leavingCriticalSection(int tId){
        number[tId] = 0;
    }
    
     /*
      * A prova do algoritmo consiste em mostrar primeiro que se tId está na CS
      * e j(j!=i) já definiu number[j] != 0, então a condição 
      * (number[tId], tid) < number[j], j) deve ser satisfeita.
      * Se tId está na CS então choosing[tId] = false e number[j] >= number[tId].
      *  Se number[j] > number[tId] 
      *     então teremos (number[tId], tID) < (number[j], j)
      *  senão se number[j] > number[tId] 
      *     então concluimos que ambos começaram a execução do protocolo de
      *           entrada em paralelo, entretanto já que tId está na sua CS
      *           então devemos ter tId < j o que mostra que a condição
      *          (number[tId], tid) < number[j], j) é satisfeita
      * A exclusão mútua é decorrência da condição do item anterior, pois se
      * tId está na CS e tj está tentando entrar, ele encontrará 
      * number[j]!=0 e (number[tId], tid) < number[j], j) e dessa forma tj
      * continuará preso no while até que tId deixe a CS.
      * A espera limitada também é satisfeita já que neste caso os processos
      * entram na CS segundo uma política FCFS.
      * 
     */
}
