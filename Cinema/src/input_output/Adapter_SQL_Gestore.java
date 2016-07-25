/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oggetti.Booking;
import oggetti.Config;
import oggetti.Film;
import oggetti.Room;
import oggetti.Screening;
import oggetti.Seat;

/**
 *
 * @author Yoga
 */
public class Adapter_SQL_Gestore extends Adapter_SQL {

    public Adapter_SQL_Gestore() {
    }

    public ArrayList<Screening> showScreening(int tipo) throws SQLException { //G
        //TIPO = 0 //Odierne
        //TIPO = 1 //Future
        //TIPO = 3 //Sia odierne che future
        String query;
        ResultSet risultato_query;
        ArrayList<Screening> Proiezioni;

        if (tipo == 0) {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) = DATE( NOW( ) ) ORDER BY Proiezione.data_ora DESC";
            risultato_query = SQL.eseguiQueryLettura(query);
        } else if (tipo == 1) {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) ) ORDER BY Proiezione.data_ora DESC";
            risultato_query = SQL.eseguiQueryLettura(query);
        } else {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) ) OR DATE( Proiezione.data_ora ) = DATE( NOW( ) ) ORDER BY Proiezione.data_ora DESC";
            risultato_query = SQL.eseguiQueryLettura(query);
        }

        Proiezioni = parser.Screening(risultato_query);
        risultato_query.close();

        return Proiezioni;
    }

    public boolean writeScrinning(Screening proiezione) {//G
        String query = "INSERT INTO Proiezione(id_proiezione,data_ora, id_film, id_sala, projection_type, prezzo) VALUES (NULL," + "'" + proiezione.getData_ora_sql() + "','" + proiezione.getFilm().getId_film() + "','" + proiezione.getRoom().getId_sala() + "','" + proiezione.getTipo_proiezione() + "','" + proiezione.getPrezzo() + "');";
        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean checkScreening(Screening proiezione) {//G
        ResultSet risultato_query;

        String query = "select Film.titolo\n"
                + "from Proiezione, Film,\n"
                + "					(\n"
                + "					SELECT '" + proiezione.getData_ora_sql() + "' as time_min,    ('" + proiezione.getData_ora_sql() + "' + INTERVAL Film.durata MINUTE + INTERVAL last_config.offset_time MINUTE) AS time_max, last_config.offset_time as offset_time\n"
                + "					FROM\n"
                + "						Film,\n"
                + "						(SELECT \n"
                + "							*\n"
                + "						FROM\n"
                + "							sql8115909.Config\n"
                + "						ORDER BY Config.id_config DESC\n"
                + "						LIMIT 1) AS last_config\n"
                + "					WHERE  Film.id_film = '" + proiezione.getFilm().getId_film() + "' \n"
                + "					) as time_interval\n"
                + "where Proiezione.id_sala='" + proiezione.getRoom().getId_sala() + "' AND datediff(Proiezione.data_ora,'" + proiezione.getData_ora_sql() + "')=0 AND Proiezione.id_film=Film.id_film AND(\n"
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
            risultato_query = SQL.eseguiQueryLettura(query);
            while (risultato_query.next()) {
                return false;
            }
            risultato_query.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean deleteScreening(int id_proiezione) {//G
        String query = "DELETE FROM Proiezione WHERE Proiezione.id_proiezione =" + id_proiezione;
        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public ArrayList<Room> showRoom() throws SQLException {//G
        String query;
        ResultSet risultato_query;
        ArrayList<Room> Sale;

        query = "SELECT * FROM Sala";
        risultato_query = SQL.eseguiQueryLettura(query);
        Sale = parser.Room(risultato_query);
        risultato_query.close();

        return Sale;
    }

    public boolean deleteRoom(int id_sale) {//G
        String query = "DELETE FROM Seats WHERE Seats.id_sala =" + id_sale;

        try {
            SQL.eseguiQueryScrittura(query);
            query = "DELETE FROM Sala WHERE Sala.id_sala =" + id_sale;
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public ArrayList<Film> showFilm(int quantita_max_da_visualizzare) throws SQLException {//G
        String query;
        ResultSet risultato_query;
        ArrayList<Film> Films;

        if (quantita_max_da_visualizzare == 0) {
            query = "SELECT * FROM  `Film` WHERE Film.titolo IS NOT NULL AND Film.titolo != '' ORDER BY Film.data_ora DESC";

            risultato_query = SQL.eseguiQueryLettura(query);
        } else {
            query = "SELECT * FROM `Film` ORDER BY data_ora desc LIMIT " + quantita_max_da_visualizzare;
            risultato_query = SQL.eseguiQueryLettura(query);
        }

        Films = parser.Film(risultato_query);
        risultato_query.close();

        return Films;
    }

    public boolean writeFilm(Film film) {//G

        String query = "INSERT INTO Film(titolo,genere,durata,descrizione,link_youtube,link_copertina) VALUES("
                + "'" + film.getTitolo_film() + "','" + film.getGenere() + "','"
                + film.getDurata() + "','" + film.getDescrizione() + "','" + film.getLink_youtube() + "','"
                + film.getLink_copertina() + "')";

        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    public boolean deleteFilm(int id_film) {//G
        String query = "DELETE FROM Film WHERE Film.id_film =" + id_film;
        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean writeConfig(Config config) {//G

        String query = "INSERT INTO Config(prezzo_vip,sconto,glasses_price,over_price,disabled_price,offset_time,booking_validation_time) VALUES("
                + "'" + config.getPrezzo_vip() + "','" + config.getSconto() + "','"
                + config.getGlasses_price() + "','" + config.getOver_price() + "','" + config.getDisabled_price() + "','"
                + config.getOffset_time() + "','" + config.getBooking_validation_time() + "')";
        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean writeHall(Room sala) {//G
        ResultSet risultato_query;

        String query = "INSERT INTO Sala(rows,columns) VALUES("
                + "'" + sala.getRows() + "','" + sala.getColumns() + "')";
        try {
            SQL.eseguiQueryScrittura(query);
            query = "SELECT Sala.id_sala FROM Sala ORDER BY Sala.id_sala DESC LIMIT 1";
            risultato_query = SQL.eseguiQueryLettura(query);
            while (risultato_query.next()) {
                sala.setId_sala(risultato_query.getInt("id_sala"));
            }

            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean writeSeats(Room sala) {//G
        ArrayList<Seat> posti = sala.getSeats();
        boolean complete = false;
        for (Seat s : posti) {
            try {
                String query = "INSERT INTO Seats(id_sala,x,y,tipo) VALUES("
                        + "'" + sala.getId_sala() + "','" + s.getx() + "','" + s.gety() + "','"
                        + s.giveType() + "')";
                SQL.eseguiQueryScrittura(query);
                complete = true;
            } catch (SQLException ex) {
                complete = false;
            }
        }
        return complete;

    }

    public ArrayList<Screening> visualizzaStatoSale() throws SQLException {//G
        String query;
        ResultSet risultato_query;
        ArrayList<Screening> proiezioni;
        query = "SELECT Proiezione.*\n"
                + "FROM Proiezione, Film\n"
                + "WHERE (Proiezione.id_film=Film.id_film)\n"
                + "AND TIMESTAMPDIFF(MINUTE, Proiezione.data_ora + INTERVAL Film.durata MINUTE ,NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)<0\n"
                + "AND TIMESTAMPDIFF(MINUTE, Proiezione.data_ora + INTERVAL Film.durata MINUTE ,NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)>-Film.durata\n"
                + "";
        risultato_query = SQL.eseguiQueryLettura(query);
        proiezioni = parser.Screening(risultato_query);
        risultato_query.close();
        return proiezioni;
    }

    public ArrayList<Booking> showBooking() throws SQLException {//G

        String query;
        ResultSet risultato_query;
        ArrayList<Booking> booking;

        query = "SELECT * FROM Booking ORDER BY date_time DESC";
        risultato_query = SQL.eseguiQueryLettura(query);

        booking = parser.Booking(risultato_query);
        risultato_query.close();

        return booking;

    }

    public ArrayList<Screening> viewShows(int id_sala) throws SQLException {//G
        String query;
        ResultSet risultato_query;
        ArrayList<Screening> Proiezioni;
        query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) = DATE( NOW( ) ) AND Proiezione.id_sala =" + id_sala;
        risultato_query = SQL.eseguiQueryLettura(query);

        Proiezioni = parser.Screening(risultato_query);
        risultato_query.close();

        return Proiezioni;
    }

    public ArrayList<Booking> salesVolumeSearch(String a, String b) throws SQLException {//G
        ArrayList<Booking> booking;
        ResultSet risultato_query;
        String query = "SELECT id_booking, id_proiezione, date_time, number_of_glasses, price\n"
                + "FROM `Booking`\n"
                + "WHERE booking_status =1\n"
                + "AND date_time >= '" + a + "'\n"
                + "AND date_time <= '" + b + "'";
        risultato_query = SQL.eseguiQueryLettura(query);
        booking = parser.Booking(risultato_query);
        risultato_query.close();
        return booking;
    }

    public boolean bookingCleaner() {//G
        //Lettura di prenotazioni scadute (5 min), eliminazioni prenotazioni scadute sia booking che bookingseat
        //Per la relazione di  tipo RESTRICT elimino sia il posto/i che la prenotazione
        try {
            String Query = "delete From Booked_Seat\n"
                    + "where Booked_Seat.id_booking in (select * from (\n"
                    + "select Booking.id_booking\n"
                    + "from Booking,Config\n"
                    + "where Booking.booking_status = 0 and \n"
                    + "TIMESTAMPDIFF(MINUTE, Booking.date_time ,NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)> Config.booking_validation_time\n"
                    + ") as Temp_table)";
            SQL.eseguiQueryScrittura(Query);
            Query = "DELETE FROM Booking\n"
                    + "where Booking.id_booking in (select * from (\n"
                    + "select Booking.id_booking\n"
                    + "from Booking,Config\n"
                    + "where Booking.booking_status = 0 and \n"
                    + "TIMESTAMPDIFF(MINUTE, Booking.date_time ,NOW() + INTERVAL " + TIME_ZONE_COMPENSATION + " HOUR)> Config.booking_validation_time\n"
                    + ") as Temp_table)";
            SQL.eseguiQueryScrittura(Query);

        } catch (SQLException ex) {
            return false;
        }

        return true;
    }

}
