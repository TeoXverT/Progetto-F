package customer;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import obj.Config;
import obj.Booking;
import obj.Projection;
import obj.Hall;
import obj.Seat;

public class PageThree extends JPanel {
    private CustomerController controller;
    private double totale_prezzo;
    private Component frameErrore;
    private JLabel prezzo = new JLabel();
    
    public PageThree(Projection proiezione) {
        this.controller = CustomerController.getInstance();
        initGui(proiezione);
    }

    public void initGui(Projection screening) {
        try {
            ImageIcon screen_icon = new ImageIcon("images/hall/screen.png");
            ImageIcon seat_taken = new ImageIcon("images/hall/seat_taken.png");
            ImageIcon seat_vip = new ImageIcon("images/hall/seat_vip.png");
            ImageIcon seat_handicap = new ImageIcon("images/hall/seat_handicap.png");
            ImageIcon seat_free = new ImageIcon("images/hall/seat_free.png");
            ImageIcon seat_selezione = new ImageIcon("images/hall/seat_selezione.png");
            ArrayList<Seat> seats = new ArrayList<>();
            ArrayList<Seat> booked_seats = new ArrayList<>();
            ArrayList<Seat> Taken_seats = new ArrayList<>();
            Booking booking = null;
            
            
            Config config = controller.getConfig();
            Hall hall = controller.getHallByIdHall(screening.getRoom().getIdHall());
            seats = controller.getSeatsByIdHall(screening.getRoom().getIdHall());
            
            this.removeAll();
            this.setLayout(new BorderLayout(20, 30));
            
            JPanel nord = new JPanel();
            nord.setBackground(java.awt.Color.WHITE);
            
            JPanel sud = new JPanel(new GridLayout(0, 2));
            JPanel center = new JPanel(new BorderLayout());
            
            JPanel seats_layout = new JPanel(new GridLayout(hall.getRows(), hall.getColumns(), 0, 1));
            
            JLabel screen = new JLabel(screen_icon);
            nord.add(screen);
            checkTakenSeats(booked_seats, screening);
            
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
                    seats.get(i).addActionListener(seatClick(i, seat_vip, seat_handicap, seat_free, seat_selezione, seats, Taken_seats, screening, config));
                }
                seats_layout.add(seats.get(i));
            }
            center.add(seats_layout, BorderLayout.CENTER);
            
            JPanel leggenda = new JPanel(new BorderLayout());
            ImageIcon Seat_description = new ImageIcon("images/Leggenda.png");
            JLabel legend = new JLabel(Seat_description);
            leggenda.add(legend, BorderLayout.WEST);
            
            JPanel total = new JPanel(new GridLayout(2, 2));
            JLabel totale = new JLabel("TOTALE: ");
            JButton indietro = new JButton("indietro");
            JButton prosegui = new JButton("PROSEGUI");
            total.add(totale, BorderLayout.WEST);
            total.add(prezzo, BorderLayout.EAST);
            total.add(indietro);
            total.add(prosegui);
            
            indietro.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent ae) {
                    goback();
                }
            });
            
            prosegui.addActionListener(proseguiClick(Taken_seats, booking, screening));
            
            sud.add(leggenda);
            sud.add(total);
            
            this.add(nord, BorderLayout.NORTH);
            this.add(center, BorderLayout.CENTER);
            this.add(sud, BorderLayout.SOUTH);
        } catch (SQLException ex) {
            Logger.getLogger(PageThree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    private ActionListener proseguiClick(final ArrayList<Seat> Taken_seats, final Booking booking, final Projection screening) {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cartControl(Taken_seats, booking, screening);
            }
        };
        return event;
    }

    private void cartControl(ArrayList<Seat> Taken_seats, Booking booking, Projection screening) {  //  questo metodo ha il compito di controllare 
        if (Taken_seats.size() != 0) {
            booking = new Booking(0, screening, Taken_seats, null, 0, totale_prezzo, 0, null);
            openPage(new PageFour(booking));
        } else {
            JOptionPane.showMessageDialog(null,
                    "Please select the seat.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private ActionListener seatClick(final int i, final ImageIcon seat_vip, final ImageIcon seat_handicap, final ImageIcon seat_free, final ImageIcon seat_selezione,final ArrayList<Seat> seats,
            final ArrayList<Seat> Taken_seats, final Projection screening, final Config config) {
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

    private void checkTakenSeats(ArrayList<Seat> booked_seats, Projection screening) {
        try {
            booked_seats.clear();
            booked_seats = controller.getTakenSeats(screening.getIdProjection());
        } catch (SQLException ex) {
            Logger.getLogger(PageThree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
