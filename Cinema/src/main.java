
import Cliente.Cliente;
import input_output.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import oggetti.*;

public class main {

    public static void main(String[] args) throws IOException {
      /*  System.out.println("Progetto Cinema V 0.1/W la pasta ");

        Input input = new Input();

        ArrayList<Film> listaFilm = input.scaricaFilm("file_db/film.txt");
        for (Film f : listaFilm) {
            System.out.println(f.toString());
        }

        ArrayList<Sala> listaSala = input.scaricaSala("file_db/sala.txt");
        for (Sala s : listaSala) {
            System.out.println(s.toString());
        }

        ArrayList<Proiezione> listaProiezione = input.scaricaProiezione("file_db/proiezione.txt");
        for (Proiezione p : listaProiezione) {
            System.out.println(p.toString());
        }
        
        ArrayList<Prenotazione> listaPrenotazione = input.scaricaPrenotazione("file_db/prenotazione.txt");
        for (Prenotazione pe : listaPrenotazione) {
            System.out.println(pe.toString());
        }
        
        Config config = input.scaricaConfig("file_db/config.txt");
        System.out.println(config.toString());
        
        
         Output output = new Output();
        System.out.println( output.caricaFilm(listaFilm,"file_db/film_copy.txt") );
        System.out.println( output.caricaConfig(config,"file_db/config_copy.txt") );
        System.out.println( output.caricaSala(listaSala,"file_db/sala_copy.txt") );
        System.out.println( output.caricaProiezione(listaProiezione,"file_db/proiezione_copy.txt") );
        System.out.println( output.caricaPrenotazione(listaPrenotazione,"file_db/prenotazione_copy.txt") );

        */
        /*
        
        
        
        ArrayList<Proiezione> listaProiezioni;
         ArrayList<Proiezione> listaProiezioniFuture;
        Cliente cliente = new Cliente();
        Input input = new Input();
        int i ;
        
        listaProiezioni = cliente.listaProiezioniFuture( input.scaricaProiezione("file_db/proiezione.txt"));
        
        
        
          listaProiezioniFuture =   cliente.listaProiezioniFuture(listaProiezioni);
        
        for(i = 0; i < listaProiezioniFuture.size(); i++) {
            
            System.out.println(listaProiezioniFuture.get(i));
            
        }
        
                */
    }
    
    

}
