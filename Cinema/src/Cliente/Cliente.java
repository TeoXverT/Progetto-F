/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import input_output.Output;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import oggetti.Proiezione;

/**
 *
 * @author Yoga
 */
public class Cliente {
    ArrayList<Proiezione> listaProiezioniFuture;
    
    public Cliente() {
        
        
        
    }
    
    
  public ArrayList<Proiezione> listaProiezioniFuture(ArrayList<Proiezione>  listaProiezione) {
      int i, d=0;
      Calendar giornoAttuale;
      TimeZone timezone = TimeZone.getTimeZone("Europe/Rome");
      
      giornoAttuale = Calendar.getInstance(timezone);

      for(i = 0; i < listaProiezione.size(); i++) {
          
          if(listaProiezione.get(i).getData_ora().after(giornoAttuale)) {
              
              listaProiezioniFuture.add(listaProiezione.get(i));
              
          }
          
      }
      
      return listaProiezioniFuture;
  }
    
    
    
}
