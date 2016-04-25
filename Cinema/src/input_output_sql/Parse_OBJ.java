/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output_sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class Parse_OBJ {

    public Parse_OBJ() {
    }

    public ArrayList<Proiezione> Proiezione(ResultSet risultato_query) throws SQLException {
        ArrayList<Proiezione> Proiezioni = new ArrayList<>();

        while (risultato_query.next()) {
//          System.out.println(risultato_query.getString("id_proiezione") + " " + risultato_query.getString("data_ora") + " ppp " + risultato_query.getString("id_film") + " " + risultato_query.getString("id_sala") + " " + risultato_query.getString("tipo") + " " + risultato_query.getString("prezzo"));
            Proiezioni.add(new Proiezione(risultato_query.getInt("id_proiezione"), parseData_ora(risultato_query.getTimestamp("data_ora")), risultato_query.getInt("id_film"), risultato_query.getInt("id_sala"), risultato_query.getString("tipo"), risultato_query.getDouble("prezzo")));
        }
        risultato_query.close();

        return Proiezioni;
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
