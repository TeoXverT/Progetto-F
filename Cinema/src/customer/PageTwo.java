package customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import obj.Film;
import obj.YoutubePanel;
import obj.Projection;

public class PageTwo extends JPanel {

    private CustomerController controller;
    private Film film;
    private ArrayList<Projection> proiezione;
    private Calendar focusedDateTime;

    public PageTwo(Film film, Calendar focusedDateTime) {
        this.controller = CustomerController.getInstance();
        this.film = film;
        this.focusedDateTime = focusedDateTime;
        this.setLayout(new GridLayout(2, 2, 10, 10));
        
        draw();
    }

    private void draw() {

        JButton cover = new JButton();
        cover.setIcon(imageScaling(new ImageIcon("images/home.png"), 30, 30));
        cover.setBorderPainted(true);
        cover.setContentAreaFilled(false);

        JPanel pannelloCopertina = new JPanel();
        pannelloCopertina.setBackground(java.awt.Color.WHITE);
        this.add(pannelloCopertina);
        Image image = null;
        try {
            URL url = new URL(film.getLinkCover());
            image = ImageIO.read(url);
        } catch (IOException e) {
            
            JOptionPane.showMessageDialog(null,
                    "Error while downloading film's cover, retry later.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                        
        }
        ImageIcon ii = new ImageIcon(image);
        JLabel label1 = new JLabel(imageScaling(ii, 250, 350));
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

        
        JPanel pannelloHome = new JPanel();
        pannelloTramaGridLayout.add(pannelloHome);
        pannelloHome.add(cover);
        pannelloHome.setSize(new Dimension(50, 50));

        pannelloHome.setBackground(java.awt.Color.WHITE);

        pannelloTramaGridLayout.add(new JLabel());
        
        
        JEditorPane labeln = new JEditorPane();
        
       labeln.setContentType("text/html");
        labeln.setText("<b>" + film.toString() + "</b>");
        
        labeln.setEditable(false);
        labeln.setBorder(null);
        labeln.setBackground(java.awt.Color.WHITE);
       
        pannelloTramaGridLayout.add(labeln);
        pannelloTramaGridLayout.add(new JLabel());

        JTextArea Trama = new JTextArea(film.getDescription());
        Trama.setLineWrap(true);
        Trama.setEditable(false);
        Trama.setBorder(null);
        Trama.setBackground(java.awt.Color.WHITE);
        Trama.setWrapStyleWord(true);
        pannelloTrama.add(Trama, BorderLayout.CENTER);

        try {
            proiezione = controller.projectionFilteredByFilmAndTime(film.getIdFilm(), focusedDateTime);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error while reading film's informations, retry later.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");

         JPanel pannelloContenitoreBackOrari = new JPanel();
        pannelloContenitoreBackOrari.setBackground(java.awt.Color.WHITE);
        this.add(pannelloContenitoreBackOrari);
        this.add(new YoutubePanel(film.getLinkYoutube(), 200, 200));
        JPanel pannelloOrari = new JPanel(new GridLayout(proiezione.size(), 2, 10, 10));
        pannelloOrari.setBackground(java.awt.Color.WHITE);

        ButtonCart bottoneCarrello = null;
        JPanel pannelloCart;
        JScrollPane scrollpane = new JScrollPane(pannelloOrari, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pannelloContenitoreBackOrari.add(scrollpane, BorderLayout.CENTER);
        scrollpane.setBorder(null);

        for (final Projection s : proiezione) {

            pannelloOrari.add(new JLabel(sdfDate.format(s.getData_ora().getTime()) + "    tipo: " + s.getType_String() + "    sala: " + s.getRoom().getIdHall()), BorderLayout.SOUTH);
            try {
                bottoneCarrello = new ButtonCart(s);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error while reading projection, retry later.",
                        "Error",
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

    private ImageIcon imageScaling(ImageIcon immagine, int lunghezza, int altezza) {
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

    private void openPageThree(Projection screening) {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new PageThree(screening), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

}
