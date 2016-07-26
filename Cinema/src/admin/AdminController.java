package admin;

import obj.Seat;
import obj.Config;
import obj.Hall;
import obj.Film;
import obj.Projection;
import obj.Booking;
import inputoutput.AdapterSQLAdmin;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Yatin
 */
public class AdminController {

    private final AdapterSQLAdmin adapter;

    private final int PERIOD = 10000; //Dato per il Thread di pulitura bigletti non pagati
    private final char[] PASSWORD = {'1', '2', '3'};
    private final String ALPHA_NUMERIC_SEED = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int CODE_LENGTH = 5;
    private EmailSender emailSender;

    public AdminController() {

        adapter = new AdapterSQLAdmin();
        emailSender = new EmailSender();

        Timer timer = new Timer();
        timer.schedule(ticketSender(), 0, PERIOD);

    }

    private TimerTask ticketSender() {
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                adapter.bookingCleaner();
                try {
                    ArrayList<Booking> payedBooking = adapter.getPayedBooking();
                    for (Booking booking : payedBooking) {
                        System.out.println("Send ticket to: " + booking.getEmail());
                        String personalCode = randomAlphaNumeric();
                        if (emailSender.sendTicket(booking, personalCode)) {
                            adapter.saveTicket(booking.getIdBooking(), personalCode);
                        }
                    }

                } catch (SQLException ex) {
                    System.out.println("ERROR: Ticket Sender");
                }

                try {
                    ArrayList<Booking> unSendedBooking = adapter.getUnSendedBooking();
                    for (Booking booking : unSendedBooking) {
                        ArrayList<Seat> seat = adapter.getTakenSeatByIdBooking(booking.getIdBooking());
                        booking.setBookedSeat(seat);
                        Projection projection = adapter.getProjectionById(booking.getProjection().getIdProjection());
                        projection.setFilm(adapter.getFilmById(projection.getFilm().getIdFilm()));
                        booking.setProjection(projection);
                        System.out.println("Send Payment Request to: " + booking.getEmail());
                        if (emailSender.sendPaymentRequest(booking)) {
                            adapter.savePaymentRequest(booking.getIdBooking());
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("ERROR: Payment request");
                }

            }
        };
        return t;
    }

    private String randomAlphaNumeric() {
        int count = CODE_LENGTH;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_SEED.length());
            builder.append(ALPHA_NUMERIC_SEED.charAt(character));
        }
        return builder.toString();
    }

    public boolean codeVerification(char[] code) {
        boolean isCorrect;
        char[] correctPassword = PASSWORD;

        if (Arrays.equals(code, correctPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Booking> getBooking() throws SQLException {
        return adapter.getBooking();
    }

    public ArrayList<Projection> getProjection(int type) throws SQLException {
        //TIPO = 0 //Odierne
        //TIPO = 1 //Future
        //TIPO = 2  //Odierne e Future 
        ArrayList<Projection> projection = adapter.getProjection(type);
        return projection;
    }

    public boolean writeProjection(Projection projection) throws SQLException {
        if (adapter.checkProjection(projection)) { //Eventuale controllo sul valore dei campi di config
            return adapter.writeProjection(projection);
        } else {
            return false;
        }
    }

    public boolean deleteProjection(int idProjection) {
        return adapter.deleteProjection(idProjection);
    }

    public ArrayList<Film> getFilm(int quantity) throws SQLException {
        //quantita_max_da_visualizzare = 0 //NO LIMIT
        ArrayList<Film> film = adapter.getFilm(quantity);
        return film;
    }

    public boolean writeFilm(Film film) {
        if ((!"".equals(film.getTitle())) && (!"".equals(film.getDescription())) && (!"".equals(film.getLinkCover())) & (!"".equals(film.getCategories())) & (film.getLength() > 0)) {

            film.setDescription(stringCharReplacer(film.getDescription()));
            return adapter.writeFilm(film);
        } else {
            return false;
        }
    }

    public boolean deleteFilm(int id_film) {
        return adapter.deleteFilm(id_film);
    }

    public boolean writeConfig(Config config) throws SQLException {
        if (config.getBookingValidationTime() > 0 && config.getDisabledPrice() > -0.0001 && config.getGlassesPrice() > 0 && config.getOffsetTime() > 0 && config.getVipOverprice() > 0) {
            adapter.writeConfig(config);
            return true;
        } else {
            return false;
        }
    }

    public Config getConfig() throws SQLException {
        Config config = adapter.getConfig();
        return config;
    }

    public ArrayList<Hall> getHall() throws SQLException {
        ArrayList<Hall> hall = adapter.getHall();
        return hall;
    }

    public boolean writeHall(Hall hall) {
        if (adapter.writeHall(hall) == true) {
            if (adapter.writeSeats(hall) == true) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteHall(int idHall) {
        return adapter.deleteHall(idHall);
    }

    public ArrayList<Projection> getCurrentProjection() throws SQLException {
        ArrayList<Projection> projection = adapter.getCurrentProjection();
        return projection;
    }

    public ArrayList<Projection> getProjectionByHall(int idHall) throws SQLException {
        ArrayList<Projection> projection = adapter.getProjectionByHall(idHall);
        return projection;
    }

    public ArrayList<Booking> salesVolume(String a, String b) throws SQLException {
        ArrayList<Booking> books = adapter.salesVolumeSearch(a, b);
        return books;
    }

    public Hall getHall(int idHall) throws SQLException {
        return adapter.getHallByIdHall(idHall);
    }

    public ArrayList<Seat> getTakenSeats(int idProjection) throws SQLException {
        return adapter.getTakenSeats(idProjection);
    }

    public ArrayList<Projection> getTodayProjectionByHall(int idHall) throws SQLException {
        ArrayList<Projection> projection = adapter.getTodayProjectionByHall(idHall);
        return projection;
    }

    public String stringCharReplacer(String a) {
        a = a.replaceAll("\"", " ");
        a = a.replaceAll("'", " ");
        return a;
    }
}
