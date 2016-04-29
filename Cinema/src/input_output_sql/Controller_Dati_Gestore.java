/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output_sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class Controller_Dati_Gestore {

    SQLConnessione SQL;
    Parse_OBJ parser;

    public Controller_Dati_Gestore() {
        SQL = new SQLConnessione();
        SQL.creaConnessione();
        parser = new Parse_OBJ();
    }

    public ArrayList<Proiezione> visualizzaProiezione(int tipo) throws SQLException {
        //TIPO = 0 //Odierne
        //TIPO = 1 //Future
        String query;
        ResultSet risultato_query;
        ArrayList<Proiezione> Proiezioni;

        if (tipo == 0) {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) = DATE( NOW( ) )";
            risultato_query = SQL.eseguiQuery(query);
        } else if (tipo == 1) {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) )";
            risultato_query = SQL.eseguiQuery(query);
        } else {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) ) OR DATE( Proiezione.data_ora ) = DATE( NOW( ) )";
            risultato_query = SQL.eseguiQuery(query);
        }

        Proiezioni = parser.Proiezione(risultato_query);
        risultato_query.close();

        return Proiezioni;
    }

    public ArrayList<Sala> visualizzaSale() throws SQLException {
        String query;
        ResultSet risultato_query;
        ArrayList<Sala> Sale;

        query = "SELECT * FROM `Sala`";
        risultato_query = SQL.eseguiQuery(query);

        Sale = parser.Sala(risultato_query);
        risultato_query.close();

        return Sale;
    }

    public ArrayList<Film> visualizzaFilm(int quantita_max_da_visualizzare) throws SQLException {
        String query;
        ResultSet risultato_query;
        ArrayList<Film> Films;

        if (quantita_max_da_visualizzare == 0) {
            query = "SELECT * FROM  `Film`ORDER BY Film.data_ora DESC LIMIT 0 , 30";

            risultato_query = SQL.eseguiQuery(query);
        } else {

            query = "SELECT * FROM `Film` ORDER BY Film.data_ora desc";

//            query = "SELECT * FROM `Film` ORDER BY data_ora desc LIMIT"+ quantita_max_da_visualizzare;
            risultato_query = SQL.eseguiQuery(query);
        }

        Films = parser.Film(risultato_query);
        risultato_query.close();

        return Films;
    }

    public void spegni() {
        SQL.chiudiConnessione();
    }
}
