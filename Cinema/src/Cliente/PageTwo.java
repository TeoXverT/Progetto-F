/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import oggetti.Film;

/**
 *
 * @author Riccardo
 */
public class PageTwo extends JPanel {

    Controller_Cliente controller;

    public PageTwo(Film film, int deltaData, Controller_Cliente controller) {
        this.controller = controller;
        this.setLayout(new GridLayout(0, 2));
        this.add(new JLabel(film.toString()));
        this.add(new JLabel(" " + deltaData));

    }

}
