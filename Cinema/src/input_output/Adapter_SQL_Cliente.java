/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import oggetti.Booking;
import oggetti.Film;
import oggetti.Room;
import oggetti.Screening;
import oggetti.Seat;

/**
 *
 * @author Yoga
 */
public class Adapter_SQL_Cliente extends Adapter_SQL {

    public Adapter_SQL_Cliente() {
    }

    public ArrayList<Film> futureFilm(int deltaData) throws SQLException {//C

        ArrayList<Film> Films;
        ResultSet risultatoQuery;

//        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String strDate1 = sdfDate.format(Data_ora_fine.getTime());
//        String strDate2 = sdfDate.format(Data_ora_inizio.getTime());
        String query = " SELECT DISTINCT   Film.id_film,    Film.titolo,    Film.descrizione,    Film.data_ora,   Film.durata,    Film.genere,    Film.link_copertina, Film.link_youtube "
                + "     FROM Film, Proiezione "
                + "     WHERE Film.id_film = Proiezione.id_film AND DATEDIFF(Proiezione.data_ora, (NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)) = " + deltaData + " AND TIMESTAMPDIFF(MINUTE,  NOW(),  Proiezione.data_ora)>0 "
                + "     ";
        /* Qui mettere la data e ora dopo la quale visaulizzare i film*/
        /*Se oggi mettere 0, altrimenti per domani metti 1 ecc...*/

        risultatoQuery = SQL.eseguiQueryLettura(query);
        Films = parser.Film(risultatoQuery);

        return Films;

    }

    public ArrayList<Film> FilmFuturoBySlider(int deltaData, int sliderValue) throws SQLException {//C

        ArrayList<Film> Films;
        ResultSet risultatoQuery;
        String query;
//        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String strDate1 = sdfDate.format(Data_ora_fine.getTime());
//        String strDate2 = sdfDate.format(Data_ora_inizio.getTime());
        //    String query = "SELECT DISTINCT f.id_film, f.titolo, f.descrizione, f.data_ora, f.durata, f.genere, f.link_copertina, f.link_youtube " +
        //    "FROM Proiezione p, Film f " +
        //    "WHERE f.id_film = p.id_film AND DATEDIFF(p.data_ora, NOW() + INTERVAL 135 MINUTE) = " +deltaData+ " AND p.data_ora > (concat(date(now()+ INTERVAL  " +deltaData+ " DAY + INTERVAL 135 MINUTE), ' " +sliderValue+ ":00:00'))";
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

        risultatoQuery = SQL.eseguiQueryLettura(query);
        Films = parser.Film(risultatoQuery);

        return Films;

    }

    public ArrayList<Screening> screeningFilteredByFilmAndTime(int id_film, Calendar focusedDateTime) throws SQLException {//C
        String query;
        ResultSet resultSet;
        ArrayList<Screening> screening;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar midnight = (Calendar) focusedDateTime.clone();

        midnight.set(Calendar.HOUR_OF_DAY, 23);
        midnight.set(Calendar.MINUTE, 59);
        midnight.set(Calendar.SECOND, 59);

        query = "select *\n"
                + "from Proiezione\n"
                + "where Proiezione.id_film= " + id_film + " and Proiezione.data_ora>  \"" + sdf.format(focusedDateTime.getTime()) + "\" and Proiezione.data_ora< \"" + sdf.format(midnight.getTime()) + "\"";

        resultSet = SQL.eseguiQueryLettura(query);
        screening = parser.Screening(resultSet);
        resultSet.close();
        return screening;
    }

    public Room getSalaByIdSala(int id_sala) throws SQLException {//C
        String query;
        ResultSet risultato_query;
        Room sala;

        query = "SELECT * FROM Sala WHERE id_sala = '" + id_sala + "'";

        risultato_query = SQL.eseguiQueryLettura(query);
        sala = parser.Room(risultato_query).get(0);
        risultato_query.close();

        return sala;
    }

    public ArrayList<Seat> getSeats(int id_sala) { //C         // da finire...sono arrivato a leggere da DB ora devo parsare le cose..
        String query;
        ResultSet risultato_query;
        ArrayList<Seat> seats = new ArrayList<>();
        ImageIcon seat_free = new ImageIcon("immagini/poltrone/seat_free.png");
        ImageIcon seat_disable = new ImageIcon("immagini/poltrone/seat_diasable.png");
        ImageIcon seat_vip = new ImageIcon("immagini/poltrone/seat_vip.png");
        ImageIcon seat_handicap = new ImageIcon("immagini/poltrone/seat_handicap.png");
        ImageIcon seat_taken = new ImageIcon("immagini/poltrone/seat_taken.png");

        query = "SELECT *  FROM Seats WHERE id_sala = '" + id_sala + "'";
        try {
            risultato_query = SQL.eseguiQueryLettura(query);
            int i = 0;
            while (risultato_query.next()) {
                seats.add(new Seat(risultato_query.getInt("x"), risultato_query.getInt("y")));
                switch (risultato_query.getInt("tipo")) {
                    case 1:
                        seats.get(i).setIcon(seat_free);
                        break;
                    case 2:
                        seats.get(i).setIcon(seat_vip);
                        seats.get(i).setVip(true);
                        break;
                    case 3:
                        seats.get(i).setIcon(seat_handicap);
                        seats.get(i).setHandicap(true);
                        break;
                    case 4:
                        seats.get(i).setIcon(seat_disable);
                        seats.get(i).setDisable(true);
                        break;
                    case 5:
                        seats.get(i).setIcon(seat_taken);
                        seats.get(i).setOccupato(true);
                        break;
                }
                seats.get(i).setId_seat(risultato_query.getInt("Id_seat"));
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Adapter_SQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return seats;

    }

    public int getIdLastBooking() throws SQLException {//C
        String query;
        ResultSet risultato_query;
        Booking prenotazione;

        query = "SELECT * FROM Booking ORDER BY Booking.id_booking DESC LIMIT 1";

        risultato_query = SQL.eseguiQueryLettura(query);
        prenotazione = parser.Booking(risultato_query).get(0);
        risultato_query.close();

        return prenotazione.getId_prenotazione();
    }

    public void writeBookedSeat(int idBooking, ArrayList<Seat> seats) throws SQLException {//C
        for (Seat s : seats) {
            String query = "INSERT INTO Booked_Seat(id_booking,id_seat) VALUES('" + idBooking + "','" + s.getId() + "')";
            SQL.eseguiQueryScrittura(query);
        }
    }

    public int writeBookin(Booking booking) throws SQLException {//C
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String query = "INSERT INTO Booking(id_proiezione,date_time,number_of_glasses,price,booking_status,email) VALUES("
                + "'" + booking.getScreening().getId_proiezione() + "','" + sdf.format(Calendar.getInstance().getTime()) + "','"
                + booking.getNumber_of_glasses() + "','" + booking.getPrezzo() + "','0',\"" + booking.getEmail() + "\")";
        SQL.eseguiQueryScrittura(query);
        return getIdLastBooking();
    }

    public boolean checkBookedSeat(int id_proiezione, ArrayList<Seat> posti) throws SQLException {//C

        for (Seat s : posti) {
            String query = "SELECT * FROM Booking,Booked_Seat where Booking.id_proiezione=" + id_proiezione + " AND Booking.id_booking=Booked_Seat.id_booking AND Booked_Seat.id_seat=" + s.getId();
            ResultSet risultato_query = SQL.eseguiQueryLettura(query);
            while (risultato_query.next()) { // Non ci entra se i posti sono ancora liberi
                return false;
            }
            risultato_query.close();
        }
        return true;
    }

    public ArrayList<Seat> getTakenSeats(int id_proiezione) throws SQLException {//C
        String query;
        ResultSet risultato_query;
        query = "SELECT Seats.* "
                + "FROM Booking,Booked_Seat, Seats "
                + "WHERE Booking.id_proiezione = " + id_proiezione + " AND Booking.id_booking=Booked_Seat.id_booking AND"
                + " Booked_Seat.id_seat = Seats.id_seat";
        risultato_query = SQL.eseguiQueryLettura(query);
        return parser.Seat(risultato_query);
    }

    public int checkBookingPayment(int idBooking) throws SQLException {//C
        int status;

        String Query = "SELECT booking_status "
                + "FROM Booking "
                + "WHERE Booking.id_booking = " + idBooking + "";

        ResultSet result = SQL.eseguiQueryLettura(Query);
        result.next();
        status = result.getInt("booking_status");

        return status;
    }

    public void insertFakePayment(Booking p) throws SQLException {//C

        String Query = "UPDATE Booking "
                + "SET Booking.booking_status=1 "
                + "WHERE Booking.id_booking = " + p.getId_prenotazione() + "";

        SQL.eseguiQueryScrittura(Query);

    }

}
