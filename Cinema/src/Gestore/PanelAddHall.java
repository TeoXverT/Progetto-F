/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

//import static com.sun.webkit.graphics.WCImage.getImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
    ImageIcon seat_free = new ImageIcon("immagini/poltrone/seat_free.png");
    ImageIcon seat_disable = new ImageIcon("immagini/poltrone/seat_diasable.png");
    ImageIcon seat_vip = new ImageIcon("immagini/poltrone/seat_vip.png");
    ImageIcon seat_handicap = new ImageIcon("immagini/poltrone/seat_handicap.png");
    ImageIcon screen_icon = new ImageIcon("immagini/poltrone/screen.png");
    JComboBox seat_type;

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
        this.setLayout(new BorderLayout(20, 30));
        JPanel nord = new JPanel();
        JPanel sud = new JPanel(new BorderLayout(10,20));
        JPanel west = new JPanel();
        JPanel seats_layout = new JPanel(new GridLayout(x,y,0,4));
        JLabel screen = new JLabel(screen_icon);

        nord.add(screen);
        
        seats = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                seats.add(new Seat(i, j, seat_free));
            }
        }
        
        for(int i=0; i<seats.size(); i++) {
            seats.get(i).addActionListener(seatClick(i));
            seats_layout.add(seats.get(i));
        }
        
        JButton create_hall = new JButton("Create Hall");
        create_hall.addActionListener(createHall());
        sud.add(seats_layout, BorderLayout.CENTER);
        sud.add(create_hall, BorderLayout.SOUTH);
        String[] type_list = {"Disable", "Vip", "Handicap", "Free"};
        seat_type = new JComboBox(type_list);
        west.add(seat_type);
 
        this.add(nord, BorderLayout.NORTH);
        this.add(sud, BorderLayout.SOUTH);
        this.add(west, BorderLayout.CENTER);
    }
    
    public ActionListener seatClick(final int i) {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch ((String)seat_type.getSelectedItem()) {
                    case "Disable":
                        seats.get(i).setIcon(seat_disable);
                        break;
                    case "Vip":
                        seats.get(i).setIcon(seat_vip);
                        break;
                    case "Handicap":
                        seats.get(i).setIcon(seat_handicap);
                        break;
                    case "Free":
                        seats.get(i).setIcon(seat_free);
                }        
            }
        };
       return event;
    }
    
    public ActionListener createHall() {
        ActionListener event = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return event; 
    }
}
