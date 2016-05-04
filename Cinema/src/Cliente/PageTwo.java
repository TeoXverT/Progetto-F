/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import oggetti.Film;

/**
 *
 * @author Riccardo
 */
public class PageTwo extends JPanel {

    Controller_Cliente controller;
    Film film;
    public PageTwo(Film film, int deltaData, Controller_Cliente controller) throws SQLException {
        this.controller = controller;
        this.setLayout(new GridLayout(0, 2));
        this.add(new JLabel(film.toString()));
        this.add(new JLabel(" " + deltaData));
        /*this.film=film;
        this.controller.InformazioniFilm(film.getId_film());
        this.add(new JLabel(film.getLink_copertina()));
        //getLink_youtube
        this.add(new JLabel(film.getDescrizione()));
        this.add(new JLabel(film.getTitolo_film()));
        */
    }
    
}



