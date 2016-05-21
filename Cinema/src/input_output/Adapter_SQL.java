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
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) = DATE( NOW( ) ) ORDER BY Proiezione.data_ora DESC";
            risultato_query = SQL.eseguiQueryLettura(query);
        } else if (tipo == 1) {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) ) ORDER BY Proiezione.data_ora DESC";
            risultato_query = SQL.eseguiQueryLettura(query);
        } else {
            query = "SELECT * FROM  `Proiezione` WHERE DATE( Proiezione.data_ora ) > DATE( NOW( ) ) OR DATE( Proiezione.data_ora ) = DATE( NOW( ) ) ORDER BY Proiezione.data_ora DESC";
            risultato_query = SQL.eseguiQueryLettura(query);
        }

        Proiezioni = parser.Proiezione(risultato_query);
        risultato_query.close();

        return Proiezioni;
    }

    public boolean scriviProiezione(Proiezione proiezione) {
        String query = "INSERT INTO Proiezione(id_proiezione,data_ora, id_film, id_sala, projection_type, prezzo) VALUES (NULL," + "'" + proiezione.getData_ora_sql() + "','" + proiezione.getId_film() + "','" + proiezione.getId_sala() + "','" + proiezione.getTipo_proiezione() + "','" + proiezione.getPrezzo() + "');";
       System.out.println(query);
        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean contolloProiezioni(Proiezione proiezione) {
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
                + "					WHERE  Film.id_film = '" + proiezione.getId_film() + "' \n"
                + "					) as time_interval\n"
                + "where Proiezione.id_sala='" + proiezione.getId_sala() + "' AND datediff(Proiezione.data_ora,'" + proiezione.getData_ora_sql() + "')=0 AND Proiezione.id_film=Film.id_film AND(\n"
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
               System.out.println(query);

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

    public boolean eliminaProiezione(int id_proiezione) {
        String query = "DELETE FROM Proiezione WHERE Proiezione.id_proiezione =" + id_proiezione;
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

    public boolean eliminaSale(int id_sale) {
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

    public ArrayList<Film> visualizzaFilm(int quantita_max_da_visualizzare) throws SQLException {
        String query;
        ResultSet risultato_query;
        ArrayList<Film> Films;

        if (quantita_max_da_visualizzare == 0) {
            query = "SELECT * FROM  `Film` WHERE Film.titolo IS NOT NULL AND Film.titolo != '' ORDER BY Film.data_ora DESC";

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

    public boolean eliminaFilm(int id_film) {
        String query = "DELETE FROM Film WHERE Film.id_film =" + id_film;
        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
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

    public ArrayList<Film> FilmFuturo(int deltaData) throws SQLException {

        ArrayList<Film> Films;
        ResultSet risultatoQuery;

//        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String strDate1 = sdfDate.format(Data_ora_fine.getTime());
//        String strDate2 = sdfDate.format(Data_ora_inizio.getTime());
        String query = " SELECT DISTINCT   Film.id_film,    Film.titolo,    Film.descrizione,    Film.data_ora,   Film.durata,    Film.genere,    Film.link_copertina, Film.link_youtube "
                + "     FROM Film, Proiezione "
                + "     WHERE Film.id_film = Proiezione.id_film AND DATEDIFF(Proiezione.data_ora, (NOW() + INTERVAL 120 MINUTE)) = " + deltaData + " AND TIMESTAMPDIFF(MINUTE,  NOW(),  Proiezione.data_ora)>0 "
                + "     ";
        /* Qui mettere la data e ora dopo la quale visaulizzare i film*/
        /*Se oggi mettere 0, altrimenti per domani metti 1 ecc...*/

        risultatoQuery = SQL.eseguiQueryLettura(query);
        Films = parser.Film(risultatoQuery);

        return Films;

    }

    public ArrayList<Film> FilmFuturoBySlider(int deltaData, int sliderValue) throws SQLException {

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
            System.out.println("ciao");
            query = "SELECT DISTINCT f.id_film, f.titolo, f.descrizione, f.data_ora, f.durata, f.genere, f.link_copertina, f.link_youtube "
                    + "FROM Proiezione p, Film f "
                    + "WHERE f.id_film = p.id_film AND DATEDIFF(p.data_ora, NOW() + INTERVAL 135 MINUTE) = " + deltaData + " AND p.data_ora > (concat(date(now()+ INTERVAL  " + deltaData + " DAY + INTERVAL 135 MINUTE), ' " + sliderValue + ":00:00')) AND p.data_ora > (NOW() +INTERVAL 135 MINUTE)";

        } else {
            query = "SELECT DISTINCT f.id_film, f.titolo, f.descrizione, f.data_ora, f.durata, f.genere, f.link_copertina, f.link_youtube "
                    + "FROM Proiezione p, Film f "
                    + "WHERE f.id_film = p.id_film AND DATEDIFF(p.data_ora, NOW() + INTERVAL 135 MINUTE) = " + deltaData + " AND p.data_ora > (concat(date(now()+ INTERVAL  " + deltaData + " DAY + INTERVAL 135 MINUTE), ' " + sliderValue + ":00:00'))";
        }

        risultatoQuery = SQL.eseguiQueryLettura(query);
        Films = parser.Film(risultatoQuery);

        return Films;

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

        String query = "INSERT INTO Config(prezzo_vip,sconto,glasses_price,over_price,disabled_price,offset_time) VALUES("
                + "'" + config.getPrezzo_vip() + "','" + config.getSconto() + "','"
                + config.getGlasses_price()+ "','" + config.getOver_price() + "','" + config.getDisabled_price() + "','"
                + config.getOffset_time() +"')";
        try {
            SQL.eseguiQueryScrittura(query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean scriviFilm(Film film) {

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

    public boolean writeHall(Sala sala) {
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

    public boolean writeSeats(Sala sala) {
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

    public ArrayList<Proiezione> getShowByFilm(int id_film, int deltaData, int ora) throws SQLException {
        String query;
        ResultSet risultato_query;
        ArrayList<Proiezione> proiezione;

        query = "SELECT Proiezione.* "
                + "FROM Proiezione "
                + "WHERE (Proiezione.id_film=" + id_film + ") AND (concat(date(now()+ INTERVAL " + deltaData + " DAY), ' 00:00:00')=concat(date(Proiezione.data_ora), ' 00:00:00')) and (Proiezione.data_ora>concat(date(now()+ INTERVAL " + deltaData + " DAY), ' " + ora + ":00:00' ))";

        risultato_query = SQL.eseguiQueryLettura(query);

        proiezione = parser.Proiezione(risultato_query);
        risultato_query.close();

        return proiezione;
    }

    public Sala getSalaByIdSala(int id_sala) throws SQLException {
        String query;
        ResultSet risultato_query;
        Sala sala;

        query = "SELECT * FROM Sala WHERE id_sala = '" + id_sala + "'";

        risultato_query = SQL.eseguiQueryLettura(query);
        sala = parser.getSalaById(risultato_query);
        risultato_query.close();

        return sala;
    }

    public ArrayList<Seat> getSeats(int id_sala) {          // da finire...sono arrivato a leggere da DB ora devo parsare le cose..
        String query;
        ResultSet risultato_query;
        ArrayList<Seat> seats = new ArrayList<>();
        ImageIcon seat_free = new ImageIcon("immagini/poltrone/seat_free.png");
        ImageIcon seat_disable = new ImageIcon("immagini/poltrone/seat_diasable.png");
        ImageIcon seat_vip = new ImageIcon("immagini/poltrone/seat_vip.png");
        ImageIcon seat_handicap = new ImageIcon("immagini/poltrone/seat_handicap.png");

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
                }
                seats.get(i).setId_seat(risultato_query.getInt("Id_seat"));
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Adapter_SQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return seats;

    }
    
    public ArrayList<Proiezione> visualizzaStatoSale() throws SQLException {
        String query;
        ResultSet risultato_query;
        ArrayList<Proiezione> proiezioni;
        query = "SELECT Proiezione.*\n"
                + "FROM Proiezione, Film\n"
                + "WHERE (Proiezione.id_film=Film.id_film)\n"
                + "AND TIMESTAMPDIFF(MINUTE, Proiezione.data_ora + INTERVAL Film.durata MINUTE ,NOW() + INTERVAL 2 HOUR)<0\n"
                + "AND TIMESTAMPDIFF(MINUTE, Proiezione.data_ora + INTERVAL Film.durata MINUTE ,NOW() + INTERVAL 2 HOUR)>-Film.durata\n"
                + "";
        risultato_query = SQL.eseguiQueryLettura(query);
        proiezioni = parser.Proiezione(risultato_query);
        risultato_query.close();
        return proiezioni;
    }

    public void spegni() {
        SQL.chiudiConnessione();
    }
}
