package oggetti;

import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cri
 */
public class Prenotazione {
  
    private int id_sala, posti_x, posti_y;    
    private int posti_vip[][];
    private Calendar data_ora;

    
    
    public Prenotazione(int id_prenotazione, int id_proiezione, int[][] posti_prenotati,  Calendar data_ora,  nuovaProiezione) {
        
    }
    
    public double calcoloCosto() {
        
        
        return 2.0; // non vale niente
    };
    
    
}
