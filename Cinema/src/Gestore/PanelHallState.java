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
        
        for (Proiezione p : pro) {      
            int ID_Hall = p.getId_sala();
            String Movie = "";
            Object[] datas = {ID_Hall, Movie, "indefinito"}; 
            tableModel.addRow(datas);
        }
        }catch(SQLException ex){
           // System.out.println("we got something man");
        }
    }
}
