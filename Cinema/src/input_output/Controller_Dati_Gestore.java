package input_output;

import java.io.IOException;
import java.util.ArrayList;
import oggetti.*;

public class Controller_Dati_Gestore {

    private Input input;
    private Output output;
    ArrayList<Film> listaFilm = new ArrayList<>();

    public Controller_Dati_Gestore() {
        this.input = new Input("file_db/config.txt", "file_db/film.txt", "file_db/sala.txt", "file_db/proiezione.txt", "file_db/prenotazione.txt");
        this.output = new Output("file_db/config.txt", "file_db/film.txt", "file_db/sala.txt", "file_db/proiezione.txt", "file_db/prenotazione.txt");
    }

    public boolean creaProiezione(Proiezione proiezione) {

        return false;
    }

    
    public boolean modifica_config(Config config) throws IOException{
        if(config.getPrezzo_poltrona_vip() == 0 || config.getPrezzo_poltrona_vip() >= 50){
            System.out.println("prezzo_vip errato!!");
            return false;
        } else output.caricaConfig(config);
        
        return true;
    }

    public boolean controllerFilm(Film newFilm) throws IOException {

        if (newFilm.getDurata() < 300) {
            listaFilm = input.scaricaFilm();
            newFilm.setId_film(listaFilm.size());
            listaFilm.add(newFilm);
            output.caricaFilm(listaFilm);
            System.out.println(newFilm);
            return true;
        } else {
            return false;
        }
    }
}
