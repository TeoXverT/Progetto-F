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
public class Screening {

    private int id_screening;
    private Calendar data_ora;
    private Film film;
    private Room room;
    private int tipo_proiezione; // 0- Normale 1- 3D 2-IMAX 3D 3- Live Event
    private double prezzo;

    public Screening(int id_screening, Calendar data_ora, Film film, Room room, int tipo_proiezione, double prezzo) {
        this.id_screening = id_screening;
        this.data_ora = data_ora;
        this.film = film;
        this.room = room;
        this.tipo_proiezione = tipo_proiezione;
        this.prezzo = prezzo;
    }
     public Screening(int id_proiezione) {
        this.id_screening = id_proiezione;
    }

    @Override
    public String toString() {
        return "Sala:" + id_screening + " Del:" + getData_ora_friendly();
    }

    public int getId_proiezione() {
        return id_screening;
    }

    public String getData_ora_sql() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(data_ora.getTime());
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getData_ora_friendly() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
        return sdf.format(data_ora.getTime());
    }

    public String getData_ora_friendly_2() {
        SimpleDateFormat giorno = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat ora = new SimpleDateFormat("HH:mm");
        return "Giorno: " + giorno.format(data_ora.getTime()) + " Alle Ore: " + ora.format(data_ora.getTime());
    }

    public Calendar getData_ora() {
        return data_ora;
    }

    public int getTipo_proiezione() {
        return tipo_proiezione;
    }

    public boolean isTypeNormal() {
        return tipo_proiezione == 0;
    }

    public boolean isType3D() {
        return tipo_proiezione == 1;
    }

    public boolean isTypeIMAX3D() {
        return tipo_proiezione == 2;
    }

    public boolean isTypeLiveEvent() {
        return tipo_proiezione == 3;
    }

    public String getType_String() {
        switch (tipo_proiezione) {
            case 0:
                return "Normal";
            case 1:
                return "3D";
            case 2:
                return "IMAX 3D";
            case 3:
                return "Live Show";
            default:
                return "Error";
        }
    }

    public double getPrezzo() {
        return prezzo;
    }

    public Film getFilm() {
        return film;
    }

    public Room getRoom() {
        return room;
    }

}
