/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import oggetti.Config;
import oggetti.Booking;

/**
 *
 * @author Yoga
 */
public class Gui_Cliente extends JFrame {

    JPanel display = new JPanel(new BorderLayout());
    Controller_Cliente controller = Controller_Cliente.getInstance();

    public Gui_Cliente() {

        Create_Gui();

    }

    public void Create_Gui() {

        this.setTitle("Pannello Cliente");
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 920, 700);
        ImageIcon icona = new ImageIcon("immagini/logo_trasparente.png");
        setIconImage(icona.getImage());
//        this.setResizable(false);
        this.add(display);
        display.add(new PageOne());
    }

}
