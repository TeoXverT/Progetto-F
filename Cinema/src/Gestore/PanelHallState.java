/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import oggetti.Film;
import oggetti.Proiezione;
import oggetti.Sala;

/**
 *
 * @author cl421572
 */
public class PanelHallState extends JPanel{
    
    public PanelHallState(final Controller_Gestore controller, final JLabel outputGrafico) {
        //molto beta...
        
        String[] columnNames = {"ID Hall", "Movie", "Date&Time"};
    
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        
        try{
        ArrayList<Proiezione> pro = controller.visualizzaStatoSale();
        ArrayList<Film> fil = controller.visualizzaFilm(0);
        String Movie = "";
        for (Proiezione p : pro) {      
              for(Film f : fil){
                    if(f.getId_film() == p.getId_film()){
                    Movie = f.getTitolo_film();
                    continue;
                    }
               }

            int ID_Hall = p.getId_sala();
            String date_time = p.getData_ora_friendly();
            Object[] datas = {ID_Hall, Movie, date_time}; 
            tableModel.addRow(datas);
        }
        }catch(SQLException ex){
           // System.out.println("we got something man");
        }
    }
}
