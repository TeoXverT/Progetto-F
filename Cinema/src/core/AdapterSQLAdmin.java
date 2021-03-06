package core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import core.obj.Booking;
import core.obj.Config;
import core.obj.Film;
import core.obj.Hall;
import core.obj.Projection;
import core.obj.Seat;

/**
 * Classe concretizzazione di AdapterSQL: raccoglie i metodi necessari a fare richeiste su DB per l'utente admin
 *
*/
public class AdapterSQLAdmin extends AdapterSQL {

    public AdapterSQLAdmin() {
    }

    public ArrayList<Booking> getPayedBooking() throws SQLException {
        String query;
        ResultSet risultato_query;
        ArrayList<Booking> booking;

        query = "SELECT * FROM  `Booking` WHERE Booking.booking_status=2";
        risultato_query = SQL.readingQuery(query);

        booking = parser.booking(risultato_query);
        risultato_query.close();

        return booking;
    }

    public ArrayList<Booking> getUnSendedBooking() throws SQLException {
        String query;
        ResultSet risultato_query;
        ArrayList<Booking> booking;

        query = "SELECT * FROM  `Booking` WHERE Booking.booking_status=0";
        risultato_query = SQL.readingQuery(query);

        booking = parser.booking(risultato_query);
        risultato_query.close();

        return booking;
    }

    public ArrayList<Projection> getProjection(int type) throws SQLException { //G
        //TIPO = 0 //Odierne
        //TIPO = 1 //Future
        //TIPO = 3 //Sia odierne che future 
        String query;
        ResultSet risultato_query;
        ArrayList<Projection> projection;

        if (type == 0) {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) = DATE( NOW( ) ) ORDER BY Proiezione.data_ora DESC";
            risultato_query = SQL.readingQuery(query);
        } else if (type == 1) {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) ) ORDER BY Proiezione.data_ora DESC";
            risultato_query = SQL.readingQuery(query);
        } else {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) ) OR DATE( Proiezione.data_ora ) = DATE( NOW( ) ) ORDER BY Proiezione.data_ora DESC";
            risultato_query = SQL.readingQuery(query);
        }

        projection = parser.projection(risultato_query);
        risultato_query.close();

        return projection;
    }

    public boolean writeProjection(Projection projection) {//G
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String query = "INSERT INTO Proiezione(id_proiezione,data_ora, id_film, id_sala, projection_type, prezzo) VALUES (NULL," + "'" + sdf.format(projection.getData_ora().getTime()) + "','" + projection.getFilm().getIdFilm() + "','" + projection.getRoom().getIdHall() + "','" + projection.getpProjectionType() + "','" + projection.getPrice() + "');";
        try {
            SQL.writingQuery(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean checkProjection(Projection projection) {//G 
        ResultSet result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String query = "select Film.titolo\n"
                + "from Proiezione, Film,\n"
                + "					(\n"
                + "					SELECT '" + sdf.format(projection.getData_ora().getTime()) + "' as time_min,    ('" + sdf.format(projection.getData_ora().getTime()) + "' + INTERVAL Film.durata MINUTE + INTERVAL last_config.offset_time MINUTE) AS time_max, last_config.offset_time as offset_time\n"
                + "					FROM\n"
                + "						Film,\n"
                + "						(SELECT \n"
                + "							*\n"
                + "						FROM\n"
                + "							Config\n"
                + "						ORDER BY Config.id_config DESC\n"
                + "						LIMIT 1) AS last_config\n"
                + "					WHERE  Film.id_film = '" + projection.getFilm().getIdFilm() + "' \n"
                + "					) as time_interval\n"
                + "where Proiezione.id_sala='" + projection.getRoom().getIdHall() + "' AND datediff(Proiezione.data_ora,'" + sdf.format(projection.getData_ora().getTime()) + "')=0 AND Proiezione.id_film=Film.id_film AND(\n"
                + "(\n"
                + "Proiezione.data_ora<time_interval.time_min AND (Proiezione.data_ora +INTERVAL Film.durata MINUTE + INTERVAL time_interval.offset_time MINUTE) >time_interval.time_min\n"
                + ")  OR\n"
                + "(\n"
                + "Proiezione.data_ora<time_interval.time_max AND (Proiezione.data_ora +INTERVAL Film.durata MINUTE + INTERVAL time_interval.offset_time MINUTE) >time_interval.time_max\n"
                + ")  OR\n"
                + "(\n"
                + "Proiezione.data_ora<time_interval.time_min AND (Proiezione.data_ora +INTERVAL Film.durata MINUTE + INTERVAL time_interval.offset_time MINUTE) >time_interval.time_max\n"
                + ")OR(Proiezione.data_ora=time_interval.time_min AND (Proiezione.data_ora +INTERVAL Film.durata MINUTE + INTERVAL time_interval.offset_time MINUTE) =time_interval.time_max)\n"
                + ")";

        try {
            result = SQL.readingQuery(query);
            while (result.next()) {
                return false;
            }
            result.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean deleteProjection(int idPorjection) {//G 
        String query = "DELETE FROM Proiezione WHERE Proiezione.id_proiezione =" + idPorjection;
        try {
            SQL.writingQuery(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public ArrayList<Hall> getHall() throws SQLException {//G
        String query;
        ResultSet result;
        ArrayList<Hall> hall;

        query = "SELECT * FROM Sala";
        result = SQL.readingQuery(query);

        hall = parser.hall(result);
        result.close();
        
        return hall;
    }

    public boolean deleteHall(int idHall) {//G
        String query = "DELETE FROM Seats WHERE Seats.id_sala =" + idHall;

        try {
            SQL.writingQuery(query);
            query = "DELETE FROM Sala WHERE Sala.id_sala =" + idHall;
            SQL.writingQuery(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public ArrayList<Film> getFilm(int quantity) throws SQLException {//G 
        String query;
        ResultSet result;
        ArrayList<Film> film;

        if (quantity == 0) {
            query = "SELECT * FROM  `Film` WHERE Film.titolo IS NOT NULL AND Film.titolo != '' ORDER BY Film.data_ora DESC";

            result = SQL.readingQuery(query);
        } else {
            query = "SELECT * FROM `Film` ORDER BY data_ora desc LIMIT " + quantity;
            result = SQL.readingQuery(query);
        }

        film = parser.film(result);
        result.close();

        return film;
    }

    public boolean writeFilm(Film film) {//G

        String query = "INSERT INTO Film(titolo,genere,durata,descrizione,link_youtube,link_copertina) VALUES("
                + "'" + film.getTitle() + "','" + film.getCategories() + "','"
                + film.getLength() + "','" + film.getDescription() + "','" + film.getLinkYoutube() + "','"
                + film.getLinkCover() + "')";

        try {
            SQL.writingQuery(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    public boolean deleteFilm(int id_film) {//G
        String query = "DELETE FROM Film WHERE Film.id_film =" + id_film;
        try {
            SQL.writingQuery(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void writeConfig(Config config) throws SQLException {//G

        String query = "INSERT INTO Config(prezzo_vip,glasses_price,disabled_price,offset_time,booking_validation_time,ticket_validation_ip) VALUES("
                + "'" + config.getVipOverprice() + "','"
                + config.getGlassesPrice() + "','" + config.getHandicapPrice() + "','"
                + config.getOffsetTime() + "','" + config.getBookingValidationTime()+ "','" + config.getTicket_validation_ip()+ "')";
        SQL.writingQuery(query);
    }

    public boolean writeHall(Hall hall) {//G 
        ResultSet result;

        String query = "INSERT INTO Sala(rows,columns) VALUES("
                + "'" + hall.getRows() + "','" + hall.getColumns() + "')";
        try {
            SQL.writingQuery(query);
            query = "SELECT Sala.id_sala FROM Sala ORDER BY Sala.id_sala DESC LIMIT 1";
            result = SQL.readingQuery(query);
            while (result.next()) {
                hall.setIdHall(result.getInt("id_sala"));
            }

            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean writeSeats(Hall hall) {//G 
        ArrayList<Seat> seat = hall.getSeats();
        boolean complete = false;
        for (Seat s : seat) {
            try {
                String query = "INSERT INTO Seats(id_sala,x,y,tipo) VALUES("
                        + "'" + hall.getIdHall() + "','" + s.getx() + "','" + s.gety() + "','"
                        + s.giveType() + "')";
                SQL.writingQuery(query);
                complete = true;
            } catch (SQLException ex) {
                complete = false;
            }
        }
        return complete;

    }

    public ArrayList<Projection> getCurrentProjection() throws SQLException {//G
        String query;
        ResultSet result;
        ArrayList<Projection> projection;
        query = "SELECT Proiezione.*\n"
                + "FROM Proiezione, Film\n"
                + "WHERE (Proiezione.id_film=Film.id_film)\n"
                + "AND TIMESTAMPDIFF(MINUTE, Proiezione.data_ora + INTERVAL Film.durata MINUTE ,NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)<0\n"
                + "AND TIMESTAMPDIFF(MINUTE, Proiezione.data_ora + INTERVAL Film.durata MINUTE ,NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)>-Film.durata\n"
                + "";
        result = SQL.readingQuery(query);
        projection = parser.projection(result);
        result.close();
        return projection;
    }

    public ArrayList<Booking> getBooking() throws SQLException {//G

        String query;
        ResultSet risultato_query;
        ArrayList<Booking> booking;

        query = "SELECT * FROM Booking ORDER BY date_time DESC";
        risultato_query = SQL.readingQuery(query);

        booking = parser.booking(risultato_query);
        risultato_query.close();

        return booking;
    }

    public ArrayList<Projection> getProjectionByHall(int idHall) throws SQLException {//G
        String query;
        ResultSet result;
        ArrayList<Projection> projection;
        query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) = DATE( NOW( ) ) AND Proiezione.id_sala =" + idHall;
        result = SQL.readingQuery(query);

        projection = parser.projection(result);
        result.close();

        return projection;
    }

    public ArrayList<Booking> salesVolumeSearch(String a, String b) throws SQLException {//G
        ArrayList<Booking> booking;
        ResultSet risultato_query;
        String query = "SELECT * \n"
                + "FROM `Booking`\n"
                + "WHERE booking_status =3\n"
                + "AND date_time >= '" + a + "'\n"
                + "AND date_time <= '" + b + "'";
        risultato_query = SQL.readingQuery(query);
        booking = parser.booking(risultato_query);
        risultato_query.close();
        return booking;
    }

    public boolean bookingCleaner() {//G
        //Lettura di prenotazioni scadute (5 min), eliminazioni prenotazioni scadute sia booking che bookingseat
        //Per la relazione di  tipo RESTRICT elimino sia il posto/i che la prenotazione
        try {
            String query = "delete From Booked_Seat\n"
                    + "where Booked_Seat.id_booking in (select * from (\n"
                    + "select Booking.id_booking\n"
                    + "from Booking,Config\n"
                    + "where Booking.booking_status = 1 and \n"
                    + "TIMESTAMPDIFF(MINUTE, Booking.date_time ,NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)> Config.booking_validation_time\n"
                    + ") as Temp_table)";
            SQL.writingQuery(query);
            query = "DELETE FROM Booking\n"
                    + "where Booking.id_booking in (select * from (\n"
                    + "select Booking.id_booking\n"
                    + "from Booking,Config\n"
                    + "where Booking.booking_status = 1 and \n"
                    + "TIMESTAMPDIFF(MINUTE, Booking.date_time ,NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)> Config.booking_validation_time\n"
                    + ") as Temp_table)";
            SQL.writingQuery(query);

        } catch (SQLException ex) {
            return false;
        }

        return true;
    }

    public Projection getProjectionById(int idProjection) throws SQLException {
        String query;
        ResultSet result;
        ArrayList<Projection> projection = null;
        query = "SELECT * FROM  `Proiezione` WHERE  Proiezione.id_proiezione =" + idProjection;
        result = SQL.readingQuery(query);

        projection = parser.projection(result);
        result.close();

        return projection.get(0);
    }

    public Film getFilmById(int idFilm) throws SQLException {
        String query;
        ResultSet result;
        ArrayList<Film> film = null;
        query = "SELECT * FROM  `Film` WHERE Film.id_film = " + idFilm;
        result = SQL.readingQuery(query);

        film = parser.film(result);
        result.close();

        return film.get(0);
    }

    public Hall getHallByIdHall(int idHall) throws SQLException {
        String query;
        ResultSet result;
        ArrayList<Hall> hall;

        query = "SELECT * FROM Sala WHERE id_sala = '" + idHall + "'";

        result = SQL.readingQuery(query);
        hall = parser.hall(result);
        result.close();

        return hall.get(0);
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

    public ArrayList<Projection> getTodayProjectionByHall(int idHall) throws SQLException {
        String query;
        ResultSet result;
        ArrayList<Projection> projection;
        query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) = DATE( NOW() ) AND Proiezione.id_sala =" + idHall;
        result = SQL.readingQuery(query);

        projection = parser.projection(result);
        result.close();

        return projection;
    }

    public void saveTicket(int idBooking, String personalCode) throws SQLException {
        String query = "UPDATE Booking "
                + "SET Booking.booking_status=3, Booking.personal_code=\"" + personalCode
                + "\" WHERE Booking.id_booking = " + idBooking + "";
        SQL.writingQuery(query);
    }

    public void savePaymentRequest(int idBooking) throws SQLException {
        String query = "UPDATE Booking "
                + "SET Booking.booking_status=1,Booking.date_time=(NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)"
                + " WHERE Booking.id_booking = " + idBooking;

        SQL.writingQuery(query);
    }

    public ArrayList<Seat> getTakenSeatByIdBooking(int idBooking) throws SQLException {
        String query;
        ResultSet result;
        query = "SELECT Seats.* "
                + "FROM Booking,Booked_Seat, Seats "
                + "WHERE Booking.id_booking = " + idBooking + " AND Booking.id_booking=Booked_Seat.id_booking AND"
                + " Booked_Seat.id_seat = Seats.id_seat";
        result = SQL.readingQuery(query);
        return parser.seat(result);

    }
}
