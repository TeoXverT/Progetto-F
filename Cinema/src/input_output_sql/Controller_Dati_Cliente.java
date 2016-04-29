/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output_sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
        
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate1 = sdfDate.format(Data_ora_fine.getTime());
        String strDate2 = sdfDate.format(Data_ora_inizio.getTime());
        
        String query = "SELECT * FROM  Proiezione  WHERE ( DATE( Proiezione.data_ora ) > DATE( '"+strDate2+"' ) AND DATE( Proiezione.data_ora ) < DATE('"+strDate1+ "'))";
       /*
        __________________________________________________________________________________________________________________________________
        |
        |è LA QUERY DA USARE PER  AVERE TUTTE LE INFORMAZIONI (FUNZIONA)
        |
        |SELECT * FROM  Proiezione INNER JOIN Film ON Proiezione.id_film = Film.id_film  WHERE ( DATE( Proiezione.data_ora ) > DATE( '2016-04-29 14:00:00' ) AND DATE( Proiezione.data_ora ) < DATE( '2016-05-30 14:00:00' ))
        |
        |___________________________________________________________________________________________________________________________
        */
         
         
         
        risultatoQuery = sql.eseguiQuery(query);
        listaProiezioni = parser.Proiezione(risultatoQuery);
        
        return listaProiezioni;
    }
    
    
}