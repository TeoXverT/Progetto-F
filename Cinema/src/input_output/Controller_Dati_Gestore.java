
package input_output;

import java.io.IOException;
import java.util.ArrayList;
import oggetti.*;

public class Controller_Dati_Gestore {
 
    private Input input;
    private Output output;
    ArrayList<Film> listaFilm = new ArrayList<>();

    public Controller_Dati_Gestore() {
        this.input = new Input("file_db/config.txt","file_db/film.txt","file_db/sala.txt","file_db/proiezione.txt","file_db/prenotazione.txt");    
        this.output= new Output("file_db/config.txt","file_db/film.txt","file_db/sala.txt","file_db/proiezione.txt","file_db/prenotazione.txt");
    }
    
    public boolean creaProiezione(Proiezione proiezione){
        
        
        
        
        
     return false;
    }

    void controllerFilm(Film newFilm) throws IOException{
         listaFilm = input.scaricaFilm();
         newFilm.setId_film(listaFilm.size() + 1);
         listaFilm.add(newFilm);
         System.out.println(newFilm);
    }
    

}
