package core;

import core.obj.Seat;
import core.obj.Hall;
import core.obj.Projection;
import core.obj.Film;
import core.obj.Booking;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



/**
 * Classe concretizzazione di AdapterSQL: raccoglie i metodi necessari a fare richeiste su DB per il cliente
 *
*/


public class AdapterSQLCustomer extends AdapterSQL {

    public AdapterSQLCustomer() {
    }


    public ArrayList<Film> getIncomingFilmBySlider(int deltaData, int sliderValue) throws SQLException {//C

        ArrayList<Film> film;
        ResultSet result;
        String query;

        /* Qui mettere la data e ora dopo la quale visaulizzare i film*/
        /*Se oggi mettere 0, altrimenti per domani metti 1 ecc...*/

        if ((deltaData == 0) && (sliderValue <= Calendar.getInstance().get(Calendar.HOUR_OF_DAY))) {
            query = "SELECT DISTINCT f.id_film, f.titolo, f.descrizione, f.data_ora, f.durata, f.genere, f.link_copertina, f.link_youtube "
                    + "FROM Proiezione p, Film f "
                    + "WHERE f.id_film = p.id_film AND DATEDIFF(p.data_ora, NOW() + INTERVAL " + ((TIME_ZONE_COMPENSATION * 60) + TIME_LIMIT_ONLINE_PURCHASE) + " MINUTE) = " + deltaData + " AND p.data_ora > (concat(date(now()+ INTERVAL  " + deltaData + " DAY + INTERVAL " + ((TIME_ZONE_COMPENSATION * 60) + TIME_LIMIT_ONLINE_PURCHASE) + " MINUTE), ' " + sliderValue + ":00:00')) AND p.data_ora > (NOW() +INTERVAL " + ((TIME_ZONE_COMPENSATION * 60) + TIME_LIMIT_ONLINE_PURCHASE) + " MINUTE)";

        } else {
            query = "SELECT DISTINCT f.id_film, f.titolo, f.descrizione, f.data_ora, f.durata, f.genere, f.link_copertina, f.link_youtube "
                    + "FROM Proiezione p, Film f "
                    + "WHERE f.id_film = p.id_film AND DATEDIFF(p.data_ora, NOW() + INTERVAL " + ((TIME_ZONE_COMPENSATION * 60) + TIME_LIMIT_ONLINE_PURCHASE) + " MINUTE) = " + deltaData + " AND p.data_ora > (concat(date(now()+ INTERVAL  " + deltaData + " DAY + INTERVAL " + ((TIME_ZONE_COMPENSATION * 60) + TIME_LIMIT_ONLINE_PURCHASE) + " MINUTE), ' " + sliderValue + ":00:00'))";
        }

        result = SQL.readingQuery(query);
        film = parser.film(result);

        return film;

    }

    public ArrayList<Projection> getProjectionFilteredByFilmAndTime(int id_film, Calendar focusedDateTime) throws SQLException {//C
        String query;
        ResultSet resultSet;
        ArrayList<Projection> projection;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar midnight = (Calendar) focusedDateTime.clone();

        midnight.set(Calendar.HOUR_OF_DAY, 23);
        midnight.set(Calendar.MINUTE, 59);
        midnight.set(Calendar.SECOND, 59);

        query = "select *\n"
                + "from Proiezione\n"
                + "where Proiezione.id_film= " + id_film + " and Proiezione.data_ora>  \"" + sdf.format(focusedDateTime.getTime()) + "\" and Proiezione.data_ora< \"" + sdf.format(midnight.getTime()) + "\"";

        resultSet = SQL.readingQuery(query);
        projection = parser.projection(resultSet);
        resultSet.close();
        return projection;
    }

    public Hall getHallByIdHall(int idHall) throws SQLException {//C
        String query;
        ResultSet result;
        Hall hall;

        query = "SELECT * FROM Sala WHERE id_sala = '" + idHall + "'";

        result = SQL.readingQuery(query);
        
        hall = parser.hall(result).get(0);
        result.close();

        return hall;
    }

    public int getIdLastBooking() throws SQLException {//C
        String query;
        ResultSet result;
        Booking booking;

        query = "SELECT * FROM Booking ORDER BY Booking.id_booking DESC LIMIT 1";

        result = SQL.readingQuery(query);
        booking = parser.booking(result).get(0);
        result.close();

        return booking.getIdBooking();
    }

    public void writeBookedSeat(int idBooking, ArrayList<Seat> seats) throws SQLException {//C
        for (Seat s : seats) {
            String query = "INSERT INTO Booked_Seat(id_booking,id_seat) VALUES('" + idBooking + "','" + s.getId() + "')";
            SQL.writingQuery(query);
        }
    }

    public void writeBooking(Booking booking) throws SQLException {//C
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String query = "INSERT INTO Booking(id_proiezione,date_time,number_of_glasses,price,booking_status,email,personal_code) VALUES("
                + "'" + booking.getProjection().getIdProjection() + "','" + sdf.format(Calendar.getInstance().getTime()) + "','"
                + booking.getNumberOfGlasses() + "','" + booking.getPrice() + "','0',\"" + booking.getEmail() + "\",'0')";
        SQL.writingQuery(query);
    }

    public boolean checkBookedSeat(int idProjection, ArrayList<Seat> seat) throws SQLException {//C

        for (Seat s : seat) {
            String query = "SELECT * FROM Booking,Booked_Seat where Booking.id_proiezione=" + idProjection + " AND Booking.id_booking=Booked_Seat.id_booking AND Booked_Seat.id_seat=" + s.getId();
            ResultSet result = SQL.readingQuery(query);
            while (result.next()) { // Non ci entra se i posti sono ancora liberi
                return false;
            }
            result.close();
        }
        return true;
    }
    public ArrayList<Seat> getBookedSeat(Booking booking) throws SQLException {//C
        String query;
        ResultSet result;
        query = "SELECT Seats.* FROM Booked_Seat,Seats where Booked_Seat.id_booking = "+ booking.getIdBooking()+" and Booked_Seat.id_seat=Seats.id_seat";
        result = SQL.readingQuery(query);
        return parser.seat(result);
    }

    public ArrayList<Seat> getTakenSeats(int idProjection) throws SQLException {//C
        String query;
        ResultSet result;
        query = "SELECT Seats.* "
                + "FROM Booking,Booked_Seat, Seats "
                + "WHERE Booking.id_proiezione = " + idProjection + " AND Booking.id_booking=Booked_Seat.id_booking AND"
                + " Booked_Seat.id_seat = Seats.id_seat";
        result = SQL.readingQuery(query);
        return parser.seat(result);
    }

    public boolean checkBookingPayment(int idBooking) throws SQLException {//C
        String Query = "SELECT Booking.booking_status "
                + "FROM Booking "
                + "WHERE Booking.id_booking = " + idBooking;

        ResultSet result = SQL.readingQuery(Query);
        result.next();
        return result.getInt("booking_status") == 2;
    }

    public void insertFakePayment(int idBooking) throws SQLException {//C
        String Query = "UPDATE Booking "
                + "SET Booking.booking_status=2 "
                + "WHERE Booking.id_booking = " + idBooking + "";
        SQL.writingQuery(Query);
    }

}
