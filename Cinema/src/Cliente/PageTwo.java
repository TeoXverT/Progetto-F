package Cliente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import oggetti.Config;
import oggetti.Film;
import oggetti.YoutubePanel;
import oggetti.Booking;
import oggetti.Screening;
import oggetti.Seat;

/**
 *
 * @author Riccardo
 */
public class PageTwo extends JPanel {

    private Controller_Cliente controller;
    private Film film;
    private ArrayList<Screening> proiezione;
    private Screening proiezione1;
    private Calendar focusedDateTime;

    public PageTwo(Film film, Calendar focusedDateTime) {
        //Il controller ti permettera di parlare con il database
        //deltaData è l'offset in giorni rispetto ad oggi, ad es: se oggi è lunedi ed il tab da dove viene selezionato il film è martedi allora questo valore vale 1
        //deltaData è temporaneo in attesa dello slider  laterale di selezione del orario        
        //film è proprio il film che viene clickato con tutti i dati del caso, guarda i suoi metodi
        //Tè dovrai eseguire una query per scoprire in quali orari viene proiettatto questo film il giorno oggi+deltaData 
        this.controller = Controller_Cliente.getInstance();
        this.film = film;
        this.focusedDateTime = focusedDateTime;
        this.setLayout(new GridLayout(2, 2, 10, 10));
        // this.setBorder(new EmptyBorder(10, 30, 10, 10)); // Da problemi... meglio non avere i bordi.

        draw();
    }

    private void draw() {

        JButton cover = new JButton();
        cover.setIcon(scalaImmagine(new ImageIcon("immagini/home.png"), 30, 30));
        cover.setBorderPainted(true);
        cover.setContentAreaFilled(false);

        JPanel pannelloCopertina = new JPanel();
        pannelloCopertina.setBackground(java.awt.Color.WHITE);
        this.add(pannelloCopertina);
        Image image = null;
        try {
            URL url = new URL(film.getLink_copertina());
            image = ImageIO.read(url);
        } catch (IOException e) {
        }
        ImageIcon ii = new ImageIcon(image);
        JLabel label1 = new JLabel(scalaImmagine(ii, 250, 350));
        pannelloCopertina.add(label1);

        cover.addActionListener(goBackEvent());

        JPanel pannelloTrama = new JPanel(new BorderLayout(10, 10));
        pannelloTrama.setBackground(java.awt.Color.WHITE);

        this.setBackground(java.awt.Color.WHITE);
        this.add(pannelloTrama);

        JPanel pannelloTramaGridLayout = new JPanel(new GridLayout(2, 3));
        pannelloTramaGridLayout.setBackground(java.awt.Color.WHITE);
        pannelloTrama.add(pannelloTramaGridLayout, BorderLayout.NORTH);

        pannelloTramaGridLayout.add(new JLabel());

        pannelloTramaGridLayout.add(new JLabel());

        //PROVA CON PANNELLO
        JPanel pannelloHome = new JPanel();
        pannelloTramaGridLayout.add(pannelloHome);
        pannelloHome.add(cover);
        pannelloHome.setSize(new Dimension(50, 50));

        pannelloHome.setBackground(java.awt.Color.WHITE);

        pannelloTramaGridLayout.add(new JLabel());
        JLabel labeln = new JLabel(film.toString());

        labeln.setFont(labeln.getFont().deriveFont(22.0f));
        pannelloTramaGridLayout.add(labeln);
        pannelloTramaGridLayout.add(new JLabel());

        JTextArea Trama = new JTextArea(film.getDescrizione());
        Trama.setLineWrap(true);
        Trama.setEditable(false);
        Trama.setBorder(null);
        Trama.setBackground(java.awt.Color.WHITE);

        pannelloTrama.add(Trama, BorderLayout.CENTER);

        try {
            proiezione = controller.screeningFilteredByFilmAndTime(film.getId_film(), focusedDateTime);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Errore lettura informazioni film, riprovare più tardi.",
                    "Errore",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");

        //ABBIAMO MODIFICAGTO QUI PRIMA ERA COSI new BorderLayout() DENTRO AL NEW JPANEL()
        JPanel pannelloContenitoreBackOrari = new JPanel();
        pannelloContenitoreBackOrari.setBackground(java.awt.Color.WHITE);
        this.add(pannelloContenitoreBackOrari);
        this.add(new YoutubePanel(film.getLink_youtube(), 200, 200));
        JPanel pannelloOrari = new JPanel(new GridLayout(proiezione.size(), 2, 10, 10));
        pannelloOrari.setBackground(java.awt.Color.WHITE);

        ButtonCart bottoneCarrello = null;
        JPanel pannelloCart;
        JScrollPane scrollpane = new JScrollPane(pannelloOrari, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pannelloContenitoreBackOrari.add(scrollpane, BorderLayout.CENTER);
        scrollpane.setBorder(null);
//        for (int i = 0; i < proiezione.size(); i++) {

        for (final Screening s : proiezione) {

            pannelloOrari.add(new JLabel(sdfDate.format(s.getData_ora().getTime()) + "    tipo: " + s.getType_String() + "    sala: " + s.getRoom().getId_sala()), BorderLayout.SOUTH);
            try {
                bottoneCarrello = new ButtonCart(s);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Errore lettura proiezione, riprovare più tardi.",
                        "Errore",
                        JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            bottoneCarrello.setBorderPainted(false);
            bottoneCarrello.setContentAreaFilled(false);
            bottoneCarrello.setPreferredSize(new Dimension(50, 50));
            bottoneCarrello.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    s.setFilm(film);
                    openPageThree(s);
                }
            });

            pannelloCart = new JPanel();
            pannelloCart.setBackground(java.awt.Color.WHITE);
            pannelloCart.add(bottoneCarrello);
            pannelloCart.setSize(new Dimension(50, 50));
            pannelloOrari.add(pannelloCart);

        }

    }

    private ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }

    private void goBack() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new PageOne(), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private ActionListener goBackEvent() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        };
        return evento;
    }

    private void openPageThree(Screening screening) {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new PageThree(screening), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

}
