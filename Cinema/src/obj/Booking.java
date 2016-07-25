package obj;

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

    private int idBooking;
    private Calendar dateTime;
    private double price;
    private ArrayList<Seat> bookedSeat = new ArrayList<>();
    private int numberOfGlasses = 0;
    private int bookingStatus = 0;
    private Projection projection;
    private String email;

    public Booking(int idBooking, Projection projection, ArrayList<Seat> bookedSeat, Calendar dateTime, int numberOfGlasses, double price, int bookingStatus, String email) {
        this.idBooking = idBooking;
        this.projection = projection;
        this.bookedSeat = bookedSeat;
        this.dateTime = dateTime;
        this.numberOfGlasses = numberOfGlasses;
        this.price = price;
        this.bookingStatus = bookingStatus;
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
    public int getBookingStatus() {
        return bookingStatus;
    }

    public String getData_ora_sql() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dateTime.getTime());
    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public int getNumberOfGlasses() {
        return numberOfGlasses;
    }

//    @Override
//    public String toString() {
//        return "Prenotazione{" + "id_prenotazione=" + idBooking + ", Proiezione=" + projection.toString() + ", data_ora=" + dateTime + ", prezzo=" + price + ", posti_prenotati=" + bookedSeat + '}';
//    }
    public Calendar getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Seat> getBookedSeat() {
        return bookedSeat;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumberOfGlasses(int number) {
        this.numberOfGlasses = number;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
