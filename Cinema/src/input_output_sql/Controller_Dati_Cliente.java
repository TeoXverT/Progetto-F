/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output_sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import oggetti.Proiezione;

/**
 *
 * @author Cri
 */
public class Controller_Dati_Cliente {
    
    SQLConnessione sql;
     Parse_OBJ parser;
     
    public Controller_Dati_Cliente() {
         
        sql = new SQLConnessione();
        sql.creaConnessione();
        parser = new  Parse_OBJ();
    }
    
    //______________________________________________________________________________________________________________________
    //ANCORA IN COSTRUZIONE(PER RICCARDO, LA QUERY SU PHPMYADMIN FUNZIONA, CREDO CHE IL PROBLEMA PRINCIPALE                |
    //SIA IL PASSAGGIO DEL VALORE, VEDI TU SE RIESCI A RISOLVERE I PROBLEMI CHE DA)                                        |
    //_____________________________________________________________________________________________________________________|
    public ArrayList<Proiezione> visualizzaProiezioniFiltrate(Calendar Data_ora_inizio, Calendar Data_ora_fine) throws SQLException {
        
        ArrayList<Proiezione> listaProiezioni;
        ResultSet risultatoQuery;
        String query = "SELECT * FROM  Proiezione  WHERE ( DATE( Proiezione.data_ora ) > DATE("+Data_ora_inizio+" ) AND DATE( Proiezione.data_ora ) < DATE( "+Data_ora_fine+ ", INTERVAL 1 DAY)))";
        
        
        
        risultatoQuery = sql.eseguiQuery(query);
        listaProiezioni = parser.Proiezione(risultatoQuery);
        
        return listaProiezioni;
    }
    
    
}
