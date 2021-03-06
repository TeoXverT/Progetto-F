package gui.admin;

import core.AdminController;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import gui.obj.ButtonCover;
import core.obj.Film;
import gui.obj.YoutubePanel;

/**
 * Pannello contenente la lista completa di tutti i film,
 *          l'interfaccia permette tramite un click sulla copertina desiderata di vedere le informaioni realtive al film
 * 
 */
public class PanelViewFilm extends JPanel {

    private AdminController controller;
    private JLabel outputGrafico;
    private JPanel listFilm = new JPanel();
    private Component frameErrore;
    private Boolean threadSync = true;

    public PanelViewFilm(AdminController controller, JLabel outputGrafico) {
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
                    ArrayList<Film> Films = controller.getFilm(0);
                    JPanel pannello = new JPanel(new GridLayout(0, 3, 15, 15));
                    for (final Film f : Films) {
//                        System.out.println("In Download immagine URL: " + f.getLink_copertina());
                        ButtonCover cover = new ButtonCover(f);
                        cover.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                drawDetail(f);
                            }
                        });
                        if (threadSync == false) {
                            break;
                        }
                        pannello.add(cover);
                        drawList(pannello);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frameErrore, "Errore con scaricamento immagini", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frameErrore, "Errore con il server", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
                }
                outputGrafico.setText("Caricamento lista film completato.");

            }
        }
        );
        return t;
    }

    private void drawList(JPanel pannello) {

        this.removeAll();
        this.setPreferredSize(new Dimension(800, 600));

        JScrollPane scrollPane = new JScrollPane(pannello);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();

    }

    private void drawDetail(Film film) {
        threadSync = false;

        this.removeAll();
        outputGrafico.setText("");
        this.setLayout(new BorderLayout());

        JPanel nord = new JPanel(new GridLayout(1, 2, 30, 30));
        ImageIcon immagine = null;
        try {
            immagine = new ImageIcon(ImageIO.read(new URL(film.getLinkCover())));
        } catch (IOException ex) {
            Logger.getLogger(PanelViewFilm.class.getName()).log(Level.SEVERE, null, ex);
        }
        nord.add(new JLabel(new ImageIcon(immagine.getImage().getScaledInstance(230, 320, java.awt.Image.SCALE_SMOOTH))));
        nord.add(new YoutubePanel(film.getLinkYoutube(), 100, 100));

        JPanel centro = new JPanel();
        centro.setLayout(new GridLayout(0, 2, 12, 12));
        centro.add(new JLabel("ID:", SwingConstants.RIGHT));
        centro.add(new JLabel("" + film.getIdFilm(), SwingConstants.LEFT));
        centro.add(new JLabel("Titolo:", SwingConstants.RIGHT));
        centro.add(new JLabel(film.getTitle(), SwingConstants.LEFT));
        centro.add(new JLabel("Genere:", SwingConstants.RIGHT));
        centro.add(new JLabel(film.getCategories(), SwingConstants.LEFT));
        centro.add(new JLabel("Durata:", SwingConstants.RIGHT));
        centro.add(new JLabel("" + film.getLength() + " Min.", SwingConstants.LEFT));
        centro.add(new JLabel("Data Caricamento:", SwingConstants.RIGHT));
        centro.add(new JLabel("" + film.getDateTime().getTime(), SwingConstants.LEFT));

        JPanel sud = new JPanel(new GridLayout(0, 1));
        sud.add(new JLabel("Descrizione:", SwingConstants.CENTER));

        JTextArea textArea = new JTextArea(film.getDescription(), 2, 2);
        textArea.setFont(new Font("Serif", Font.ITALIC, 15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);

        sud.add(textArea);

        this.add(nord, BorderLayout.NORTH);
        this.add(centro, BorderLayout.CENTER);
        this.add(sud, BorderLayout.SOUTH);

        this.revalidate();
        this.repaint();
    }

}
