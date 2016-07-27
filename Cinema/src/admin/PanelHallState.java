package admin;

import customer.CustomerController;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import obj.Projection;
import obj.Hall;
import obj.Seat;

/**
 *
 * @author yatin
 */
public class PanelHallState extends JPanel {

    private AdminController controller;
    private JLabel outputGrafico;
    private ImageIcon seat_taken = new ImageIcon("images/hall/seat_taken.png");

    public PanelHallState(final AdminController controller, final JLabel outputGrafico) {
        this.controller = controller;
        this.outputGrafico = outputGrafico;
        drawHallList();
    }

    private void drawHallList() {
        try {
            this.removeAll();
            ArrayList<Hall> hall = new ArrayList<>();
            ArrayList<JButton> hall_buttons = new ArrayList<>();
            hall = controller.getHall();
            JPanel hallList = new JPanel(new GridLayout(0, 5));

            for (int i = 0; i < hall.size(); i++) {
                hall_buttons.add(new JButton("Hall " + hall.get(i).getIdHall()));
                hallList.add(hall_buttons.get(i));
                hall_buttons.get(i).addActionListener(hallClick(hall.get(i).getIdHall()));
            }
            
            outputGrafico.setText("Done.");
            this.add(hallList);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Someting goes wrong with server.", "Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    private ActionListener hallClick(final int hallId) {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                drawProjections(hallId);
            }
        };
        return event;
    }

    private void drawProjections(int hallId) {
        try {
            this.removeAll();
            this.setLayout(new BorderLayout(5,100));
            ArrayList<Projection> proiezioni;

            proiezioni = controller.getTodayProjectionByHall(hallId);     
            JPanel container = new JPanel(new GridLayout(0, 4));
            ArrayList<JButton> show = new ArrayList<>();  
            
            for (int i = 0; i < proiezioni.size(); i++) {  
                show.add(new JButton("SHOW " + proiezioni.get(i).getIdProjection()));
                show.get(i).addActionListener(showClick(proiezioni.get(i).getIdProjection(), hallId));
                container.add(show.get(i));
            }
            JButton backToHall = new JButton("BACK");
            backToHall.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    goBackToHallList();
                }
            });
            this.add(container, BorderLayout.CENTER);
            this.add(backToHall, BorderLayout.SOUTH);
            this.revalidate();
            this.repaint();
        } catch (SQLException ex) {
            outputGrafico.setText("Errore con il server");
        }

    }

    private ActionListener showClick(final int id_proiezione, final int hallId) {         // questo metodo è per gestire l'actionListener associato alle proiezioni.
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                drawLayout(id_proiezione, hallId);
            }
        };
        return event;
    }

    private void drawLayout(int id_proiezione, final int hallId) {
        try {
            ArrayList<Seat> seats;
            ArrayList<Seat> bookedSeats;
            Hall hall = controller.getHall(hallId);

            this.removeAll();
            this.setLayout(new BorderLayout());
            JPanel seats_layout = new JPanel(new GridLayout(hall.getRows(), hall.getColumns(),0,1));
            seats = controller.getSeatsByIdHall(hallId);
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
                    goBackToShowList(hallId);
                }
            });
            this.add(back, BorderLayout.SOUTH);
            this.revalidate();
            this.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(PanelHallState.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void goBackToShowList(int hallId) {
        this.removeAll();
        this.setLayout(new BorderLayout());
        drawProjections(hallId);
        this.revalidate();
        this.repaint();
    }
    
    private void goBackToHallList() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        drawHallList();
        this.revalidate();
        this.repaint();
    }
}
