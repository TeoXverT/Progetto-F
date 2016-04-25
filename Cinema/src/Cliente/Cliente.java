/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import input_output.*;
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
      int i;
      Calendar giornoAttuale;
      TimeZone timezone = TimeZone.getTimeZone("Europe/Rome");
      Proiezione proiezioneProva;
      
      listaProiezioniFuture = new ArrayList<>();
      
      giornoAttuale = Calendar.getInstance(timezone);

      for(i = 0; i < listaProiezione.size(); i++) {
          
          if(listaProiezione.get(i).getData_ora().after(giornoAttuale)) {
              
              proiezioneProva = new Proiezione(listaProiezione.get(i).getId_proiezione(), listaProiezione.get(i).getData_ora(), listaProiezione.get(i).getId_film(), listaProiezione.get(i).getId_sala(), listaProiezione.get(i).getTipo_proiezione(), listaProiezione.get(i).getPrezzo());
              
              listaProiezioniFuture.add(proiezioneProva);
              
          }
          
      }
      
      return listaProiezioniFuture;
  }
    
    
    
}
