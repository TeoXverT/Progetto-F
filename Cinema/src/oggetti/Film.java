package oggetti;
/**
 *
 * @author cri
 */
public class Film {
    
    private String titolo_film, genere, descrizione, link_youtube, link_copertina;
    private int id_film, durata;

    //DURATA IN MINUTI
    public Film(int id_film, String titolo_film, String genere, int durata, String descrizione, String link_youtube, String link_copertina) {

        this.id_film = id_film;
        this.titolo_film = titolo_film;
        this.genere = genere;
        this.durata = durata;
        this.descrizione = descrizione;
        this.link_youtube = link_youtube;
        this.link_copertina = link_copertina;
    }

    @Override
    public String toString() {
        return "Film{" + "titolo_film=" + titolo_film + ", genere=" + genere + ", descrizione=" + descrizione + ", link_youtube=" + link_youtube + ", id_film=" + id_film + ", durata=" + durata + '}';
    }

    public String getTitolo_film() {
        return titolo_film;
    }

    public String getGenere() {
        return genere;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getLink() {
        return link_youtube;
    }

    public int getId_film() {
        return id_film;
    }

    public int getDurata() {
        return durata;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }
    
    
    
    

}
