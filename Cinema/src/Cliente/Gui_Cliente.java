/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Yoga
 */
public class Gui_Cliente extends JFrame {

    JPanel display = new JPanel(new BorderLayout());

    public Gui_Cliente() {
        Create_Gui();
    }

    public void Create_Gui() {
        this.setTitle("Pannello Cliente");
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 920, 770);
        ImageIcon icona = new ImageIcon("immagini/logo_trasparente.png");
        setIconImage(icona.getImage());
//        this.setResizable(false);
        this.add(display);
        display.add(new PageOne());
    }

}
