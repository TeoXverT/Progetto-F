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
import javax.swing.JPanel;
import obj.Projection;
import obj.Hall;
import obj.Seat;

/**
 *
 * @author yatin
 */
public class PanelHallState extends JPanel {

    AdminController controller;
    CustomerController controller_cliente = new CustomerController();
    JLabel outputGrafico;
    ArrayList<Projection> proiezioni;
    private ArrayList<Seat> seats;
    private ArrayList<Seat> bookedSeats;
    int id_sala;
    Hall sala;
    private ImageIcon seat_taken = new ImageIcon("images/hall/seat_taken.png");

    public PanelHallState(final AdminController controller, final JLabel outputGrafico, int id_sala) {
        try {
            this.controller = controller;
            this.outputGrafico = outputGrafico;
            this.id_sala = id_sala;

            sala = controller.getHall(id_sala);
            init();
        } catch (SQLException ex) {
            outputGrafico.setText("Errore letture dati dal database.");
        }
    }

    private void init() {
        this.removeAll();
        try {
            proiezioni = controller.getTodayProjectionByHall(id_sala);     // vado a prendere tutte le proiezioni in un sala
            JPanel container = new JPanel(new GridLayout(0, 4));
            ArrayList<JButton> show = new ArrayList<>();    // uso per rappresentare tutte le proiezioni

            for (int i = 0; i < proiezioni.size(); i++) {   // questo ciclo visualizza tutte le proiezioni e aggiunge gli actionListener
                show.add(new JButton("SHOW " + proiezioni.get(i).getIdProjection()));
                show.get(i).addActionListener(showClick(proiezioni.get(i).getIdProjection()));
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