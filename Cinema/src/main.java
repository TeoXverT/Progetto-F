
import Cliente.Cliente;
import Cliente.Gui_Cliente;
import Gestore.*;
import input_output.Input;
import input_output_sql.SQLConnessione;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import oggetti.Proiezione;

public class main {

    public static void main(String[] args) throws IOException {
      
        Gui_Gestore gestore = new Gui_Gestore();
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
    }

   
    
    
    
    
}
