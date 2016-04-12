package Gestore;
import oggetti.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yatin
 */
public class Gestore {
    
    public Gestore() {  
    }
  
    void aggiungiFilm(String titolo_film, String genere, int durata, String descrizione, String link_trailer){
        Film newFilm = new Film(0, titolo_film, genere, durata, descrizione, link_trailer);
    }
    
}
