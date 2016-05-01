
import Cliente.Controller_Cliente;
import Cliente.Gui_Cliente;
import Gestore.*;

import input_output.Adapter_SQL;
import input_output.SQLConnessione;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import oggetti.Film;
import oggetti.Proiezione;

public class main {

    public static void main(String[] args) throws IOException, SQLException {
        /*  BACHECA:
        
         //LE MODIFICHE INGENTI RIGUARDANO I SEGUENTI PUNTI:
        
         0) HO MODIFICATO LE WIKI SU GITHUB METTENDO DEI SCREENSHOOT IPOTETICHI (PHOTOSHOP A MANETTA) CHE RAPPRESENTANO IN MINIMA PARTE IL RISULTATO FINALE
         0) NON SO PERCHE' STO SCRIVENDO IN MAIUSCOLO, MA ORA MAI HO GIA SCRITTO 2 RIGHE E NON MI VA DI CANCELLARLE E RISCRIVERE TUTTO
         1) I CONTROLLER_GESTORE E IL CONTROLLER_CLIENTE SI SONO FUSI IN UN UNICA CLASSE CHIAMATA ADAPTER SQL
         2) LE CLASSI CLIENTE E GESTORE ORA SI CHIAMANO RISPETTIVAMENTE CONTROLLER_CLIENTE E CONTROLLER_GESTORE (PRIMA AVEVANO UN NOME IMPROPRIO
         3) CI SONO STATE MODIFICHE MINORI AD ALCUNE CLASSI IN OGGETTI (NULLA DI RILEVANTE)
         4) IN SQLCONNESSIONE IL METODO ESEGUI_QUERY SI E' SUDDIVISO IN DUE METODI eseguiQueryScrittura E eseguiQueryLettura CHE COME RICRDA ANCHE IL NOME SI USANO PER DUE MOTIVI DIVERSI
         5) LA GUI_GESTORE ORA E' DISEGNATA DA OGGETTI ESTERNI (JPANEL), QUESTO AL FINE DI RENDERE IL CODE PULITO, FLUIDO E FACILE DA MODIFICARE
         6) UN ESEMPIO COMPLETO PER SCRIVERE E LEGGERE DA DB_SQL SONO DATI DA PanelAddProiezione E PanelAddImpostazioni
         7) YAT IL TUO CODICE FUNZIONAVA MA NON RISPETTAVA IL CODE-FLOW, L'HO RIDISTRIBUITO NELLE CLASSI CORRETTE, HO LASCIATO COMMENTATO UNQ PARTE DEL TUO CODE
        
        
        
        
         REGOLE PER IL PROGRAMMATORE NON MARRANO:
         0) NON PUSHARE INVANO
         1) NON PUSHARE SUL HEAD DEL CODICE CHE DA PROBLEMI ALLA COMPILAZIONE, FARSI EVENTUALMENTE UN BRANCH PROPRIO
         2) NON PUSHARE CODICE ALTRUI COMMENTATO (CHE NE COMPROMETTA IL CORRETTO FUNZIONAMENTO)
         3) NON PUSHARE PROGRAMMI CON CODICE SUPERFLUO NEL MAIN AD ECCEZIONE DELLA CREAZIONE DELLE DUE GUI
         4) NON PUSHARE SUL HEAD PROGRAMMI CHE HANNO IN GIRO DEI SYSTEM.OUT.PRINT, EVENTUALMENTE COMMENTARLI PRIMA DI PUSHARE
         5) NON CREARE OGGETTI SE SONO GIA' PRESENTI AL INTERNO DELLA CLASSE STESSA
         6) RISPETTARE IL CODE-FLOW, OVE NON FOSSE POSSIBILE PENTIRSENE ETERNAMENTE
         (CODE-FLOW: PAROLA INVENTATA DA UMEER CHE DEFINISCE COME IL PROGRAMMA E' STRUTTURATO AD ESEMPIO: <SE CON IL DB_SQL CI SI PARLA TRAMITE IN ISTANZA DI SQLConnessione IN Adapter_SQL LO SI FA TRAMITE QUELLO, NON SI CREA UNA NUOVA CLASSE>)
         7) IL NOME DEI BRANCH DEVE RISPETTARE LA NATURA DELLA MODIFICA/TEST ESEGUITO SU DI ESSO
         8) COMMENTARE SEMPRE I COMMIT
         9) IL CODICE CHE SCRIVERAI PROBABILMENTE E' GIA' STATO SCRITTO DA QUALCUN'ALTRO DAI UN OCCHIATA A TUTTE LE CLASSI
        
        
        
         */

        Gui_Gestore gestore = new Gui_Gestore();
        gestore.setVisible(true);

        Gui_Cliente gui = new Gui_Cliente();
        gui.setVisible(true);

        /* 
        
         //TEST METODO PROIZIONIFILTRATE IN CLIENTE
        
         ArrayList<Proiezione> listaFilm = new ArrayList<>();
         ArrayList<Proiezione> listaFiltrate = new ArrayList<>();
        
        
         Cliente cliente = new Cliente();
        
         Input input = new Input("nine", "no", "no", "file_db/proiezione.txt", "no");
         listaFilm = input.scaricaProiezione();
        
         listaFiltrate = cliente.listaProiezioniFiltrate(listaFilm, 3);
        
        
        
         for(int i = 0; i < listaFiltrate.size(); i++){
            
         System.out.println(listaFiltrate.get(i).getId_film());
            
            
         }
         
        
        
         Calendar umeer = Calendar.getInstance();
          
         
          
         umeer.add(Calendar.DAY_OF_MONTH, 6);
                 
         System.out.println(umeer);
    


         */
        /*ArrayList<Proiezione> lista = new ArrayList<>();
       
         Calendar dataAttuale = Calendar.getInstance();
       
         Controller_Dati_Cliente controller = new Controller_Dati_Cliente();
         lista = controller.visualizzaFilmFiltratiRispettoOraEData(dataAttuale);
    
         for(int i = 0; i < lista.size(); i++) {
        
         System.out.println(lista.get(i).getId_proiezione());
      
         */
        ArrayList<Film> lista;
        Calendar dataOra = Calendar.getInstance();
        Calendar dataFine = Calendar.getInstance();
        dataFine.add(Calendar.DAY_OF_MONTH, 6);

        Adapter_SQL controller = new Adapter_SQL();
        lista = controller.visualizzaFilmFiltratiRispettoOraEData(dataOra, dataFine);

        for (int i = 0; i < lista.size(); i++) {

            System.out.println(lista.get(i).getId_film());
        }
    }
}
