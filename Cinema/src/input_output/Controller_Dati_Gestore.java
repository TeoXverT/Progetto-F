
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

    void controllerFilm(Film newFilm) throws IOException{
         Input i = new Input();
         listaFilm = i.scaricaFilm("file_db/film.txt");
         newFilm.setId_film(listaFilm.size() + 1);
         listaFilm.add(newFilm);
         System.out.println(newFilm);
    }
    

}
