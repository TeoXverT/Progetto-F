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
 * @author Yoga
 */
public class PanelViewFilm extends JPanel {

    Controller_Gestore controller;
    JLabel outputGrafico;
    JPanel listaFilm;

    public PanelViewFilm(Controller_Gestore controller, JLabel outputGrafico) {
        this.controller = controller;
        this.outputGrafico = outputGrafico;
        outputGrafico.setText("Caricamento lista film in corso...");

        this.setLayout(new BorderLayout());

    }

}
