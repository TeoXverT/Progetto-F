/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class Parse_OBJ {

    public Parse_OBJ() {
    }

    public ArrayList<Room> Room(ResultSet risultato_query) throws SQLException {
        ArrayList<Room> Sale = new ArrayList<>();
        while (risultato_query.next()) {
            Sale.add(new Room(risultato_query.getInt("id_sala"), risultato_query.getInt("rows"), risultato_query.getInt("columns")));
        }
        risultato_query.close();

        return Sale;
    }

    public ArrayList<Screening> Screening(ResultSet risultato_query) throws SQLException {
        ArrayList<Screening> screening = new ArrayList<>();

        while (risultato_query.next()) {
            screening.add(new Screening(risultato_query.getInt("id_proiezione"), parseData_ora(risultato_query.getTimestamp("data_ora")), new Film(risultato_query.getInt("id_film")), new Room(risultato_query.getInt("id_sala")), risultato_query.getInt("projection_type"), risultato_query.getDouble("prezzo")));
        }
        risultato_query.close();

        return screening;
    }

    public ArrayList<Film> Film(ResultSet risultato_query) throws SQLException {
        ArrayList<Film> Films = new ArrayList<>();

        while (risultato_query.next()) {
            Films.add(new Film(risultato_query.getInt("id_film"), risultato_query.getString("titolo"), risultato_query.getString("genere"), risultato_query.getInt("durata"), risultato_query.getString("descrizione"), risultato_query.getString("link_youtube"), risultato_query.getString("link_copertina"), parseData_ora(risultato_query.getTimestamp("data_ora"))));
        }
        risultato_query.close();
        return Films;
    }

    public Config Config(ResultSet risultato_query) throws SQLException {
        Config config = null;

        while (risultato_query.next()) {//Lo farà una sola volta
            config = new Config(risultato_query.getDouble("prezzo_vip"), risultato_query.getDouble("sconto"), risultato_query.getDouble("glasses_price"), risultato_query.getDouble("over_price"),
                    risultato_query.getDouble("disabled_price"), risultato_query.getInt("offset_time"), risultato_query.getInt("booking_validation_time"));
        }
        risultato_query.close();

        return config;
    }

    public ArrayList<Booking> Booking(ResultSet risultato_query) throws SQLException {  //Non si scarica i posti
        ArrayList<Booking> booking = new ArrayList<>();

        while (risultato_query.next()) {
            booking.add(new Booking(risultato_query.getInt("id_booking"), new Screening(risultato_query.getInt("id_proiezione")), null, parseData_ora(risultato_query.getTimestamp("date_time")),
                    risultato_query.getInt("number_of_glasses"), risultato_query.getDouble("price"), risultato_query.getInt("booking_status"), risultato_query.getString("email")));
        }
        risultato_query.close();

        return booking;
    }

    public ArrayList<Seat> Seat(ResultSet risultato_query) throws SQLException {
        ArrayList<Seat> seat = new ArrayList<>();

        while (risultato_query.next()) {
            seat.add(new Seat(risultato_query.getInt("id_seat"), risultato_query.getInt("x"), risultato_query.getInt("y"), risultato_query.getInt("tipo"), risultato_query.getInt("id_sala")));

        }
        risultato_query.close();
        return seat;
    }
    ////////////////////////////////////////////////// METODI DI USO COMUNE ///////////////////////////////////
    private Calendar parseData_ora(Timestamp timestamp) {
        java.util.Date date = null;
        if (timestamp != null) {
            date = new java.util.Date(timestamp.getTime());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
