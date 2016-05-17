package oggetti;

import Gestore.Controller_Gestore;
import java.text.SimpleDateFormat;
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

    private int id_proiezione;
    private Calendar data_ora;
    private int id_film;
    private int id_sala;

    private String tipo_proiezione;
    private double prezzo;

    public Proiezione(int id_proiezione, Calendar data_ora, int id_film, int id_sala, String tipo_proiezione, double prezzo) {
        this.id_proiezione = id_proiezione;
        this.data_ora = data_ora;
        this.id_film = id_film;
        this.id_sala = id_sala;
        this.tipo_proiezione = tipo_proiezione;
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Sala:" + id_sala + " Del:" + getData_ora_friendly();
    }

    public int getId_proiezione() {
        return id_proiezione;
    }

    public String getData_ora_sql() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(data_ora.getTime());
    }

    public String getData_ora_friendly() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
        return sdf.format(data_ora.getTime());
    }

    public Calendar getData_ora() {
        return data_ora;
    }

    public int getId_film() {
        return id_film;
    }

    public int getId_sala() {
        return id_sala;
    }

    public String getTipo_proiezione() {
        return tipo_proiezione;
    }

    public double getPrezzo() {
        return prezzo;
    }

}
