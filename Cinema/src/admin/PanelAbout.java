/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author yatin
 */

public class PanelAbout extends JPanel {

    public PanelAbout(final AdminController controller, final JLabel outputGrafico) {
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout(1, 10));
        ImageIcon logo_image = new ImageIcon("images/logo_300px.png");
        JLabel logo = new JLabel(logo_image);
        JPanel north = new JPanel(new BorderLayout());
        JPanel south = new JPanel(new BorderLayout());
        
        north.add(logo, BorderLayout.CENTER);
        
        ImageIcon names_image = new ImageIcon("images/names.gif");
        JLabel names = new JLabel(names_image);
        south.add(names, BorderLayout.SOUTH);
        
        this.add(north, BorderLayout.NORTH);
        this.add(south, BorderLayout.SOUTH);

    }
}
