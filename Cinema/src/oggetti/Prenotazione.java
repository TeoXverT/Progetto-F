package oggetti;

import java.util.ArrayList;
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

    private int id_prenotazione, id_proiezione;
    private Calendar data_ora;
    private double prezzo;
    private ArrayList<Seat> posti_prenotati = new ArrayList<>();

    public Prenotazione(int id_prenotazione, int id_proiezione, ArrayList<Seat> posti_prenotati, Calendar data_ora, double prezzo) {
        this.id_prenotazione = id_prenotazione;
        this.id_proiezione = id_proiezione;
        this.posti_prenotati = posti_prenotati;
        this.data_ora = data_ora;
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Prenotazione{" + "id_prenotazione=" + id_prenotazione + ", id_proiezione=" + id_proiezione + ", data_ora=" + data_ora + ", prezzo=" + prezzo + ", posti_prenotati=" + posti_prenotati + '}';
    }

    public int getId_prenotazione() {
        return id_prenotazione;
    }

    public int getId_proiezione() {
        return id_proiezione;
    }

    public Calendar getData_ora() {
        return data_ora;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public ArrayList<Seat> getPosti_prenotati() {
        return posti_prenotati;
    }
    
    
    

}
