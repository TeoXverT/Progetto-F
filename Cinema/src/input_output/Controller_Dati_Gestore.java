package input_output;

import java.io.IOException;
import java.util.ArrayList;
import oggetti.*;

public class Controller_Dati_Gestore {

    private Input input;
    private Output output;
    ArrayList<Film> listaFilm = new ArrayList<>();
    ArrayList<Proiezione> listaProiezione = new ArrayList<>();
    ArrayList<Prenotazione> listaPrenotazione = new ArrayList<>();



    public Controller_Dati_Gestore() {
        this.input = new Input("file_db/config.txt", "file_db/film.txt", "file_db/sala.txt", "file_db/proiezione.txt", "file_db/prenotazione.txt");
        this.output = new Output("file_db/config.txt", "file_db/film.txt", "file_db/sala.txt", "file_db/proiezione.txt", "file_db/prenotazione.txt");
    }

    public boolean creaProiezione(Proiezione proiezione) {

        
        
        
        
        return false;
    }

    public boolean controllerFilm(Film newFilm) throws IOException {

        if (newFilm.getDurata() < 300) {
            listaFilm = input.scaricaFilm();
            newFilm.setId_film(listaFilm.get(listaFilm.size()).getId_film()+1);
            listaFilm.add(newFilm);
            output.caricaFilm(listaFilm);
            System.out.println(newFilm);
            return true;
        } else {
            return false;
        }
    }
}
