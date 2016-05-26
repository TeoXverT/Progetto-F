/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
 *
 * @author NEVERMIND
 */
public class PanelSalesVolume extends JPanel {
    
    public PanelSalesVolume(final Controller_Gestore controller, final JLabel outputGrafico) {
    JPanel North = new JPanel(new GridLayout(1,5));
    JSpinner dateFrom = new JSpinner();
    North.add(new JLabel("From: "));
    North.add(dateFrom);
    North.add(new JLabel("To: "));
    
    
    
    this.add(North);
    }
}
