/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import inputoutput.AdapterSQLCustomer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import obj.Config;
import obj.Film;
import obj.Booking;
import obj.Projection;
import obj.Hall;
import obj.Seat;

/**
 *
 * @author Yoga
 */
public class CustomerController {

    private static CustomerController instance;

//    private ArrayList<Projection> listaProiezioniFuture;
//    private ArrayList<Projection> listaProiezioniFiltrate;
    private AdapterSQLCustomer adapter;
    private Config config;

    public CustomerController() {
        adapter = new AdapterSQLCustomer();
    }

    public static synchronized CustomerController getInstance() {
        if (instance == null) {
            instance = new CustomerController();
        }
        return instance;
    }

//    public ArrayList<Projection> listFutureProjections(ArrayList<Projection> projection) {
//        int i;
//        Calendar giornoAttuale;
//        TimeZone timezone = TimeZone.getTimeZone("Europe/Rome");
//        Projection proiezioneProva;
//        ArrayList<Projection> futureProjection = new ArrayList<>();
//        giornoAttuale = Calendar.getInstance(timezone);
//        for (i = 0; i < projection.size(); i++) {
//            if (projection.get(i).getData_ora().after(giornoAttuale)) {
//                proiezioneProva = new Projection(projection.get(i).getIdProjection(), projection.get(i).getData_ora(), projection.get(i).getFilm(), projection.get(i).getRoom(), projection.get(i).getpProjectionType(), projection.get(i).getPrice());
//                futureProjection.add(proiezioneProva);
//            }
//        }
//        return futureProjection;
//    }

//    public ArrayList<Projection> listFilteredProjections(ArrayList<Projection> listaProiezione, int GiornoSettimana) {
//        int i;
//        Calendar giornoAttuale;
//        TimeZone timezone = TimeZone.getTimeZone("Europe/Rome");
//        Projection proiezioneProva;
//        ArrayList<Projection> listaProiezioniFiltrate = new ArrayList<>();
//        giornoAttuale = Calendar.getInstance(timezone);
//        for (i = 0; i < listaProiezione.size(); i++) {
//            System.out.println(listaProiezione.get(i).getData_ora().get(Calendar.DAY_OF_WEEK));
//            if (listaProiezione.get(i).getData_ora().after(giornoAttuale) && (listaProiezione.get(i).getData_ora().get(Calendar.DAY_OF_WEEK)) == GiornoSettimana) {
//                proiezioneProva = new Projection(listaProiezione.get(i).getIdProjection(), listaProiezione.get(i).getData_ora(), listaProiezione.get(i).getFilm(), listaProiezione.get(i).getRoom(), listaProiezione.get(i).getpProjectionType(), listaProiezione.get(i).getPrice());
//                listaProiezioniFiltrate.add(proiezioneProva);
//            }
//        }
//        return listaProiezioniFiltrate;
//    }

    public ArrayList<Film> futureFilmBySlider(int deltaData, int sliderValue) throws SQLException {
        return adapter.getIncomingFilmBySlider(deltaData, sliderValue);
    }

    public Config getConfig() throws SQLException {
        if (config == null) {
            config = adapter.getConfig();
        }
        return config;
    }

    public Hall roomByID(int idHall) throws SQLException {
        return adapter.getHallByIdHall(idHall); 
    }

    public ArrayList<Projection> projectionFilteredByFilmAndTime(int idFilm, Calendar focusedDateTime) throws SQLException {
        return adapter.getProjectionFilteredByFilmAndTime(idFilm, focusedDateTime); 
    }

    public ArrayList<Seat> getSeats(int idHall) {
        ArrayList<Seat> seat;
        seat = adapter.getSeatByIdHall(idHall); 
        return seat;
    }

    public int writeBooking(Booking booking) throws SQLException {
        int idBooking = 0;
        if (adapter.checkBookedSeat(booking.getProjection().getIdProjection(), booking.getBookedSeat())) { //Controllo disponibilità posti
            adapter.writeBooking(booking);
            idBooking = adapter.getIdLastBooking();
            adapter.writeBookedSeat(idBooking, booking.getBookedSeat());
        }
        return idBooking;
    }

    public ArrayList<Seat> getTakenSeats(int idProjection) throws SQLException {
        return adapter.getTakenSeats(idProjection);
    }

    public void getInsertPaymentForced(Booking booking) throws SQLException {
        adapter.insertFakePayment(booking.getIdBooking()); 
    }

    public int checkBookingPayment(int idBooking) throws SQLException {
        return adapter.checkBookingPayment(idBooking);
    }
}
