/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Yatin
 */
public class PanelAddHall extends JPanel {
    
    public PanelAddHall(Controller_Gestore controller, JLabel outputGrafico) {
        this.setLayout(new BorderLayout());
        JPanel nord = new JPanel();
        JPanel sud = new JPanel();
        JPanel seats_layout = new JPanel();
        
        sud.add(seats_layout, BorderLayout.CENTER);
        
        this.add(sud);
    }
}
