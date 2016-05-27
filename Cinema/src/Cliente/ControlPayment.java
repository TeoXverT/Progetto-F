/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.util.TimerTask;

/**
 *
 * @author Riccardo
 */
public class ControlPayment extends TimerTask {
    
   
   public int cp1;
   
   public ControlPayment(int cp1){
       this.cp1=cp1;
    
       }

    public void run(){ //query controlla pagamento restituisce cp1=1 pagato cp1=0 non pagato 
        
    }
}
   
   

