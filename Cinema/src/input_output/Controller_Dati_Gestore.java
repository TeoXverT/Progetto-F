/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output;

import java.util.ArrayList;
import oggetti.Film;
import oggetti.Proiezione;
import input_output.Input;
import java.io.IOException;

/**
 *
 * @author Yoga
 */
public class Controller_Dati_Gestore {
 
    private Input input;
    private Output output;
    ArrayList<Film> listaFilm = new ArrayList<>();
    

    public Controller_Dati_Gestore() {
        this.input = new Input();
        this.output= new Output();
    }
    
    public boolean creaPriezione(){
     return false;
    }
    
    void controllerFilm(Film newFilm) throws IOException{
         Input i = new Input();
         listaFilm = i.scaricaFilm("file_db/film.txt");
         newFilm.setId_film(listaFilm.size() + 1);
         listaFilm.add(newFilm);
         // usa int size() per l'indice
    }
}
