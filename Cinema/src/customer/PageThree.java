/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import obj.Config;
import obj.Film;
import obj.Booking;
import obj.Projection;
import obj.Hall;
import obj.Seat;

/**
 *
 * @author Yatin
 */
public class PageThree extends JPanel {

    private CustomerController controller;
    private Projection screening;

    private ImageIcon screen_icon = new ImageIcon("images/hall/screen.png");
    private ImageIcon seat_taken = new ImageIcon("images/hall/seat_taken.png");
    private ImageIcon seat_vip = new ImageIcon("images/hall/seat_vip.png");
    private ImageIcon seat_handicap = new ImageIcon("images/hall/seat_handicap.png");
    private ImageIcon seat_free = new ImageIcon("images/hall/seat_free.png");
    private ImageIcon seat_selezione = new ImageIcon("images/hall/seat_selezione.png");

    private double totale_prezzo;
    private Config config;

    private Hall sala;
    private ArrayList<Seat> seats;
    private ArrayList<Seat> booked_seats;
    private ArrayList<Seat> Taken_seats = new ArrayList<>();
    private Component frameErrore;
    private JLabel prezzo = new JLabel();
    private JButton prosegui = new JButton("PROSEGUI");

    public PageThree(Projection proiezione) {
        this.controller = CustomerController.getInstance();
        this.screening = proiezione;
        try {
            this.sala = controller.getHallByIdHall(proiezione.getRoom().getIdHall());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frameErrore, "Errore con il server", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);

        }

        booked_seats = new ArrayList<>();
        seats = new ArrayList<>();
        seats = controller.getSeatsByIdHall(proiezione.getRoom().getIdHall());
        try {
            config = controller.getConfig();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frameErrore, "Errore con il server", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);

        }
        initGui();
    }

    public void initGui() {
        this.removeAll();
        this.setLayout(new BorderLayout(20, 30));

        JPanel nord = new JPanel();
        nord.setBackground(java.awt.Color.WHITE);

        JPanel sud = new JPanel(new GridLayout(0, 2));
        JPanel center = new JPanel(new BorderLayout());

        JPanel seats_layout = new JPanel(new GridLayout(sala.getRows(), sala.getColumns(), 0, 1));

        JLabel screen = new JLabel(screen_icon);
        nord.add(screen);
        checkTakenSeats();

        for (int i = 0; i < booked_seats.size(); i++) {
            for (int j = 0; j < seats.size(); j++) {
                if (booked_seats.get(i).getId() == seats.get(j).getId()) {
                    seats.get(j).setIcon(seat_taken);
                    seats.get(j).setOccupato(true);
                }
            }
        }

        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).isDisable() == false && seats.get(i).isOccupato() == false) {
                seats.get(i).addActionListener(seatClick(i));
            }
            seats_layout.add(seats.get(i));
        }
        center.add(seats_layout, BorderLayout.CENTER);

        JPanel leggenda = new JPanel(new GridLayout(0, 2));
        JLabel prezzo_normale = new JLabel("Prezzo: " + screening.getPrice() + "€");
        JLabel prezzo_vip = new JLabel("Posti vip costo aggiuntivo di " + config.getVipOverprice() + "€");
        leggenda.add(prezzo_normale);
        leggenda.add(prezzo_vip);

        JPanel total = new JPanel(new BorderLayout(5, 5));
        JLabel totale = new JLabel("TOTALE: ");

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
                openPage(new PageFour(new Booking(0, screening, Taken_seats, null, 0, totale_prezzo, 0, null)));
            }
        });

        sud.add(leggenda);
        sud.add(total);
        sud.add(buttons);

        this.add(nord, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(sud, BorderLayout.SOUTH);
    }

    public ActionListener seatClick(final int i) {
        ActionListener event = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (seats.get(i).isOccupato()) {
                    seats.get(i).setOccupato(false);
                    Taken_seats.remove(Taken_seats.size() - 1);
                    if (seats.get(i).isVip()) {
                        seats.get(i).setIcon(seat_vip);
                        totale_prezzo -= (config.getVipOverprice() + screening.getPrice());
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    } else if (seats.get(i).isHandicap()) {
                        seats.get(i).setIcon(seat_handicap);
                        totale_prezzo -= screening.getPrice();
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    } else {
                        seats.get(i).setIcon(seat_free);
                        totale_prezzo -= screening.getPrice();
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    }
                } else {
                    seats.get(i).setOccupato(true);
                    seats.get(i).setIcon(seat_selezione);
                    Taken_seats.add(seats.get(i));
                    if (seats.get(i).isVip()) {                                  // Per aggiornare il prezzo.
                        totale_prezzo += (config.getVipOverprice() + screening.getPrice());
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    } else {
                        totale_prezzo += screening.getPrice();
                        prezzo.setText(String.valueOf(totale_prezzo) + "€");
                    }
                }
            }
        };
        return event;
    }

    private void goback() {  //ritorna alla pagina selezione film
        this.removeAll();
        this.add(new PageOne());
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

    private void checkTakenSeats() {
        try {
            booked_seats.clear();
            booked_seats = controller.getTakenSeats(screening.getIdProjection());
        } catch (SQLException ex) {
            Logger.getLogger(PageThree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}