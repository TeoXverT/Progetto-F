/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import static com.sun.webkit.graphics.WCImage.getImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Icon;
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
        this.setLayout(new GridLayout(0, 2));
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
        this.setLayout(new BorderLayout(0, 20));
        JPanel nord = new JPanel();
        JPanel sud = new JPanel();
        JPanel seats_layout = new JPanel(new GridLayout(0, 10));
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("cinema/immagini/poltrone/screen.png"));
        } catch (IOException e) {
        }
        JLabel screen = new JLabel();
        screen.setIcon((Icon) img);
        nord.add(screen);
        sud.setBackground(Color.BLACK);

        seats = new ArrayList<>();
        ImageIcon icon = new ImageIcon("immagini/poltrone/seat_disponibile.png");

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                seats.add(new Seat(i, j, icon));
            }
        }

        for (Seat s : seats) {
            seats_layout.add(s);
        }

        sud.add(seats_layout);
        this.add(nord, BorderLayout.NORTH);
        this.add(sud, BorderLayout.SOUTH);
    }

    public void creaPosti() {
        this.removeAll();
    }
}
