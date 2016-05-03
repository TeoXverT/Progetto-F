/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author cl421572
 */

public class PanelAbout extends JPanel {

    public PanelAbout(final Controller_Gestore controller, final JLabel outputGrafico) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 20, 20));
        ImageIcon immagine = new ImageIcon("immagini/logo_trasparente.png");
        String info = "PROGETTO F - v1.0 \n" + " \n";
        info += "Sviluppato da: \n";
        info += "Umeer Mohammad \n";
        info += "Riccardo Previtali \n";
        info += "Cristian Santangelo \n";
        info += "Yatin Bhutani \n";
        info += "Federico Avarino \n";
        JTextArea infos = new JTextArea("");
        infos.setFont(new Font("Courier", Font.ROMAN_BASELINE, 24));
        //infos.setPreferredSize(new java.awt.Dimension(384, 129));
        infos.setWrapStyleWord(true); //dovrebbe settare a capo automatico
        infos.setText(info);
        panel.add(new JLabel(scalaImmagine(immagine, 640 / 3, 433 / 3)));
        panel.add(infos);
        this.add(panel);
    }
        
   public ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }

}
