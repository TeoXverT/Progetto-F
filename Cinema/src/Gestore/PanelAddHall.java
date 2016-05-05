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
    private ArrayList<Seat> seats = null;
    public PanelAddHall(Controller_Gestore controller, final JLabel outputGrafico) { 
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
                outputGrafico.setText("Visualizzazione Layout in Corso..");
                x = Integer.parseInt(row.getText());
                y = Integer.parseInt(column.getText());
                creaLayoutPosti();
            }
        });
    }
    
    public void creaLayoutPosti() {
        
        this.removeAll();
        this.setLayout(new GridLayout(x,y));
        JPanel nord = new JPanel();
        JPanel sud = new JPanel();
        JPanel seats_layout = new JPanel(new GridLayout(x , y));
//        JLabel prova = new JLabel("CIAOOOOOOOO");
        //this.add(sud);
        
        seats = new ArrayList<>();
        ImageIcon s = new ImageIcon("immagini/poltrone/seat_disponibile.png");
        
        for (int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
               seats.add(new Seat(i,j,s));
            }   
        }
        this.add(seats.get(0));
        this.add(seats.get(1));
//        for (int i = 0; i < x*y; i++) {   
//            this.add(seats.get(i));
//        }        

        //sud.add(seats_layout);
    }

    public void creaPosti() {
        this.removeAll();
    }
}
