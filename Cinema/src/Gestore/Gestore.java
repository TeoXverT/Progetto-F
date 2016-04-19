package Gestore;

import input_output.*;
import java.io.IOException;
import oggetti.*;

/**
 *
 * @author Yatin
 */
public class Gestore {
    
    private Controller_Dati_Gestore controller = new Controller_Dati_Gestore();
    
    public Gestore() {}
        
        public boolean creaProiezione( String data_ora,int id_film, int id_sala, String  tipo_proiezione, double prezzo_normale, double prezzo_3d){ 
            
            return false;
        }
        
        public boolean modifica_config(double prezzo_vip, double sconto){
            try {
            Config config1 = new Config(prezzo_vip, sconto);
            controller.modifica_config(config1, "config.txt");
            }
            catch(IOException e){
                    System.out.println("Errore!!!");
                    return false;
                    }
            return true;            
        }
        
}
