
import input_output.IODati;
import java.io.IOException;
import java.util.ArrayList;
import oggetti.*;

public class main {

    public static void main(String[] args) throws IOException {
        System.out.println("Progetto Cinema V 0.2/W la mozzarella");

        IODati IO = new IODati();

        ArrayList<Film> listaFilm = IO.caricaListaFilm("file_db/film.txt");
        for (Film f : listaFilm) {
            System.out.println(f.toString());
        }

        ArrayList<Sala> listaSale = IO.caricaListaSala("file_db/sala.txt");
        for (Sala s : listaSale) {
            System.out.println(s.toString());
        }

        ArrayList<Proiezione> listaProiezione = IO.caricaListaProiezione("file_db/proiezione.txt");
        for (Proiezione p : listaProiezione) {
            System.out.println(p.toString());
        }
        
        ArrayList<Prenotazione> listaPrenotazione = IO.caricaListaPrenotazione("file_db/prenotazione.txt");
        for (Prenotazione pe : listaPrenotazione) {
            System.out.println(pe.toString());
        }
    }

}
