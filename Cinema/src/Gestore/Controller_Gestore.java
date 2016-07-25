package Gestore;

import input_output.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import oggetti.*;

/**
 *
 * @author Yatin
 */
public class Controller_Gestore {

    private final Adapter_SQL_Gestore adapter;

    //Dati per il Thread di pulitura bigletti non pagati
    private final int PERIOD = 10000;

    public Controller_Gestore() {
        adapter = new Adapter_SQL_Gestore();

//        Timer timer = new Timer();
//        timer.schedule(new DbCleanerThread(timer, adapter), 0, PERIOD);
    }

    public ArrayList<Booking> getBooking() throws SQLException {
        return adapter.getBooking();
    }

    public ArrayList<Screening> getScreening(int tipo) throws SQLException {
        //TIPO = 0 //Odierne
        //TIPO = 1 //Future
        //TIPO = 2  //Odierne e Future
        ArrayList<Screening> Proiezioni = adapter.getScreening(tipo);
        return Proiezioni;
    }

    public boolean writeScreening(Screening proiezione) throws SQLException {
        if (adapter.checkScreening(proiezione)) { //Eventuale controllo sul valore dei campi di config
            return adapter.writeScrinning(proiezione);
        } else {
            return false;
        }
    }

    public boolean deleteScreening(int id_proiezione) {
        return adapter.deleteScreening(id_proiezione);
    }

    public ArrayList<Film> getFilm(int quantita_max_da_visualizzare) throws SQLException {
        //quantita_max_da_visualizzare = 0 //NO LIMIT
        ArrayList<Film> Films = adapter.getFilm(quantita_max_da_visualizzare);
        return Films;
    }

    public boolean writeFilm(Film film) {
        if ((!"".equals(film.getTitolo_film())) && (!"".equals(film.getDescrizione())) && (!"".equals(film.getLink_copertina())) & (!"".equals(film.getGenere())) & (film.getDurata() > 0)) {
            System.out.println("hoal");

            return adapter.writeFilm(film);
        } else {
            return false;
        }
    }

    public boolean deleteFilm(int id_film) {
        return adapter.deleteFilm(id_film);
    }

    public boolean writeConfig(Config config) {
        if (true) { //Eventuale controllo sul valore dei campi di config
            return adapter.writeConfig(config);
        } else {
            return false;
        }
    }

    public Config getConfig() throws SQLException {
        Config config = adapter.getConfig();
        return config;
    }

    public ArrayList<Room> getRoom() throws SQLException {
        ArrayList<Room> Sale = adapter.getRoom();
        return Sale;
    }

    public boolean writeRoom(Room sala) {
        if (adapter.writeHall(sala) == true) {
            if (adapter.writeSeats(sala) == true) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteRoom(int id_sala) {
        return adapter.deleteRoom(id_sala);
    }

    public ArrayList<Screening> getCurrentScreening() throws SQLException {
        ArrayList<Screening> Sale = adapter.getCurrentScreening();
        return Sale;
    }

    public ArrayList<Screening> getScreeningByRoom(int id_sala) throws SQLException {
        ArrayList<Screening> Sale = adapter.getScreeningByRoom(id_sala);
        return Sale;
    }

    public ArrayList<Booking> salesVolume(String a, String b) throws SQLException {
        ArrayList<Booking> books = adapter.salesVolumeSearch(a, b);
        return books;
    }

}
