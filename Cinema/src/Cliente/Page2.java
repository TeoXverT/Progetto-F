/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import input_output.Adapter_SQL;
import java.awt.GridLayout;
import java.sql.SQLException;
import javax.swing.JPanel;

/**
 *
 * @author Riccardo
 */
public class Page2 extends JPanel {
    
    
    
    
    public Page2(Controller_Cliente controller, Adapter_SQL adapter, int id_film) throws SQLException{
        controller = new Controller_Cliente();
        adapter=new Adapter_SQL();
        adapter.visualizzaInformazioniFilm(id_film);
        this.setLayout(new GridLayout(0, 2, 1, 50));
        
       }
    
}
