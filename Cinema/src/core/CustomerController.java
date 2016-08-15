package core;

import core.AdapterSQLCustomer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import core.obj.Config;
import core.obj.Film;
import core.obj.Booking;
import core.obj.Projection;
import core.obj.Hall;
import core.obj.Seat;

/**
 * Controller per la parte cliente, ha il compito di coordinare le azioni e gestire il tutto per la parte grafica
 * 
 */

public class CustomerController {

    private static CustomerController instance;

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

    public ArrayList<Film> futureFilmBySlider(int deltaData, int sliderValue) throws SQLException {
        return adapter.getIncomingFilmBySlider(deltaData, sliderValue);
    }

    public Config getConfig() throws SQLException {
        if (config == null) {
            config = adapter.getConfig();
        }
        return config;
    }

    public Hall getHallByIdHall(int idHall) throws SQLException {
        return adapter.getHallByIdHall(idHall);
    }

    public ArrayList<Projection> projectionFilteredByFilmAndTime(int idFilm, Calendar focusedDateTime) throws SQLException {
        return adapter.getProjectionFilteredByFilmAndTime(idFilm, focusedDateTime);
    }

    public ArrayList<Seat> getSeatsByIdHall(int idHall) {
        ArrayList<Seat> seat;
        seat = adapter.getSeatByIdHall(idHall);
        return seat;
    }

    public int writeBooking(Booking booking) throws SQLException {
        int idBooking = 0;
        if (booking.getEmail().isEmpty() || booking.getBookedSeat().isEmpty() || booking.getProjection() == null) {
            return 0;
        }
        if (adapter.checkBookedSeat(booking.getProjection().getIdProjection(), booking.getBookedSeat())) { //Controllo disponibilità posti
            adapter.writeBooking(booking);
            idBooking = adapter.getIdLastBooking();
            adapter.writeBookedSeat(idBooking, booking.getBookedSeat());
        }

        ArrayList<Seat> seatTaken = adapter.getBookedSeat(booking);
        int i = 0;
        for (Seat s : seatTaken) {
            for (Seat seatBooked : booking.getBookedSeat()) {
                if (s.getId() == seatBooked.getId()) {
                    i++;
                }
            }
        }
        if(i!=seatTaken.size()){
        return 0;
        }

        return idBooking;
    }

    public ArrayList<Seat> getTakenSeats(int idProjection) throws SQLException {
        return adapter.getTakenSeats(idProjection);
    }

    public void getInsertPaymentForced(Booking booking) throws SQLException {
        adapter.insertFakePayment(booking.getIdBooking());
    }

    public boolean checkBookingPayment(int idBooking) throws SQLException {
        return adapter.checkBookingPayment(idBooking);
    }
}
