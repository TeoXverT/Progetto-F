package core.obj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/*
 * Classe che rappresenta il concetto di prenotazione, ha al suo interno oltre che a diversi attributi un array list di oggetti posto e l'oggetto proiezione
 *
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

    public void setBookedSeat(ArrayList<Seat> bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Booking{" + "idBooking=" + idBooking + ", dateTime=" + dateTime + ", price=" + price + ", bookedSeat=" + bookedSeat + ", numberOfGlasses=" + numberOfGlasses + ", bookingStatus=" + bookingStatus + ", projection=" + projection + ", email=" + email + '}';
    }
    
    public String toStringFileSalesVolume() {
        return "ID = " + idBooking + " | Date & Time = " + getData_ora_sql() + " | price = " + price + " | 3D Glasses = " + numberOfGlasses;
    }
}
