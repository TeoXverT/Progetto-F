/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import oggetti.Film;
import oggetti.Screening;
import oggetti.Room;

/**
 *
 * @author cl421572
 */
public class PanelHallState extends JPanel{
    Controller_Gestore controller;
    JLabel outputGrafico;
    ArrayList<Screening> proiezioni;
    int id_sala;
    
    public PanelHallState(final Controller_Gestore controller, final JLabel outputGrafico, int id_sala) {
        this.controller = controller;
        this.outputGrafico = outputGrafico;
        this.id_sala = id_sala;
        init();
    }
    
    private void init() {
        
        this.removeAll();
        try {
            proiezioni = controller.getScreeningByRoom(id_sala);
        } catch (SQLException ex) {
            outputGrafico.setText("Errore con il server");
        }
        JPanel container = new JPanel(new GridLayout(0, 4));
        ArrayList<JButton> show  = new ArrayList<>();
        for(int i = 0; i < proiezioni.size(); i++) {
            show.add(new JButton("SHOW "+ i));
            container.add(show.get(i));
        }
        
        this.add(container, BorderLayout.CENTER);
        
    }
}







//molto beta...
//        
//        String[] columnNames = {"ID Hall", "Movie", "Date&Time"};
//    
//        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
//        JTable table = new JTable(tableModel);
//        table.setFillsViewportHeight(true);
//        JScrollPane scrollPane = new JScrollPane(table);
//        this.add(scrollPane);
//        
//        try{
//        ArrayList<Proiezione> pro = controller.visualizzaStatoSale();
//        ArrayList<Film> fil = controller.visualizzaFilm(0);
//        String Movie = "";
//        for (Proiezione p : pro) {      
//              for(Film f : fil){
//                    if(f.getId_film() == p.getId_film()){
//                    Movie = f.getTitolo_film();
//                    continue;
//                    }
//               }
//
//            int ID_Hall = p.getId_sala();
//            String date_time = p.getData_ora_friendly();
//            Object[] datas = {ID_Hall, Movie, date_time}; 
//            tableModel.addRow(datas);
//        }
//        }catch(SQLException ex){
//           // System.out.println("we got something man");
//        }

