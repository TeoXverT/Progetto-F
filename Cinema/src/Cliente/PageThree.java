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
    Controller_Cliente controller;
    Proiezione proiezione;
    
    JButton prosegui = new JButton("PROSEGUI");
    
    public PageThree(Proiezione proiezione, Controller_Cliente controller) throws SQLException {
        this.controller = controller;
        this.proiezione = proiezione;
    }
     
    public void initGui() {
//        this.removeAll();
//        this.setLayout(new BorderLayout(20, 30));
//        JPanel nord = new JPanel();
//        JPanel sud = new JPanel(new BorderLayout(10,20));
//        JPanel seats_layout = new JPanel(new GridLayout(sala.getRows(), sala.getColumns()));
//        JLabel screen = new JLabel(screen_icon);
//
//        nord.add(screen);
//        
//        for(int i=0; i < sala.getSeats().size(); i++) {
//            sala.getSeats().get(i).addActionListener(seatClick(i));
//            seats_layout.add(sala.getSeats().get(i));
//        }
//        
//        sud.add(seats_layout, BorderLayout.CENTER);
// 
//        this.add(nord, BorderLayout.NORTH);
//        this.add(sud, BorderLayout.SOUTH);
//    }
//    
//    public ActionListener seatClick(final int i) {
//        ActionListener event = new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        };
//        return event; 
   }
}
