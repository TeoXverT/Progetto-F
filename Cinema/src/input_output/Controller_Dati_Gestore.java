
package input_output;

import java.io.IOException;
import java.util.ArrayList;
import oggetti.*;

public class Controller_Dati_Gestore {
 
    private Input input;
    private Output output;
    ArrayList<Film> listaFilm = new ArrayList<>();

    public Controller_Dati_Gestore() {
        this.input = new Input();
        this.output= new Output();
    }
    
    public boolean creaProiezione(Proiezione proiezione){
        
        
        
        
     return false;
    }

    public boolean controllerFilm(Film newFilm) throws IOException{
         Input i = new Input();
         Output o = new Output();
        if (newFilm.getDurata()<300) {
            listaFilm = i.scaricaFilm("file_db/film.txt");
            newFilm.setId_film(listaFilm.size() + 1);
            listaFilm.add(newFilm);
            o.caricaFilm(listaFilm, "ff");
            System.out.println(newFilm);
            return true;
        } else {
            return false;
        }
    }
    

}
