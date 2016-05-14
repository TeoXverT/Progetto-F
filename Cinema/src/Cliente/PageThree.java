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
    
    public PageThree(Proiezione proiezione) throws SQLException {
        
        
        //bisogna implementare il metodo che presa l'id sala dalla Proiezione del costruttore vada a confrontarla con gli id sala 
        //presenti sul database e restituisca le caratteristiche della sala e le associ a una variabile "Sala sala"
       
        System.out.println("fin qui va, per cancellare riga 36 del PageThree");
        controller = new Controller_Cliente();
        Sala sala;
        //in teoria restituisce la sala ma per adesso non funziona
        sala = controller.salaByID(proiezione.getId_sala());
        
        
        this.removeAll();
        this.setLayout(new BorderLayout(20, 30));
        JPanel nord = new JPanel();
        JPanel sud = new JPanel(new BorderLayout(10,20));
        JPanel west = new JPanel();
        JPanel seats_layout = new JPanel(new GridLayout(sala.getRows(), sala.getColumns(), 1, 5));
        JLabel screen = new JLabel(screen_icon);

        nord.add(screen);
        
        for(int i=0; i < sala.getSeats().size(); i++) {
            sala.getSeats().get(i).addActionListener(seatClick(i));
            seats_layout.add(sala.getSeats().get(i));
        }
        
        sud.add(seats_layout, BorderLayout.CENTER);
 
        this.add(nord, BorderLayout.NORTH);
        this.add(sud, BorderLayout.SOUTH);
        this.add(west, BorderLayout.CENTER);
    }
    
    public ActionListener seatClick(final int i) {
        ActionListener event = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return event; 
    }
}
