package Gestore;

import input_output.Adapter_SQL;
import input_output.SQLConnessione;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oggetti.*;

/**
 *
 * @author Yatin
 */
public class Controller_Gestore {

    private final Adapter_SQL adapter = new Adapter_SQL();

    public Controller_Gestore() {

    }

//        public boolean creaProiezione( String data_ora,int id_film, int id_sala, String  tipo_proiezione, int prezzo_normale, int prezzo_3d){ 
//            Proiezione proiezione = new Proiezione(0, parseData_ora(data_ora),id_film, id_sala, tipo_proiezione, prezzo_normale, prezzo_3d);
//            return controller.creaProiezione(proiezione);
//        }
//
//        public boolean modifica_config(double prezzo_vip, double sconto){
//            try {
//            Config config1 = new Config(prezzo_vip, sconto);
//            controller.modifica_config(config1);
//            }
//            catch(IOException e){
//                    System.out.println("Errore!!!");
//                    return false;
//                    }
//            return true;            
//        }
//        
//    private Calendar parseData_ora(String stringaCalendario) {
//        int anno, mese, giorno, ora, min, sec;
//
//        StringTokenizer st = new StringTokenizer(stringaCalendario);
//        StringTokenizer rt = new StringTokenizer(st.nextToken(), "-");
//
//        anno = Integer.parseInt(rt.nextToken());
//        mese = Integer.parseInt(rt.nextToken());
//        giorno = Integer.parseInt(rt.nextToken());
//
//        rt = new StringTokenizer(st.nextToken(), ":");
//
//        ora = Integer.parseInt(rt.nextToken());
//        min = Integer.parseInt(rt.nextToken());
//        sec = Integer.parseInt(rt.nextToken());
//        
//        Calendar fine = new GregorianCalendar(anno, mese, giorno, ora, min, sec);
// 
//        return fine;
//    }
//        
//    public boolean aggiungiFilm(String titolo_film, String genere, int durata, String descrizione, String link_trailer) throws IOException{
//        Film newFilm = new Film(0, titolo_film, genere, durata, descrizione, link_trailer);
//        if (newFilm.getDurata() < 300) {
//            boolean adding = controller.controllerFilm(newFilm);
//            return adding;
//        } else {
//            return false;
//        }
//    }
    public ArrayList<Proiezione> visualizzaProiezione(int tipo) throws SQLException {
        //TIPO = 0 //Odierne
        //TIPO = 1 //Future
        //TIPO = 2  //Odierne e Future
        ArrayList<Proiezione> Proiezioni = adapter.visualizzaProiezione(tipo);
        return Proiezioni;
    }

    public boolean scriviProiezione(Proiezione proiezione) throws SQLException {
        if (contolloDisponibilitaProiezione(proiezione)) { //Eventuale controllo sul valore dei campi di config
            return adapter.scriviProiezione(proiezione);
        } else {
            return false;
        }
    }

    public boolean eliminaProiezione(int id_proiezione) {
        return adapter.eliminaProiezione(id_proiezione);
    }

    public boolean contolloDisponibilitaProiezione(Proiezione proiezione) throws SQLException {
      return adapter.contolloProiezioni(proiezione);
//        ArrayList<Proiezione> Proiezioni = visualizzaProiezione(2);
//        for (Proiezione p : Proiezioni) {
//            if (p.getId_sala() == proiezione.getId_sala() && TimeUnit.MINUTES.convert(Math.abs(p.getData_ora().getTime().getTime() - proiezione.getData_ora().getTime().getTime()), TimeUnit.MILLISECONDS) < 200) {
//                System.out.println("Distanza in minuti dalla occupazione della stessa sala più vicina: " + TimeUnit.MINUTES.convert(Math.abs(p.getData_ora().getTime().getTime() - proiezione.getData_ora().getTime().getTime()), TimeUnit.MILLISECONDS) + " min");
//                return false;
//            }
//        }
//        return true;
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

    public Config aggiornaConfig() throws SQLException { //Temporaneo
        SQLConnessione db = new SQLConnessione();
        Config c = null;
        db.creaConnessione();

        String qry = "SELECT * FROM Config ORDER BY id_config DESC LIMIT 1";
        ResultSet rs = db.eseguiQueryLettura(qry);
        while (rs.next()) {
            c = new Config(rs.getDouble("prezzo_vip"), rs.getDouble("sconto"), rs.getDouble("popcorn_s"), rs.getDouble("popcorn_m"),
                    rs.getDouble("popcorn_l"), rs.getDouble("bibita_s"), rs.getDouble("bibita_m"), rs.getDouble("bibita_l"));
        }
        db.chiudiConnessione();
        return c;
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

    public ArrayList<Sala> visualizzaSale() throws SQLException {
        ArrayList<Sala> Sale = adapter.visualizzaSale();
        return Sale;
    }

    public boolean scriviHall(Sala sala) {
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
    
    public ArrayList<Proiezione> visualizzaStatoSale() throws SQLException {
        ArrayList<Proiezione> Sale = adapter.visualizzaStatoSale();
        return Sale;
    }
}
