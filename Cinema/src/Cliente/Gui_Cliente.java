/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Yoga
 */
public class Gui_Cliente extends JFrame {

    Controller_Cliente controller = new Controller_Cliente();
    
    public Gui_Cliente() {
        Create_Gui();

    }

    public void Create_Gui() {

        this.setTitle("Pannello Cliente");
        this.setLayout(new GridLayout(0, 1, 0, 3));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 795, 700);
        ImageIcon icona = new ImageIcon("immagini/logo_trasparente.png");
        setIconImage(icona.getImage());
//        this.setResizable(false);
        this.add(new PageOne(controller));
    } 
}
