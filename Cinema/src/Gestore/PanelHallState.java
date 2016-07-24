/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import Cliente.Controller_Cliente;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import oggetti.Proiezione;
import oggetti.Sala;
import oggetti.Seat;

/**
 *
 * @author cl421572
 */
public class PanelHallState extends JPanel {

    Controller_Gestore controller;
    Controller_Cliente controller_cliente;
    JLabel outputGrafico;
    ArrayList<Proiezione> proiezioni;
    private ArrayList<Seat> seats;
    int id_sala;
    Sala sala;

    public PanelHallState(final Controller_Gestore controller, final JLabel outputGrafico, int id_sala) {
        this.controller = controller;
        this.outputGrafico = outputGrafico;
        this.id_sala = id_sala;
        seats = new ArrayList<>();
        try {
            sala = controller_cliente.salaByID(id_sala);
        } catch (SQLException ex) {
            Logger.getLogger(PanelHallState.class.getName()).log(Level.SEVERE, null, ex);
        }
        init();
    }

    private void init() {
        this.removeAll();
        try {
            proiezioni = controller.viewShows(id_sala);     // vado a prendere tutte le proiezioni in un sala
            JPanel container = new JPanel(new GridLayout(0, 4));
            ArrayList<JButton> show = new ArrayList<>();    // uso per rappresentare tutt le proiezioni
            for (int i = 0; i < proiezioni.size(); i++) {   // questo ciclo visualizza tutte le proiezioni e aggiunge gli actionListener
                show.add(new JButton("SHOW " + proiezioni.get(i).getId_proiezione()));
                show.get(i).addActionListener(showClick());
                container.add(show.get(i));
            }

            this.add(container, BorderLayout.CENTER);
        } catch (SQLException ex) {
            outputGrafico.setText("Errore con il server");
        }

    }
    
    private ActionListener showClick() {         // questo metodo è per gestire l'actionListener associato alle proiezioni.
        
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
//                    System.out.println(idSala);
//                    System.out.println(sala.getId_sala() + sala.getColumns() + sala.getRows());
//                    System.out.println(sala);
                drawLayout();
            }
        };
        return event;
    }

    private void drawLayout() {
        this.removeAll();

        JPanel container = new JPanel(new GridLayout(sala.getRows(), sala.getColumns(), 0, 1));
        seats = controller_cliente.getSeats(sala.getId_sala());
        for (int i = 0; i < seats.size(); i++) {
            container.add(seats.get(i));
        }
        this.add(container, BorderLayout.CENTER);

    }
}
