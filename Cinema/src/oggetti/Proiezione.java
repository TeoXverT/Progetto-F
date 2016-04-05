package oggetti;

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
    private int ID_proiezione;
    private int ID_film;
    private int ID_sala;
    private DataOra data_ora;
    private String tipo;
    
    
    public Proiezione(int ID_Proiezione, int ID_film, int ID_sala, DataOra data_ora, String tipo){
        this.ID_proiezione = ID_proiezione;
        this.ID_film = ID_film;
        this.ID_sala = ID_sala;
        this.data_ora = data_ora;
        this.tipo = tipo;
    }
    
    
   
    
}
