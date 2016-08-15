package core.obj;

import java.util.Calendar;

/*
 * Classe che rappresenta il concetto di film
 *
 */
public class Film {

    private String title, categories, description, linkYoutube, linkCover;
    private int idFilm, length;
    private Calendar dateTime;

    //DURATA IN MINUTI
    public Film(int idFilm, String title, String categories, int length, String description, String linkYoutube, String linkCover, Calendar dateTime) {

        this.idFilm = idFilm;
        this.title = title;
        this.categories = categories;
        this.length = length;
        this.description = description;
        this.linkYoutube = linkYoutube;
        this.linkCover = linkCover;
        this.dateTime = dateTime;
    }

    public Film(String title, String categories, int length, String description, String linkYoutube, String linkCover) {

        this.title = title;
        this.categories = categories;
        this.length = length;
        this.description = description;
        this.linkYoutube = linkYoutube;
        this.linkCover = linkCover;
    }

    public Film(int idFilm) {
        this.idFilm = idFilm;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public String getLinkCover() {
        return linkCover;
    }

    @Override
    public String toString() {
        return title + "";
    }

    public String getTitle() {
        return title;
    }

    public String getCategories() {
        return categories;
    }

    public String getDescription() {
        return description;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int id_film) {
        this.idFilm = id_film;
    }

    public int getLength() {
        return length;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
