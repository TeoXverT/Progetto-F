package oggetti;

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

    private String titolo_film, genere, descrizione, link;
    private int id_film, durata;

    //DURATA IN MINUTI
    public Film(int id_film, String titolo_film, String genere, int durata, String descrizione, String link) {

        this.id_film = id_film;
        this.titolo_film = titolo_film;
        this.genere = genere;
        this.durata = durata;
        this.descrizione = descrizione;
        this.link = link;

    }

}
