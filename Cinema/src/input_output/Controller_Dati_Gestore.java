/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output;

import java.io.IOException;
import oggetti.Config;

public class Controller_Dati_Gestore {
 
    private Input input;
    private Output output;

    public Controller_Dati_Gestore() {
        this.input = new Input();
        this.output= new Output();
    }
    
    public boolean creaPriezione(){
     return false;
    }
    
    public boolean modifica_config(Config config, String nomeFile) throws IOException{
        if(config.getPrezzo_poltrona_vip() == 0 || config.getPrezzo_poltrona_vip() >= 50){
            System.out.println("prezzo_vip errato!!");
            return false;
        } else output.caricaConfig(config, nomeFile);
        
        return true;
    }
    
}
