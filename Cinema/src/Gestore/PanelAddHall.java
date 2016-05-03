/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import oggetti.Seat;

/**
 *
 * @author Yatin
 */
public class PanelAddHall extends JPanel {
    
    public PanelAddHall(Controller_Gestore controller, JLabel outputGrafico) {
        
        this.setLayout(new BorderLayout());
        JPanel nord = new JPanel();
        JPanel sud = new JPanel();
        JPanel seats_layout = new JPanel(new GridLayout(5, 5, 1, 2));
        
        ImageIcon s = new ImageIcon("immagini/poltrone/seat_n.png");
        JLabel[] seats = new JLabel[24];
        for(int i = 0; i < 24; i++){
                seats[i] = new JLabel(s);
            }
        
//        System.out.println(seats.length);
//        seats_layout.add(seats[0]);
//        seats_layout.add(seats[1]);
//        seat.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent m) {
//                System.out.println("CLICCATO");
//            }
//        });
        for(int i = 0; i < 24; i++) {
            seats_layout.add(seats[i]);
        }
        
        sud.add(seats_layout, BorderLayout.CENTER);
        this.add(sud);
        
    }
    
    public void creaPosti() {
        
    }
}
