/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Yoga
 */
public class CustomerGui extends JFrame {

    JPanel display = new JPanel(new BorderLayout());

    public CustomerGui() {
        draw();
    }

    public void draw() {
        this.setTitle("Customer");
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 920, 770);
        ImageIcon icon = new ImageIcon("immagini/logo_trasparente.png");
        setIconImage(icon.getImage());

        this.add(display);
        display.add(new PageOne());
    }

}
