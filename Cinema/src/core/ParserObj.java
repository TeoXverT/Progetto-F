package core;

import core.obj.Seat;
import core.obj.Config;
import core.obj.Film;
import core.obj.Projection;
import core.obj.Hall;
import core.obj.Booking;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Questa calsse è una classe di supporto per gli adapter e i suoi metodi permettono di trasformare i result set in effettivi oggetti utlizzabili in Java
 * 
 */
public class ParserObj {

    public ParserObj() {
    }

    public ArrayList<Hall> hall(ResultSet result) throws SQLException {
        ArrayList<Hall> hall = new ArrayList<>();
        while (result.next()) {
            hall.add(new Hall(result.getInt("id_sala"), result.getInt("rows"), result.getInt("columns")));
        }
        result.close();

        return hall;
    }

    public ArrayList<Projection> projection(ResultSet result) throws SQLException {
        ArrayList<Projection> projection = new ArrayList<>();

        while (result.next()) {
            projection.add(new Projection(result.getInt("id_proiezione"), parseDateTime(result.getTimestamp("data_ora")), new Film(result.getInt("id_film")), new Hall(result.getInt("id_sala")), result.getInt("projection_type"), result.getDouble("prezzo")));
        }
        result.close();

        return projection;
    }

    public ArrayList<Film> film(ResultSet result) throws SQLException {
        ArrayList<Film> film = new ArrayList<>();

        while (result.next()) {
            film.add(new Film(result.getInt("id_film"), result.getString("titolo"), result.getString("genere"), result.getInt("durata"), result.getString("descrizione"), result.getString("link_youtube"), result.getString("link_copertina"), parseDateTime(result.getTimestamp("data_ora"))));
        }
        result.close();
        return film;
    }

    public Config config(ResultSet result) throws SQLException {
        Config config = null;

        while (result.next()) {//Lo farà una sola volta
            config = new Config(result.getDouble("prezzo_vip"), result.getDouble("glasses_price"), result.getDouble("disabled_price"), result.getInt("offset_time"), result.getInt("booking_validation_time"), result.getString("ticket_validation_ip"));
        }
        result.close();

        return config;
    }

    public ArrayList<Booking> booking(ResultSet result) throws SQLException {
        ArrayList<Booking> booking = new ArrayList<>();

        while (result.next()) {
            booking.add(new Booking(result.getInt("id_booking"), new Projection(result.getInt("id_proiezione")), null, parseDateTime(result.getTimestamp("date_time")),
                    result.getInt("number_of_glasses"), result.getDouble("price"), result.getInt("booking_status"), result.getString("email")));
        }
        result.close();

        return booking;
    }

    public ArrayList<Seat> seat(ResultSet result) throws SQLException {
        ArrayList<Seat> seat = new ArrayList<>();

        while (result.next()) {
            seat.add(new Seat(result.getInt("id_seat"), result.getInt("x"), result.getInt("y"), result.getInt("tipo"), result.getInt("id_sala")));

        }
        result.close();
        return seat;
    }
    
    

    ////////////////////////////////////////////////// COMMON USE STUFF ///////////////////////////////////
    private Calendar parseDateTime(Timestamp timestamp) {
        java.util.Date date = null;
        if (timestamp != null) {
            date = new java.util.Date(timestamp.getTime());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
