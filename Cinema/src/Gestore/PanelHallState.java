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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import oggetti.Film;
import oggetti.Screening;
import oggetti.Room;
import oggetti.Seat;

/**
 *
 * @author yatin
 */
public class PanelHallState extends JPanel {

    Controller_Gestore controller;
    Controller_Cliente controller_cliente = new Controller_Cliente();
    JLabel outputGrafico;
    ArrayList<Screening> proiezioni;
    private ArrayList<Seat> seats;
    private ArrayList<Seat> bookedSeats;
    int id_sala;
    Room sala;
    private ImageIcon seat_taken = new ImageIcon("immagini/poltrone/seat_taken.png");

    public PanelHallState(final Controller_Gestore controller, final JLabel outputGrafico, int id_sala) {
        try {
            this.controller = controller;
            this.outputGrafico = outputGrafico;
            this.id_sala = id_sala;

            sala = controller.getSala(id_sala);
            init();
        } catch (SQLException ex) {
            outputGrafico.setText("Erroe letture dati dal database.");
        }
    }

    private void init() {
        this.removeAll();
        try {
            proiezioni = controller.viewShows(id_sala);     // vado a prendere tutte le proiezioni in un sala
            JPanel container = new JPanel(new GridLayout(0, 4));
            ArrayList<JButton> show = new ArrayList<>();    // uso per rappresentare tutte le proiezioni

            for (int i = 0; i < proiezioni.size(); i++) {   // questo ciclo visualizza tutte le proiezioni e aggiunge gli actionListener
                show.add(new JButton("SHOW " + proiezioni.get(i).getId_proiezione()));
                show.get(i).addActionListener(showClick(proiezioni.get(i).getId_proiezione()));
                container.add(show.get(i));
            }
            this.add(container, BorderLayout.CENTER);
        } catch (SQLException ex) {
            outputGrafico.setText("Errore con il server");
        }

    }

    private ActionListener showClick(final int id_proiezione) {         // questo metodo è per gestire l'actionListener associato alle proiezioni.
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                drawLayout(id_proiezione);
            }
        };
        return event;
    }

    private void drawLayout(int id_proiezione) { 
        try {
            this.removeAll();
            this.setLayout(new BorderLayout());
            JPanel seats_layout = new JPanel(new GridLayout(sala.getRows(), sala.getColumns(), 0, 1));
            seats = controller_cliente.getSeats(id_sala);
            bookedSeats = controller.getTakenSeats(id_proiezione);

            for (int i = 0; i < bookedSeats.size(); i++) {
                for (int j = 0; j < seats.size(); j++) {
                    if (bookedSeats.get(i).getId() == seats.get(j).getId()) {
                        seats.get(j).setIcon(seat_taken);
                    }
                }
            }

            for (Seat seat : seats) {
                seats_layout.add(seat);
            }
            bookedSeats.clear();
            seats.clear();
            this.add(seats_layout, BorderLayout.CENTER);
            JButton back = new JButton("BACK");
            back.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    goBack();
                }
            });
            this.add(back, BorderLayout.SOUTH);
            this.revalidate();
            this.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(PanelHallState.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void goBack() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        init();
        this.revalidate();
        this.repaint();
    }
}