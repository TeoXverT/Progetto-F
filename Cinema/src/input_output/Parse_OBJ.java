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
import java.util.StringTokenizer;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class Parse_OBJ {

    public Parse_OBJ() {
    }

    public ArrayList<Sala> Sala(ResultSet risultato_query) throws SQLException {
        ArrayList<Sala> Sale = new ArrayList<>();
        while (risultato_query.next()) {
            Sale.add(new Sala(risultato_query.getInt("id_sala"), risultato_query.getInt("posti_x"), risultato_query.getInt("posti_y"), parsePosti(risultato_query.getString("posti_vip"))));
        }
        risultato_query.close();

        return Sale;
    }
    
    
    public Sala getSalaById(ResultSet risultato_query) throws SQLException {
        
        Sala sala = new Sala(risultato_query.getInt("id_sala"), risultato_query.getInt("posti_x"), risultato_query.getInt("posti_y"), parsePosti(risultato_query.getString("posti_vip")));
        
        risultato_query.close();
        return sala;
    }
    
    
    public ArrayList<Proiezione> Proiezione(ResultSet risultato_query) throws SQLException {
        ArrayList<Proiezione> Proiezioni = new ArrayList<>();

        while (risultato_query.next()) {
            Proiezioni.add(new Proiezione(risultato_query.getInt("id_proiezione"), parseData_ora(risultato_query.getTimestamp("data_ora")), risultato_query.getInt("id_film"), risultato_query.getInt("id_sala"), risultato_query.getString("tipo"), risultato_query.getDouble("prezzo")));
        }
        risultato_query.close();

        return Proiezioni;
    }

    public ArrayList<Film> Film(ResultSet risultato_query) throws SQLException {
        ArrayList<Film> Films = new ArrayList<>();

        while (risultato_query.next()) {
            Films.add(new Film(risultato_query.getInt("id_film"), risultato_query.getString("titolo"), risultato_query.getString("genere"), risultato_query.getInt("durata"), risultato_query.getString("descrizione"), risultato_query.getString("link_youtube"), risultato_query.getString("link_copertina"),parseData_ora(risultato_query.getTimestamp("data_ora"))));
        }
        risultato_query.close();

        return Films;
    }

    public Config Config(ResultSet risultato_query) throws SQLException {
        Config config = null;

        while (risultato_query.next()) {//Lo farà una sola volta
            config = new Config(risultato_query.getDouble("prezzo_vip"), risultato_query.getDouble("sconto"), risultato_query.getDouble("popcorn_s"), risultato_query.getDouble("popcorn_m"),
                    risultato_query.getDouble("popcorn_l"), risultato_query.getDouble("bibita_s"), risultato_query.getDouble("bibita_m"), risultato_query.getDouble("bibita_l"));
        }
        risultato_query.close();

        return config;
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

    private ArrayList<Seat> parsePosti(String stringPosti) {
        ArrayList<Seat> posti = new ArrayList<>();

        if (!stringPosti.equals("0")) {
            StringTokenizer st = new StringTokenizer(stringPosti, ",");
            StringTokenizer rt;
            while (st.hasMoreTokens()) {
                rt = new StringTokenizer(st.nextToken(), ":");
                posti.add(new Seat(Integer.parseInt(rt.nextToken()), Integer.parseInt(rt.nextToken())));
            }
        }

        return posti;
    }

}
