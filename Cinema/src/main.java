import gui.admin.AdminGui;
import gui.customer.CustomerGui;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;

/*
 * Classe utilizzata per istanziare i frame, renderli visibili e associarli un listener che alla chiusura interompe il player youtube
 *
 */
  /*  BACHECA:
        
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
         9) IL CODICE CHE SCRIVERAI PROBABILMENTE E' GIA' STATO SCRITTO IN PARTE DA QUALCUN'ALTRO DAI UN OCCHIATA A TUTTE LE CLASSI
            
         */
public class main {

    public static void main(String[] args) {

        AdminGui admin = new AdminGui();
        admin.setVisible(true);
        admin.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                NativeInterface.close();
                System.exit(0);
            }
        });

        final CustomerGui client = new CustomerGui();
        client.setVisible(true);
        client.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                
                NativeInterface.close();
                System.exit(0);
            }
        });
    }
}
