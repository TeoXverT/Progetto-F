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
        Center.setLayout(new GridLayout(0, 2, 1, 50));

        JLabel titoloText = new JLabel("Titolo: ");
        JLabel genereText = new JLabel("Genere: ");
        JLabel durataText = new JLabel("Durata: ");
        JLabel descrizioneText = new JLabel("Descrione: ");
        JLabel linkText = new JLabel("Link Trailer: ");
        JLabel copertinaText = new JLabel("Immagine Copertina: ");
        final JTextField titoloField = new JTextField("inserisci qui il titolo del cazzo");
        final JTextField genereField = new JTextField("ciao", 30);
        final JTextField durataField = new JTextField("90");
        final JTextArea descrizioneArea = new JTextArea(1, 1);
        final JTextField linkField = new JTextField();
        final JTextField copertinaField = new JTextField();
        JButton addMovie = new JButton("ADD MOVIE");
        //-durata+
        JButton plus = new JButton("+"); //incrementa durata
        JButton less = new JButton("-"); //decrementa durata
        JPanel durata = new JPanel(new GridLayout(0, 3));
        durata.add(less);
        durata.add(durataField);
        durata.add(plus);
        //--------------------------------------------------------------
        Center.add(copertinaText);
        Center.add(copertinaField);
        Center.add(titoloText);
        Center.add(titoloField);
        Center.add(genereText);
        Center.add(genereField);
        Center.add(durataText);
        Center.add(durata);
        Center.add(descrizioneText);
        Center.add(descrizioneArea);
        Center.add(linkText);
        Center.add(linkField);
        Center.add(addMovie);
        this.add(Center);
//**************************************************************ACTION PERFORMED
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
