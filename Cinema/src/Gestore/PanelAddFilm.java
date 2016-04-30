/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Yoga
 */
public class PanelAddFilm extends JPanel {

    public PanelAddFilm(Controller_Gestore controller, JLabel outputGrafico) {
        this.setLayout(new GridLayout(0, 2, 1, 50));

        JLabel titoloText = new JLabel("Titolo: ");
        JLabel genereText = new JLabel("Genere: ");
        JLabel durataText = new JLabel("Durata: ");
        JLabel descrizioneText = new JLabel("Descrione: ");
        JLabel linkText = new JLabel("Link: ");
        JLabel copertinaText = new JLabel("Copertina: ");
        JTextField titoloField = new JTextField("inserisci qui il titolo del cazzo");
        JTextField genereField = new JTextField("ciao", 30);
        final JTextField durataField = new JTextField("90");
        JTextArea descrizioneArea = new JTextArea(1, 1);
        JTextField linkField = new JTextField();
        JTextField copertinaField = new JTextField();
        JButton plus = new JButton("+"); //incrementa durata
        JButton less = new JButton("-"); //decrementa durata
        JPanel durata = new JPanel(new GridLayout(0, 3));
        durata.add(plus);
        durata.add(durataField);
        durata.add(less);
        //--------------------------------------------------------------
        this.add(titoloText);
        this.add(titoloField);
        this.add(genereText);
        this.add(genereField);
        this.add(durataText);
        this.add(durata);
        this.add(descrizioneText);
        this.add(descrizioneArea);
        this.add(linkText);
        this.add(linkField);

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

    }

}
