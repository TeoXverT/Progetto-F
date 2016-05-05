/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import com.sun.prism.paint.Color;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import oggetti.ButtonCover;
import oggetti.Film;

/**
 *
 * @author Yoga
 */
public class PanelViewFilm extends JPanel {

    Controller_Gestore controller;
    JLabel outputGrafico;
    JPanel listFilm = new JPanel();
    Component frameErrore;
    Boolean the = true;

    public PanelViewFilm(Controller_Gestore controller, JLabel outputGrafico) {
        this.controller = controller;
        this.outputGrafico = outputGrafico;
        outputGrafico.setText("Caricamento lista film in corso...");

        this.setLayout(new BorderLayout());

        this.add(listFilm, BorderLayout.CENTER);
        ThreadDrawer().start();
    }

    private Thread ThreadDrawer() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    ArrayList<Film> Films = controller.visualizzaFilm(0);
                    JPanel pannello = new JPanel(new GridLayout(0, 3, 15, 15));
                    for (final Film f : Films) {
                        System.out.println("In Download immagine URL: " + f.getLink_copertina());
                        ButtonCover cover = new ButtonCover(f);
                        cover.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                drawDetail(f);
                            }
                        });
                        pannello.add(cover);
                        aggiornaGui(pannello);

                        System.out.println("finito caricamento immagini");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frameErrore, "Errore con scaricamento immagini", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frameErrore, "Errore con il server", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        );
        return t;
    }

    public void aggiornaGui(JPanel pannello) {
        if (the) {
            this.removeAll();
            this.setPreferredSize(new Dimension(800, 600));

            JScrollPane scrollPane = new JScrollPane(pannello);

            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            this.add(scrollPane, BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
        }
    }

    void drawDetail(Film film) {
        the = false;
        System.out.println(film.toString());
        this.removeAll();
        JLabel testo = new JLabel(film.toString());
        this.add(testo);
        this.revalidate();
        this.repaint();
    }

}
