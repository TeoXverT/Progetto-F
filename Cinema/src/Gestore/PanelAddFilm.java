/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import oggetti.Film;

/**
 *
 * @author Yoga
 */
public class PanelAddFilm extends JPanel {

    public PanelAddFilm(final Controller_Gestore controller, final JLabel outputGrafico) {
        JPanel Center = new JPanel();
        JPanel North = new JPanel();
        
        Center.setLayout(new GridLayout(0, 2, 1, 50));
        North.setLayout(new GridLayout(0, 2, 1, 50));
        
        final JTextField titoloField = new JTextField("", 30);
        final JTextField genereField = new JTextField("", 30);
        final JTextField durataField = new JTextField("90");
        final JTextArea descrizioneArea = new JTextArea(1, 1);
        final JTextField linkField = new JTextField();
        final JTextField copertinaField = new JTextField();
        JButton addMovie = new JButton("ADD MOVIE");

        //Building -durata+
        JButton plus = new JButton("+"); //incrementa durata
        JButton less = new JButton("-"); //decrementa durata
        JPanel durata = new JPanel(new GridLayout(0, 3));
        durata.add(less);
        durata.add(durataField);
        durata.add(plus);
        
        //-----Building Panel Center
        Center.add(new JLabel("Immagine Copertina: "));
        Center.add(copertinaField);
        Center.add(new JLabel("Titolo: "));
        Center.add(titoloField);
        Center.add(new JLabel("Genere: "));
        Center.add(genereField);
        Center.add(new JLabel("Durata: "));
        Center.add(durata);
        Center.add(new JLabel("Descrione: "));
        Center.add(descrizioneArea);
        Center.add(new JLabel("Link Trailer: "));
        Center.add(linkField);
        Center.add(addMovie);
        
        //------Building this
        this.add(Center);
        
        //******ACTION PERFORMED
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int durataInt = Integer.parseInt(durataField.getText()) + 1;
                durataField.setText("" + durataInt);
            }
        });

        less.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int durataInt = Integer.parseInt(durataField.getText()) - 1;
                durataField.setText("" + durataInt);
            }
        });
        
        addMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.scriviFilm(new Film(0, titoloField.getText(), genereField.getText(), Integer.parseInt(durataField.getText()), descrizioneArea.getText(), linkField.getText(), copertinaField.getText()))) {
                    outputGrafico.setText("Modifica registrata con successo.");
                } else {
                    outputGrafico.setText("Errore durante il caricamento dei dati.");
                }
            }
        });
    }

}
