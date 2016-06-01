package Cliente;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
    Glasses spinner;

    Component popUpWindow;

    public PageFour(Film film, Proiezione proiezione, Prenotazione prenotazione, Config config, Controller_Cliente controller) {
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
        carrello.add(new JLabel("<html><font size=\"5\">Timing: " + proiezione.getData_ora_friendly_2() + "</font></html>"));
        carrello.add(new JLabel("<html><font size=\"5\">Room: " + proiezione.getId_sala() + " Type of Projection: " + proiezione.getType_String() + "</font></html>"));

        carrello.add(new JLabel("<html><b><font size=\"5\">Cart:</font></b><html>"));
        carrello.add(new JLabel(""));

        for (Seat s : prenotazione.getPosti_prenotati()) {
            carrello.add(new JLabel("<html><font size=\"4\"><i> Line: " + s.getx() + " Seat: " + s.gety() + "</font></html>"));
            if (s.isVip()) {
                double price = proiezione.getPrezzo() + config.getPrezzo_vip() + config.getOver_price();
                carrello.add(new JLabel(price + " €"));
            } else if (s.isDisable()) {
                double price = proiezione.getPrezzo() + config.getDisabled_price() + config.getOver_price();
                carrello.add(new JLabel(price + " €"));
            } else {
                double price = proiezione.getPrezzo() + config.getOver_price();
                carrello.add(new JLabel(price + " €"));
            }
        }

        if (proiezione.getTipo_proiezione() == 1 || proiezione.getTipo_proiezione() == 2) {  // 0- Normale 1- 3D 2-IMAX 3D 3- Live Event
            JPanel price = new JPanel(new GridLayout(1, 2));
            spinner = new Glasses("3D Glasses", config.getGlasses_price(), new SpinnerNumberModel(0, 0, prenotazione.getPosti_prenotati().size(), 1));
            spinner.addChangeListener(updateTotal());
            price.add(spinner);
            price.add(new JLabel("X " + spinner.getPrice() + " €"));
            carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner.getName() + "</font></html>"));
            carrello.add(price);
        }

        carrello.add(new JLabel("<html><font size=\"5\"><i>Total</font></html>"));
        totalPrice.setText("<html><font size=\"6\">" + prenotazione.getPrezzo() + " €</font></html>");
        carrello.add(totalPrice);
        carrello.add(new JLabel("<html><font size=\"5\"><i>Email for Payment:</font></html>"));
        final JTextField email = new JTextField();
        email.setText("laTua@Email.it");
        carrello.add(email);
        centro.add(carrello);

        this.add(centro, BorderLayout.CENTER);

        JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                if (email.getText().compareTo("laTua@Email.it") != 0) {
                    if (proiezione.isType3D() || proiezione.isTypeIMAX3D()) { //Considero anche il prezzo dei occhiali 3D
                        prenotazione.setPrezzo(spinner.getTotalPrice() + prenotazione.getPrezzo());
                        prenotazione.setNumber_of_glasses(spinner.getNumber_of_glasses());
                    }
                    try {
                        if (controller.scriviPrenotazione(prenotazione) == 0) {
                            JOptionPane.showMessageDialog(popUpWindow,
                                    "I Posti da lei scielti sono stati prenotati da qualcun'altro, si prega di scieglere dei nuovi posti.",
                                    "Attenzione!!!",
                                    JOptionPane.WARNING_MESSAGE);
                            openPage(new PageThree(film, proiezione, controller));
                        } else {

                            EmailSender emailSender = new EmailSender();
                            emailSender.SendEmailRequest(email.getText(), film, proiezione, prenotazione);
                    openPage(new PageFive(prenotazione,controller));
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(popUpWindow,
                                "Errore con il server.",
                                "Attenzione!!!",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(popUpWindow,
                            "Inserire la propria email per ricevere la richiesta di pagamento.",
                            "Attenzione!!!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        this.add(next, BorderLayout.AFTER_LINE_ENDS);

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                openPage(new PageThree(film, proiezione, controller));
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
                double price = spinner.getTotalPrice() + prenotazione.getPrezzo();
                totalPrice.setText("<html><font size=\"6\">" + price + " €</font></html>");
            }
        };
        return evento;
    }

    private void openPage(JPanel panel) {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
