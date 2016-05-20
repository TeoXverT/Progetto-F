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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import oggetti.Config;
import oggetti.Proiezione;
import oggetti.Sala;
import oggetti.Seat;

/**
 *
 * @author Yatin
 */
public class PageThree extends JPanel {

    ImageIcon screen_icon = new ImageIcon("immagini/poltrone/screen.png");
    ImageIcon seat_taken = new ImageIcon("immagini/poltrone/seat_taken.png");
    ImageIcon seat_vip = new ImageIcon("immagini/poltrone/seat_vip.png");
    ImageIcon seat_handicap = new ImageIcon("immagini/poltrone/seat_handicap.png");
    ImageIcon seat_free = new ImageIcon("immagini/poltrone/seat_free.png");
    Controller_Cliente controller;
    Proiezione proiezione;
    private double totale_prezzo;
    private Config config;

    JButton prosegui = new JButton("PROSEGUI");
    Sala sala;
    ArrayList<Seat> seats;

    JLabel prezzo = new JLabel();

    public PageThree(Proiezione proiezione, Controller_Cliente controller) throws SQLException {
        this.controller = controller;
        this.proiezione = proiezione;
        this.sala = controller.salaByID(proiezione.getId_sala());
        seats = new ArrayList<>();
        seats = controller.getSeats(proiezione.getId_sala());
        config = controller.getConfig();
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

        JLabel leggenda = new JLabel("LEGGENDA");
        JLabel totale = new JLabel("TOTALE: ");

        JLabel offer = new JLabel("Scontoooooooooooooooooooo");
        JButton indietro = new JButton("indietro");

        JPanel total = new JPanel(new BorderLayout(5, 5));
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
                calculateTotal();
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
                        prezzo.setText(String.valueOf(totale_prezzo));
                    } else if (seats.get(i).isHandicap()) {
                        seats.get(i).setIcon(seat_handicap);
                        totale_prezzo -= proiezione.getPrezzo();
                        prezzo.setText(String.valueOf(totale_prezzo));
                    } else {
                        seats.get(i).setIcon(seat_free);
                        totale_prezzo -= proiezione.getPrezzo();
                        prezzo.setText(String.valueOf(totale_prezzo));
                    }
                } else {
                    seats.get(i).setOccupato(true);
                    seats.get(i).setIcon(seat_taken);
                    if (seats.get(i).isVip()) {
                        totale_prezzo += (config.getPrezzo_vip() + proiezione.getPrezzo());
                        prezzo.setText(String.valueOf(totale_prezzo));
                    } else {
                        totale_prezzo += proiezione.getPrezzo();
                        prezzo.setText(String.valueOf(totale_prezzo));
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

    private void calculateTotal() {

    }
//    // questo thread mi serve per scaricare tutti i posti di una data sala e scaricarli mentre viene creata la gui.
//    // perchè potrebbe volerci un po' se i posti sono tanti.
//    private Thread Drawer() {
//        Thread t = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                seats = controller.getSeats(proiezione.getId_sala());
//            }
//        });
//        return t;
//    }
}
