/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputoutput;

import obj.Config;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import obj.Seat;

/**
 *
 * @author Yoga
 */
public class AdapterSQL {

    protected final SQLConnection SQL;
    protected final ParserObj parser;

    protected final int TIME_LIMIT_ONLINE_PURCHASE = 15; // Min - è possibile comprare il bigletto online fino a tot min prima del inizio
    protected final int TIME_ZONE_COMPENSATION = 2; // Ore      - lo si utilizza per adeguarsi al fuso orario del DB

    public AdapterSQL() {
        SQL = new SQLConnection();
        SQL.connect();
        parser = new ParserObj();
    }

//    public ArrayList<Film> visualizzaFilmFiltratiRispettoOraEData(Calendar Data_ora_inizio, Calendar Data_ora_fine) throws SQLException {
//        
//        ArrayList<Film> listaFilmFiltrati;
//        ResultSet risultatoQuery;
//        
//        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String strDate1 = sdfDate.format(Data_ora_fine.getTime());
//        String strDate2 = sdfDate.format(Data_ora_inizio.getTime());
//
//        //TEMPORANEO TEST
//        //String query = "SELECT Film.id_film, Film.titolo, Film.genere, Film.durata, Film.descrizione, Film.link_youtube, Film.link_copertina FROM  Proiezione LEFT JOIN Film ON Proiezione.id_film = Film.id_film  WHERE ( DATE( Proiezione.data_ora ) > DATE( '" + strDate2 + "' ) AND DATE( Proiezione.data_ora ) < DATE( '" + strDate1 + "' ))";
//        String query = "SELECT DISTINCT f.id_film, f.titolo, f.genere, f.durata, f.descrizione, f.link_youtube, f.link_copertina FROM  Proiezione p LEFT JOIN Film f ON p.id_film = f.id_film  WHERE (  p.data_ora  > '" + strDate2 + "'  AND p.data_ora  < '" + strDate1 + "' )";
//        
//        risultatoQuery = SQL.eseguiQueryLettura(query);
//        listaFilmFiltrati = parser.Film(risultatoQuery);
//        
//        return listaFilmFiltrati;
//    }
    public Config getConfig() throws SQLException {//PG
        String query;
        ResultSet result;
        Config config;

        query = "SELECT * FROM Config ORDER BY id_config DESC LIMIT 1";
        result = SQL.readingQuery(query);

        config = parser.config(result);
        result.close();

        return config;
    }
    
     public ArrayList<Seat> getSeatByIdHall(int idHall) {
        String query;
        ResultSet result;
        ArrayList<Seat> seats = new ArrayList<>();
        ImageIcon seat_free = new ImageIcon("images/hall/seat_free.png");
        ImageIcon seat_disable = new ImageIcon("images/hall/seat_disable.png");
        ImageIcon seat_vip = new ImageIcon("images/hall/seat_vip.png");
        ImageIcon seat_handicap = new ImageIcon("images/hall/seat_handicap.png");
        ImageIcon seat_taken = new ImageIcon("images/hall/seat_taken.png");

        query = "SELECT *  FROM Seats WHERE id_sala = '" + idHall + "'";
        try {
            result = SQL.readingQuery(query);
            int i = 0;
            while (result.next()) {
                seats.add(new Seat(result.getInt("x"), result.getInt("y")));
                switch (result.getInt("tipo")) {
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
                    case 5:
                        seats.get(i).setIcon(seat_taken);
                        seats.get(i).setOccupato(true);
                        break;
                }
                seats.get(i).setId_seat(result.getInt("Id_seat"));
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdapterSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return seats;

    }

    public void close() {
        SQL.closeConnection();
    }

}
