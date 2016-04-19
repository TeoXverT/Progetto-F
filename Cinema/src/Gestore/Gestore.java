package Gestore;

import input_output.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import oggetti.*;



public class Gestore {
    
    private Controller_Dati_Gestore controller = new Controller_Dati_Gestore();
    
    public Gestore() {}
        
        public boolean creaProiezione( String data_ora,int id_film, int id_sala, String  tipo_proiezione, int prezzo_normale, int prezzo_3d){ 
            Proiezione proiezione = new Proiezione(0, parseData_ora(data_ora),id_film, id_sala, tipo_proiezione, prezzo_normale, prezzo_3d);
            return controller.creaProiezione(proiezione);
        }
        
        
    private Calendar parseData_ora(String stringaCalendario) {
        int anno, mese, giorno, ora, min, sec;

        StringTokenizer st = new StringTokenizer(stringaCalendario);
        StringTokenizer rt = new StringTokenizer(st.nextToken(), "-");

        anno = Integer.parseInt(rt.nextToken());
        mese = Integer.parseInt(rt.nextToken());
        giorno = Integer.parseInt(rt.nextToken());

        rt = new StringTokenizer(st.nextToken(), ":");

        ora = Integer.parseInt(rt.nextToken());
        min = Integer.parseInt(rt.nextToken());
        sec = Integer.parseInt(rt.nextToken());
        
        Calendar fine = new GregorianCalendar(anno, mese, giorno, ora, min, sec);

        return fine;
    }
        
    public void aggiungiFilm(String titolo_film, String genere, int durata, String descrizione, String link_trailer){
        Film newFilm = new Film(0, titolo_film, genere, durata, descrizione, link_trailer);
    }
}
