/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

/**
 *
 * @author NEVERMIND
 */
public enum FilmGenere {
    Animazione, Avventura, Biografico, Commedia, Documentario, Drammatico, Erotico,
    Fantascienza, Fantasy, Guerra, Horror, Musical, Porno, Storico, Thriller, Western;
    // fonte https://it.wikipedia.org/wiki/Generi_cinematografici#Ricerca_di_un_metodo_per_la_classificazione_dei_generi_cinematograficihttp://www.film.it/film/generi/
    
    public static FilmGenere parserGenere(String parola) {

        if (parola.compareTo("Animazione") == 0) {
            return FilmGenere.Animazione;
        }
        if (parola.compareTo("Avventura") == 0) {
            return FilmGenere.Avventura;
        }
        if (parola.compareTo("Biografico") == 0) {
            return FilmGenere.Biografico;
        }
        if (parola.compareTo("Commedia") == 0) {
            return FilmGenere.Commedia;
        }
        if (parola.compareTo("Documentario") == 0) {
            return FilmGenere.Documentario;
        }
        if (parola.compareTo("Drammatico") == 0) {
            return FilmGenere.Drammatico;
        }
        if (parola.compareTo("Erotico") == 0) {
            return FilmGenere.Erotico;
        }
        if (parola.compareTo("Fantascienza") == 0) {
            return FilmGenere.Fantascienza;
        }
        if (parola.compareTo("Fantasy") == 0) {
            return FilmGenere.Fantasy;
        }
        if (parola.compareTo("Guerra") == 0) {
            return FilmGenere.Guerra;
        }
        if (parola.compareTo("Horror") == 0) {
            return FilmGenere.Horror;
        }
        if (parola.compareTo("Musical") == 0) {
            return FilmGenere.Musical;
        }
        if (parola.compareTo("Porno") == 0) {
            return FilmGenere.Porno;
        }
        if (parola.compareTo("Storico") == 0) {
            return FilmGenere.Storico;
        }
        if (parola.compareTo("Documentario") == 0) {
            return FilmGenere.Documentario;
        }
        if (parola.compareTo("Thriller") == 0) {
            return FilmGenere.Thriller;
        }
        if (parola.compareTo("Western") == 0) {
            return FilmGenere.Western;
        }
        //etc etc
        return null;
    }
    
    
    
    
}
