/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import oggetti.Config;
import oggetti.Film;
import oggetti.Prenotazione;
import oggetti.Proiezione;
import oggetti.Sala;
import oggetti.Seat;

/**
 *
 * @author Yatin
 */
public class PageThree extends JPanel {

    Controller_Cliente controller;
    Proiezione proiezione;
    Film film;

    private ImageIcon screen_icon = new ImageIcon("immagini/poltrone/screen.png");
    private ImageIcon seat_taken = new ImageIcon("immagini/poltrone/seat_taken.png");
    private ImageIcon seat_vip = new ImageIcon("immagini/poltrone/seat_vip.png");
    private ImageIcon seat_handicap = new ImageIcon("immagini/poltrone/seat_handicap.png");
    private ImageIcon seat_free = new ImageIcon("immagini/poltrone/seat_free.png");

    private double totale_prezzo;
    private Config config;

    private Sala sala;
    private ArrayList<Seat> seats;

    JLabel prezzo = new JLabel();
    JButton prosegui = new JButton("PROSEGUI");

    public PageThree(Film film, Proiezione proiezione, Controller_Cliente controller) {
        this.controller = controller;
        this.film = film;
        this.proiezione = proiezione;
        try {
            this.sala = controller.salaByID(proiezione.getId_sala());
        } catch (SQLException ex) {
// TI devi gestire le eccezioni internamente non lanciarle al page precedente

        }
        seats = new ArrayList<>();
        seats = controller.getSeats(proiezione.getId_sala());
        try {
            config = controller.getConfig();
        } catch (SQLException ex) {
// TI devi gestire le eccezioni internamente non lanciarle al page precedente
        }
        initGui();
    }

    public void initGui() {
        this.removeAll();
        this.setLayout(new BorderLayout(20, 30));
        JPanel nord = new JPanel();
        JPanel sud = new JPanel(new GridLayout(0, 2));
        JPanel center = new JPanel(new BorderLayout());

        JPanel seats_layout = new JPanel(new GridLayout(sala.getRows(), sala.getColumns(), 0, 1));

        JLabel screen = new JLabel(screen_icon);
        nord.add(screen);

        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).isDisable() == false) {
                seats.get(i).addActionListener(seatClick(i));
            }
            seats_layout.add(seats.get(i));
        }
        center.add(seats_layout, BorderLayout.CENTER);

        JPanel leggenda = new JPanel(new GridLayout(0, 2));
        JLabel prezzo_normale = new JLabel("Prezzo: " + proiezione.getPrezzo() + "€");
        JLabel prezzo_vip = new JLabel("Posti vip costo aggiuntivo di " + config.getPrezzo_vip() + "€");
        leggenda.add(prezzo_normale);
        leggenda.add(prezzo_vip);

        JPanel total = new JPanel(new BorderLayout(5, 5));
        JLabel totale = new JLabel("TOTALE: ");

        JLabel offer = new JLabel("Scontoooooooooooooooooooo");
        JButton indietro = new JButton("indietro");

        JPanel buttons = new JPanel(new GridLayout(0, 2));

        total.add(totale, BorderLayout.WEST);
        total.add(prezzo, BorderLayout.EAST);
        buttons.add(indietro);
        buttons.add(prosegui);

        indietro.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                goback();
            }
        });

        prosegui.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Poi quando sei pronto mi dovrai passare i parametri che ora qui sotto simulo
                ///------------  prova della page 4 di umeer
                //Ipotetica prenotazione...
//                ArrayList<Seat> posti_prenotati1 = new ArrayList<>();
//                posti_prenotati1.add(new Seat(338, 12, 13));
//                posti_prenotati1.add(new Seat(339, 14, 13));
//               
//                Prenotazione prenotazione1 = new Prenotazione(0, proiezione.getId_proiezione(), posti_prenotati1, null, 0, 12.0, 0);
//                openPage(new PageFour(film, proiezione, prenotazione1, config, controller));

                ///------------  prova della page 4
                
            }
        });

        sud.add(leggenda);
        sud.add(total);
        sud.add(offer);
        sud.add(buttons);

        this.add(nord, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(sud, BorderLayout.SOUTH);
    }

    // questo metodo serve per prenotare i posti.
    public ActionListener seatClick(final int i) {
        ActionListener event = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (seats.get(i).isOccupato()) {
                    seats.get(i).setOccupato(false);
                    if (seats.get(i).isVip()) {
                        seats.get(i).setIcon(seat_vip);
                        totale_prezzo -= (config.getPrezzo_vip() + proiezione.getPrezzo());                        // devo cambire i prezzi, devo scaricarli da 
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    } else if (seats.get(i).isHandicap()) {
                        seats.get(i).setIcon(seat_handicap);
                        totale_prezzo -= proiezione.getPrezzo();
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    } else {
                        seats.get(i).setIcon(seat_free);
                        totale_prezzo -= proiezione.getPrezzo();
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    }
                } else {
                    seats.get(i).setOccupato(true);
                    seats.get(i).setIcon(seat_taken);
                    if (seats.get(i).isVip()) {
                        totale_prezzo += (config.getPrezzo_vip() + proiezione.getPrezzo());
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    } else {
                        totale_prezzo += proiezione.getPrezzo();
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    }

                }
            }
        };
        return event;
    }

    private void goback() {
        this.removeAll();
        this.add(new PageOne(controller));
        this.revalidate();
        this.repaint();
    }

    private void openPage(JPanel panel) {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

}
