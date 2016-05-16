/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import oggetti.Film;

/**
 *
 * @author Yoga
 */
public class PanelRemover extends JPanel {

    public PanelRemover(Controller_Gestore controller, JLabel outputGrafico) {

        this.setLayout(new FlowLayout());
        try {
            this.add(new JLabel("Film: "));
            final DefaultListModel model = new DefaultListModel();
            final JList<Film> listaFilm = new JList(model);
            JScrollPane pane = new JScrollPane(listaFilm);
            ArrayList<Film> Films;
            Films = controller.visualizzaFilm(0);
            for (Film f : Films) {
                model.addElement(f);
            }
            this.add(pane);
            JButton deleteFilm = new JButton("delete");
            this.add(deleteFilm);

        } catch (SQLException ex) {
            outputGrafico.setText("Errore con il server");
        }

    }
}
