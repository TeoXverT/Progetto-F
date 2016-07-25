package oggetti;

import java.text.SimpleDateFormat;
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
public class Booking {

    private int id_prenotazione;
    private Calendar data_ora;
    private double prezzo;
    private ArrayList<Seat> posti_prenotati = new ArrayList<>();
    private int number_of_glasses = 0;
    private int booking_status = 0;
    private Screening screening;
    private String email;

    public Booking(int id_prenotazione, Screening screening, ArrayList<Seat> posti_prenotati, Calendar data_ora, int number_of_glasses, double prezzo, int booking_status, String email) {
        this.id_prenotazione = id_prenotazione;
        this.screening = screening;
        this.posti_prenotati = posti_prenotati;
        this.data_ora = data_ora;
        this.number_of_glasses = number_of_glasses;
        this.prezzo = prezzo;
        this.booking_status = booking_status;
        this.email = email;
    }

//    //Costruttore per gestione fatturato
//    public Booking(int id_prenotazione, Screening screening, Calendar data_ora, int number_of_glasses, double prezzo, String email) {
//        this.id_prenotazione = id_prenotazione;
//        this.screening = screening;
//        this.data_ora = data_ora;
//        this.number_of_glasses = number_of_glasses;
//        this.prezzo = prezzo;
//        this.email = email;
//    }

    public int getBooking_status() {
        return booking_status;
    }

    public String getData_ora_sql() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(data_ora.getTime());
    }

    public void setId_prenotazione(int id_prenotazione) {
        this.id_prenotazione = id_prenotazione;
    }

    public int getNumber_of_glasses() {
        return number_of_glasses;
    }

    @Override
    public String toString() {
        return "Prenotazione{" + "id_prenotazione=" + id_prenotazione + ", Proiezione=" + screening.toString() + ", data_ora=" + data_ora + ", prezzo=" + prezzo + ", posti_prenotati=" + posti_prenotati + '}';
    }

    public int getId_prenotazione() {
        return id_prenotazione;
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

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public void setNumber_of_glasses(int number) {
        this.number_of_glasses = number;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
