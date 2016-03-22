
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yatin
 */
public class Gestione {
    ArrayList<Film> Film;
    ArrayList<Sala> Sale;
    ArrayList<Proiezione> Proiezioni;
    
    public Gestione(){
         Film = new ArrayList<>();
         Sale = new ArrayList<>();
         Proiezioni = new ArrayList<>();
    }
    
    public void AggiornaDati(String nome_file) {
        IODati IO = new IODati();
        switch(nome_file) {
            case "film.txt":
                IO.caricaListaFilm(nome_file);
                break;
            case "sale.txt":
                
        }
  
        
    }
}
