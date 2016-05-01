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
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class Adapter_SQL {

    SQLConnessione SQL;
    Parse_OBJ parser;

    public Adapter_SQL() {
        SQL = new SQLConnessione();
        SQL.creaConnessione();
        parser = new Parse_OBJ();
    }

    public ArrayList<Proiezione> visualizzaProiezione(int tipo) throws SQLException {
        //TIPO = 0 //Odierne
        //TIPO = 1 //Future
        //TIPO = 3 //Sia odierne che future
        String query;
        ResultSet risultato_query;
        ArrayList<Proiezione> Proiezioni;

        if (tipo == 0) {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) = DATE( NOW( ) )";
            risultato_query = SQL.eseguiQueryLettura(query);
        } else if (tipo == 1) {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) )";
            risultato_query = SQL.eseguiQueryLettura(query);
        } else {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) ) OR DATE( Proiezione.data_ora ) = DATE( NOW( ) )";
            risultato_query = SQL.eseguiQueryLettura(query);
        }

        Proiezioni = parser.Proiezione(risultato_query);
        risultato_query.close();

        return Proiezioni;
    }

    public boolean scriviProiezione(Proiezione proiezione) {
        String query = "INSERT INTO Proiezione(id_proiezione,data_ora, id_film, id_sala, tipo, prezzo) VALUES (NULL," + "'" + proiezione.getData_ora_sql() + "','" + proiezione.getId_film() + "','" + proiezione.getId_sala() + "','" + proiezione.getTipo_proiezione() + "','" + proiezione.getPrezzo() + "');";       
        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public ArrayList<Sala> visualizzaSale() throws SQLException {
        String query;
        ResultSet risultato_query;
        ArrayList<Sala> Sale;

        query = "SELECT * FROM `Sala`";
        risultato_query = SQL.eseguiQueryLettura(query);

        Sale = parser.Sala(risultato_query);
        risultato_query.close();

        return Sale;
    }

    public ArrayList<Film> visualizzaFilm(int quantita_max_da_visualizzare) throws SQLException {
        String query;
        ResultSet risultato_query;
        ArrayList<Film> Films;

        if (quantita_max_da_visualizzare == 0) {
            query = "SELECT * FROM  `Film`ORDER BY Film.data_ora DESC";

            risultato_query = SQL.eseguiQueryLettura(query);
        } else {
//            query = "SELECT * FROM `Film` ORDER BY Film.data_ora desc";
            query = "SELECT * FROM `Film` ORDER BY data_ora desc LIMIT " + quantita_max_da_visualizzare;
            risultato_query = SQL.eseguiQueryLettura(query);
        }

        Films = parser.Film(risultato_query);
        risultato_query.close();

        return Films;
    }

    public ArrayList<Film> visualizzaFilmFiltratiRispettoOraEData(Calendar Data_ora_inizio, Calendar Data_ora_fine) throws SQLException {
        
        ArrayList<Film> listaFilmFiltrati;
        ResultSet risultatoQuery;

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate1 = sdfDate.format(Data_ora_fine.getTime());
        String strDate2 = sdfDate.format(Data_ora_inizio.getTime());

        
        //TEMPORANEO TEST
        //String query = "SELECT Film.id_film, Film.titolo, Film.genere, Film.durata, Film.descrizione, Film.link_youtube, Film.link_copertina FROM  Proiezione LEFT JOIN Film ON Proiezione.id_film = Film.id_film  WHERE ( DATE( Proiezione.data_ora ) > DATE( '" + strDate2 + "' ) AND DATE( Proiezione.data_ora ) < DATE( '" + strDate1 + "' ))";
        String query = "SELECT DISTINCT f.id_film, f.titolo, f.genere, f.durata, f.descrizione, f.link_youtube, f.link_copertina FROM  Proiezione p LEFT JOIN Film f ON p.id_film = f.id_film  WHERE (  p.data_ora  > '" + strDate2 + "'  AND p.data_ora  < '" + strDate1 + "' )";


        risultatoQuery = SQL.eseguiQueryLettura(query);
        listaFilmFiltrati = parser.Film(risultatoQuery);

        return listaFilmFiltrati;
    }

    public Config visualizzaConfig() throws SQLException {
        String query;
        ResultSet risultato_query;
        Config config;

        query = "SELECT * FROM Config ORDER BY id_config DESC LIMIT 1";
        risultato_query = SQL.eseguiQueryLettura(query);

        config = parser.Config(risultato_query);
        risultato_query.close();

        return config;
    }

    public boolean scriviConfig(Config config) {

        String query = "INSERT INTO Config(prezzo_vip,sconto,popcorn_s,popcorn_m,popcorn_l,bibita_s,bibita_m,bibita_l) VALUES("
                + "'" + config.getPrezzo_vip() + "','" + config.getSconto() + "','"
                + config.getPopcorn_s() + "','" + config.getPopcorn_m() + "','" + config.getPopcorn_l() + "','"
                + config.getBibita_s() + "','" + config.getBibita_m() + "','" + config.getBibita_l() + "')";

        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    public void spegni() {
        SQL.chiudiConnessione();
    }
}
