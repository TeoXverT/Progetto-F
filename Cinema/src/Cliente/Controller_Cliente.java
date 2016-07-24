/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import input_output.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import oggetti.Config;
import oggetti.Film;
import oggetti.Booking;
import oggetti.Screening;
import oggetti.Room;
import oggetti.Seat;

/**
 *
 * @author Yoga
 */
public class Controller_Cliente {

    private static Controller_Cliente instance;

    private ArrayList<Screening> listaProiezioniFuture;
    private ArrayList<Screening> listaProiezioniFiltrate;
    private Adapter_SQL adapter;
    private Config config;

    private Controller_Cliente() {
        adapter = Adapter_SQL.getInstance();
    }

    public static synchronized Controller_Cliente getInstance() {
        if (instance == null) {
            instance = new Controller_Cliente();
        }
        return instance;
    }

    public ArrayList<Screening> listaProiezioniFuture(ArrayList<Screening> listaProiezione) {
        int i;
        Calendar giornoAttuale;
        TimeZone timezone = TimeZone.getTimeZone("Europe/Rome");
        Screening proiezioneProva;

        listaProiezioniFuture = new ArrayList<>();

        giornoAttuale = Calendar.getInstance(timezone);

        for (i = 0; i < listaProiezione.size(); i++) {

            if (listaProiezione.get(i).getData_ora().after(giornoAttuale)) {

                proiezioneProva = new Screening(listaProiezione.get(i).getId_proiezione(), listaProiezione.get(i).getData_ora(), listaProiezione.get(i).getFilm(), listaProiezione.get(i).getRoom(), listaProiezione.get(i).getTipo_proiezione(), listaProiezione.get(i).getPrezzo());

                listaProiezioniFuture.add(proiezioneProva);

            }

        }

        return listaProiezioniFuture;
    }

    public ArrayList<Screening> listaProiezioniFiltrate(ArrayList<Screening> listaProiezione, int GiornoSettimana) {

        int i;
        Calendar giornoAttuale;

        TimeZone timezone = TimeZone.getTimeZone("Europe/Rome");
        Screening proiezioneProva;

        listaProiezioniFiltrate = new ArrayList<>();

        giornoAttuale = Calendar.getInstance(timezone);

        for (i = 0; i < listaProiezione.size(); i++) {
            System.out.println(listaProiezione.get(i).getData_ora().get(Calendar.DAY_OF_WEEK));
            if (listaProiezione.get(i).getData_ora().after(giornoAttuale) && (listaProiezione.get(i).getData_ora().get(Calendar.DAY_OF_WEEK)) == GiornoSettimana) {

                proiezioneProva = new Screening(listaProiezione.get(i).getId_proiezione(), listaProiezione.get(i).getData_ora(), listaProiezione.get(i).getFilm(), listaProiezione.get(i).getRoom(), listaProiezione.get(i).getTipo_proiezione(), listaProiezione.get(i).getPrezzo());

                listaProiezioniFiltrate.add(proiezioneProva);

            }

        }

        return listaProiezioniFiltrate;
    }

    public ArrayList<Film> FilmFuturo(int deltaData) throws SQLException {

        return adapter.FilmFuturo(deltaData);

    }

    public ArrayList<Film> FilmFuturoBySlider(int deltaData, int sliderValue) throws SQLException {

        return adapter.FilmFuturoBySlider(deltaData, sliderValue);

    }

    public Config getConfig() throws SQLException {
        if (config == null) {
            config = adapter.visualizzaConfig();
        }
        return config;
    }

    public Room salaByID(int id_Sala) throws SQLException {
        return adapter.getSalaByIdSala(id_Sala);
    }

    public ArrayList<Screening> showByFilm(int id_film, int deltaData, int ora) throws SQLException {
        return adapter.getShowByFilm(id_film, deltaData, ora);
    }

    public ArrayList<Seat> getSeats(int id_sala) {
        ArrayList<Seat> seat;
        seat = adapter.getSeats(id_sala);
        return seat;
    }

    public int writeBooking(Booking booking) throws SQLException {
        int idBooking = 0; 

        if (adapter.checkBookedSeat(booking.getScreening().getId_proiezione(), booking.getPosti_prenotati())) { //Controllo disponibilità posti
            idBooking = adapter.writeBookin(booking);
            adapter.writeBookedSeat(idBooking, booking.getPosti_prenotati());
        }

        return idBooking;  //Se uguale a zero è fallita la scittura altrimenti contiene il numero della prenotazione
    }

    public ArrayList<Seat> getTakenSeats(int id_proiezione) throws SQLException {
        return adapter.getTakenSeats(id_proiezione);
    }

    public void getInsertPaymentForced(Booking p) throws SQLException {
        adapter.insertFakePayment(p);
    }

    public int checkBookingPayment(int idBooking) throws SQLException {
        return adapter.checkBookingPayment(idBooking);
    }
}
