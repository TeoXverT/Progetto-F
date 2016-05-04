/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import oggetti.Config;
import oggetti.Seat;

/**
 *
 * @author Yatin
 */
public class PanelAddHall extends JPanel {

    private int x;
    private int y;
    private ArrayList<Seat> seats;
    public PanelAddHall(Controller_Gestore controller, JLabel outputGrafico) { 
        this.setLayout(new GridLayout(0,2));
        JLabel row_num = new JLabel("Number of rows:");
        JLabel column_num = new JLabel("Number of col:");
        final JTextField row = new JTextField(10);
        final JTextField column = new JTextField(10);
        JButton submit = new JButton("Submit");
        this.add(row_num);
        this.add(row);
        this.add(column_num);
        this.add(column);
        this.add(submit);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                x = Integer.getInteger(row.getText());
                y = Integer.getInteger(column.getText());
                System.out.println(x +" "+ y);
//                creaLayoutPosti();
               // creaPosti();
            }
      });

    }
    public void creaLayoutPosti() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        JPanel nord = new JPanel();
        JPanel sud = new JPanel();
        JPanel seats_layout = new JPanel(new GridLayout(5, 10));
        
        seats = new ArrayList<>();
        ImageIcon s = new ImageIcon("immagini/poltrone/seat_disponibile.png");
        
        for (int i = 0; i < x; i++) {
            for(int j = 0; j<y; j++) {
                Seat seat = new Seat(x,y,s);
                seats.add(seat);
            }   
        }

        for (int i = 0; i < x; i++) {     
            seats_layout.add(seats.get(i));
        }


        sud.add(seats_layout, BorderLayout.CENTER);
        this.add(sud);
        
    }

    public void creaPosti() {
        this.removeAll();
    }
}
