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
import oggetti.Proiezione;
import oggetti.Sala;
import oggetti.Seat;

/**
 *
 * @author Yatin
 */
public class PageThree extends JPanel{
    ImageIcon screen_icon = new ImageIcon("immagini/poltrone/screen.png");
    ImageIcon seat_taken = new ImageIcon("immagini/poltrone/seat_taken.png");
    Controller_Cliente controller;
    Proiezione proiezione;
    
    JButton prosegui = new JButton("PROSEGUI");
    Sala sala;
    ArrayList<Seat> seats;
    
    public PageThree(Proiezione proiezione, Controller_Cliente controller) throws SQLException {
        this.controller = controller;
        this.proiezione = proiezione;
        this.sala = controller.salaByID(proiezione.getId_sala());
        seats = new ArrayList<>();
        seats = controller.getSeats(proiezione.getId_sala());
        initGui();
        
    }
 
    public void initGui() {
        this.removeAll();
        this.setLayout(new BorderLayout(20,30));
        JPanel nord = new JPanel();
        JPanel sud = new JPanel(new BorderLayout());
        JPanel center = new JPanel();
        JPanel seats_layout = new JPanel(new GridLayout(sala.getRows(), sala.getColumns(),1,1));
        JLabel screen = new JLabel(screen_icon);

        nord.add(screen);
        
        for(int i=0; i < seats.size(); i++) {
            seats.get(i).addActionListener(seatClick(i));
            seats_layout.add(seats.get(i));
        }
        
        sud.add(seats_layout, BorderLayout.CENTER);
 
        this.add(nord, BorderLayout.NORTH);
        this.add(sud, BorderLayout.CENTER);
    }
    
    // questo metodo serve per prenotare i posti.
    public ActionListener seatClick(final int i) {
        ActionListener event = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                seats.get(i).setIcon(seat_taken);
            }
        };
        return event; 
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
