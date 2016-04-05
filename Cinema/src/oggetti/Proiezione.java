package oggetti;

import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yoga
 */
public class Proiezione {
    private int id_protezione;
    private Calendar data_ora;
    private int id_film;
    private int id_sala;
    
    private String tipo_proiezione;
    private int prezzo_normale;
    private int prezzo_3d;

    public Proiezione(int id_protezione, Calendar data_ora, int id_film, int id_sala, String tipo_proiezione, int prezzo_normale, int prezzo_3d) {
        this.id_protezione = id_protezione;
        this.data_ora = data_ora;
        this.id_film = id_film;
        this.id_sala = id_sala;
        this.tipo_proiezione = tipo_proiezione;
        this.prezzo_normale = prezzo_normale;
        this.prezzo_3d = prezzo_3d;
    }
    
    
 
   
    
}
