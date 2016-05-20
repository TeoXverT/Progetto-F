package Cliente;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class PageFour extends JPanel {

    Controller_Cliente controller;
    Film film;
    Proiezione proiezione;
    Prenotazione prenotazione;
    Config config;
    JLabel totalPrice = new JLabel();
    ExtraElement[] spinner = new ExtraElement[6];

    public PageFour(Film film, Proiezione proiezione, Prenotazione prenotazione, Controller_Cliente controller, Config config) {
        this.controller = controller;
        this.film = film;
        this.proiezione = proiezione;
        this.prenotazione = prenotazione;
        this.config = config;

        drawGui();
    }

    private void drawGui() {
        this.setLayout(new BorderLayout());

        JPanel pannelloCopertina = new JPanel();
        try {
            URL url = new URL(film.getLink_copertina());
            Image image = ImageIO.read(url);
            JLabel label1 = new JLabel(scalaImmagine(new ImageIcon(image), 210, 300));
            pannelloCopertina.add(label1);
        } catch (IOException e) {
        }
        this.add(pannelloCopertina, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(0, 1));

        JPanel carrello = new JPanel(new GridLayout(0, 2));
        carrello.add(new JLabel("<html><h3>Proiezione il " + proiezione.getData_ora_friendly_2() + "</h3></html>"));
        carrello.add(new JLabel("<html><h3>Nella Sala " + proiezione.getId_sala() + "</h3></html>"));

        carrello.add(new JLabel("<html><b><h3>Carrello:</h2></b><html>"));
        carrello.add(new JLabel(""));

        for (Seat s : prenotazione.getPosti_prenotati()) {
            carrello.add(new JLabel("<html><font size=\"5\"><i> Posto " + s.getx() + " Fila " + s.gety() + "</font></html>"));
            carrello.add(new JLabel(""));

        }

        JPanel price = new JPanel(new GridLayout(1, 2));

        spinner[0] = new ExtraElement("Pop-corn - SMALL", config.getPopcorn_s(), new SpinnerNumberModel(0, 0, 5, 1));
        spinner[0].addChangeListener(updateTotal());
        price.add(spinner[0]);
        price.add(new JLabel("X " + spinner[0].getPrice() + " €"));
        carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner[0].getName() + "</font></html>"));
        carrello.add(price);

        price = new JPanel(new GridLayout(1, 2));
        spinner[1] = new ExtraElement("Pop-corn - MEDIUM", config.getPopcorn_m(), new SpinnerNumberModel(0, 0, 5, 1));
        spinner[1].addChangeListener(updateTotal());
        price.add(spinner[1]);
        price.add(new JLabel("X " + spinner[1].getPrice() + " €"));
        carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner[1].getName() + "</font></html>"));
        carrello.add(price);

        price = new JPanel(new GridLayout(1, 2));
        spinner[2] = new ExtraElement("Pop-corn - LARGE", config.getPopcorn_l(), new SpinnerNumberModel(0, 0, 5, 1));
        spinner[2].addChangeListener(updateTotal());
        price.add(spinner[2]);
        price.add(new JLabel("X " + spinner[2].getPrice() + " €"));
        carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner[2].getName() + "</font></html>"));
        carrello.add(price);

        price = new JPanel(new GridLayout(1, 2));
        spinner[3] = new ExtraElement("Drink - SMALL", config.getBibita_s(), new SpinnerNumberModel(0, 0, 5, 1));
        spinner[3].addChangeListener(updateTotal());
        price.add(spinner[3]);
        price.add(new JLabel("X " + spinner[3].getPrice() + " €"));
        carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner[3].getName() + "</font></html>"));
        carrello.add(price);

        price = new JPanel(new GridLayout(1, 2));
        spinner[4] = new ExtraElement("Drink - MEDIUM", config.getBibita_m(), new SpinnerNumberModel(0, 0, 5, 1));
        spinner[4].addChangeListener(updateTotal());
        price.add(spinner[4]);
        price.add(new JLabel("X " + spinner[4].getPrice() + " €"));
        carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner[4].getName() + "</font></html>"));
        carrello.add(price);

        price = new JPanel(new GridLayout(1, 2));
        spinner[5] = new ExtraElement("Drink - LARGE", config.getBibita_l(), new SpinnerNumberModel(0, 0, 5, 1));
        spinner[5].addChangeListener(updateTotal());
        price.add(spinner[5]);
        price.add(new JLabel("X " + spinner[5].getPrice() + " €"));
        carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner[5].getName() + "</font></html>"));
        carrello.add(price);

        carrello.add(new JLabel("<html><font size=\"4\"><i>Total</font></html>"));
        totalPrice.setText("<html><font size=\"6\">" + prenotazione.getPrezzo() + " €</font></html>");
        carrello.add(totalPrice);
        centro.add(carrello);

        this.add(centro, BorderLayout.CENTER);

        JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    double newPrice = 0;
                    for (int i = 0; i < 6; i++) {
                        newPrice = newPrice + spinner[i].getTotalPrice();
                    }
                    prenotazione.setPrezzo(newPrice + prenotazione.getPrezzo());
//                    openPage(new PageFive(prenotazione,controller));
                    openPage(new PageOne(controller));
                } catch (SQLException ex) {
                }
            }
        });
        this.add(next, BorderLayout.AFTER_LINE_ENDS);

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    openPage(new PageOne(controller));
                } catch (SQLException ex) {
                }
            }
        });
        this.add(back, BorderLayout.BEFORE_LINE_BEGINS);
    }

    private ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }

    private ChangeListener updateTotal() {
        ChangeListener evento = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double newPrice = 0;
                for (int i = 0; i < 6; i++) {
                    newPrice = newPrice + spinner[i].getTotalPrice();
                }
                newPrice = newPrice + prenotazione.getPrezzo();
                totalPrice.setText("<html><font size=\"6\">" + newPrice + " €</font></html>");
            }
        };
        return evento;
    }

    private void openPage(JPanel panel) throws SQLException {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(panel);
        this.revalidate();
        this.repaint();
    }
}
