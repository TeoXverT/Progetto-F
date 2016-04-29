
import Cliente.Cliente;
import Cliente.Gui_Cliente;
import Gestore.*;

import input_output.Input;
import input_output_sql.Controller_Dati_Cliente;
import input_output_sql.SQLConnessione;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import oggetti.Proiezione;

public class main {

    public static void main(String[] args) throws IOException, SQLException {
      
       /* Gui_Gestore gestore = new Gui_Gestore();
        gestore.setVisible(true);
        
        Gui_Cliente gui = new Gui_Cliente();
        gui.setVisible(true);
        
        
        
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
    lista = controller.visualizzaProiezioniFiltrate(dataAttuale);
    
    for(int i = 0; i < lista.size(); i++) {
        
        System.out.println(lista.get(i).getId_proiezione());
      */
    
    ArrayList<Proiezione> lista ;
    Calendar dataOra = Calendar.getInstance();
    Calendar dataFine = Calendar.getInstance();
    dataFine.add(Calendar.DAY_OF_MONTH, 1);
    
    Controller_Dati_Cliente controller = new Controller_Dati_Cliente();
    lista = controller.visualizzaProiezioniFiltrate(dataOra, dataFine);
    
    for(int i = 0; i < lista.size(); i++) {
        
        System.out.println(lista.get(i).getId_proiezione());
    }

    
    }
    
    }
    

   
    
    
    
    

