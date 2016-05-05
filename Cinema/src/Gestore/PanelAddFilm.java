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

    //Mi serve che tu prenda il link di youtube, nel formato che si vede nel url di un comune brosware una volta aperto il video, 
    //e che melo trasformi in un formato alternativo prima di scriverlo sul DB ti faccio un esempio :
    //Parti da: https://www.youtube.com/watch?v=yqN7nHM1YTA e devi ottenere: https://www.youtube.com/v/yqN7nHM1YTA?autoplay=1
    //come puoi notare il codice associato al video di youtube è yqN7nHM1YTA
    
    //Metti un filtro che blocca il caricamento dei film vuoti, visualizzando il risultato su outputGrafico
    
    //Come ti ho detto anche a voce, fai un una classe tipo enum di tutti i generi,
    // e poi modifica il codice con enum ovunque ci sia il genere, miracomando anche il parser
    
    /*
    public enum Sale {

    SALONE_MEDIEVALE, SALONE_RINASCIMENTALE, SALA_INQUISIZIONE, SALA_SCIENZE_MOTORIE, SALA_TECNOLOGIE_MODERNE;

    public static Sale parserSale(String parola) {

        if (parola.compareTo("SALA_INQUISIZIONE") == 0) {
            return Sale.SALA_INQUISIZIONE;
        }
        if (parola.compareTo("SALONE_RINASCIMENTALE") == 0) {
            return Sale.SALONE_RINASCIMENTALE;
        }
        if (parola.compareTo("SALONE_MEDIEVALE") == 0) {
            return Sale.SALONE_MEDIEVALE;
        }
        if (parola.compareTo("SALA_SCIENZE_MOTORIE") == 0) {
            return Sale.SALA_SCIENZE_MOTORIE;
        }
        if (parola.compareTo("SALA_TECNOLOGIE_MODERNE") == 0) {
            return Sale.SALA_TECNOLOGIE_MODERNE;
        }
        return null;
    }

}
    */
    
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
