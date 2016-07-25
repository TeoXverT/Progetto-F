package Gestore;

import input_output.Adapter_SQL;
import input_output.SQLConnessione;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import oggetti.*;

/**
 *
 * @author Yatin
 */
public class Controller_Gestore {

    private final Adapter_SQL adapter;

    //Dati per il Thread di pulitura bigletti non pagati
    final int PERIOD = 10000;

    public Controller_Gestore() {
        adapter = new Adapter_SQL();

        Timer timer = new Timer();
        timer.schedule(new DbCleanerThread(timer, adapter), 0, PERIOD);

    }

    public ArrayList<Booking> showBooking() throws SQLException {
        return adapter.showBooking();
    }

    public ArrayList<Screening> visualizzaProiezione(int tipo) throws SQLException {
        //TIPO = 0 //Odierne
        //TIPO = 1 //Future
        //TIPO = 2  //Odierne e Future
        ArrayList<Screening> Proiezioni = adapter.visualizzaProiezione(tipo);
        return Proiezioni;
    }

    public boolean scriviProiezione(Screening proiezione) throws SQLException {
        if (contolloDisponibilitaProiezione(proiezione)) { //Eventuale controllo sul valore dei campi di config
            return adapter.scriviProiezione(proiezione);
        } else {
            return false;
        }
    }

    public boolean eliminaProiezione(int id_proiezione) {
        return adapter.eliminaProiezione(id_proiezione);
    }

    public boolean contolloDisponibilitaProiezione(Screening proiezione) throws SQLException {
        return adapter.contolloProiezioni(proiezione);
    }

    public ArrayList<Film> visualizzaFilm(int quantita_max_da_visualizzare) throws SQLException {
        //quantita_max_da_visualizzare = 0 //NO LIMIT
        ArrayList<Film> Films = adapter.visualizzaFilm(quantita_max_da_visualizzare);
        return Films;
    }

    public boolean scriviFilm(Film film) {
        if ((!"".equals(film.getTitolo_film())) && (!"".equals(film.getDescrizione())) && (!"".equals(film.getLink_copertina())) & (!"".equals(film.getGenere())) & (film.getDurata() > 0)) {
            return adapter.scriviFilm(film);
        } else {
            return false;
        }
    }

    public boolean eliminaFilm(int id_film) {
        return adapter.eliminaFilm(id_film);
    }

    public boolean scriviConfig(Config config) {
        if (true) { //Eventuale controllo sul valore dei campi di config
            return adapter.scriviConfig(config);
        } else {
            return false;
        }
    }

    public Config visualizzaConfig() throws SQLException {
        Config config = adapter.visualizzaConfig();
        return config;
    }

    public ArrayList<Room> visualizzaSale() throws SQLException {
        ArrayList<Room> Sale = adapter.visualizzaSale();
        return Sale;
    }

    public boolean scriviHall(Room sala) {
        if (adapter.writeHall(sala) == true) {
            if (adapter.writeSeats(sala) == true) {
                return true;
            }
        }
        return false;
    }

    public boolean eliminaSale(int id_sala) {
        return adapter.eliminaSale(id_sala);
    }

    public ArrayList<Screening> visualizzaStatoSale() throws SQLException {
        ArrayList<Screening> Sale = adapter.visualizzaStatoSale();
        return Sale;
    }

    public ArrayList<Screening> vieShows(int id_sala) throws SQLException {
        ArrayList<Screening> Sale = adapter.viewShows(id_sala);
        return Sale;
    }

    public ArrayList<Booking> salesVolume(String a, String b) throws SQLException {
        ArrayList<Booking> books = adapter.salesVolumeSearch(a, b);
        return books;
    }

}
