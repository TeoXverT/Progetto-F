/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputoutput;

import obj.Seat;
import obj.Config;
import obj.Film;
import obj.Projection;
import obj.Hall;
import obj.Booking;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Yoga
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
            config = new Config(result.getDouble("prezzo_vip"), result.getDouble("glasses_price"), result.getDouble("disabled_price"), result.getInt("offset_time"), result.getInt("booking_validation_time"));
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
