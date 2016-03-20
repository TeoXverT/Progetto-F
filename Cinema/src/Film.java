/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cri
 */
public class Film {
    
    String nome ,  trama;
    int idFilm , durata;
    
    //DURATA IN MINUTI
    
    public Film(String nome, int idFilm, int durata, String trama) {
        
        this.nome = nome;
        this.idFilm = idFilm;
        this.durata = durata;
        this.trama = trama;
                
    }
    
}
