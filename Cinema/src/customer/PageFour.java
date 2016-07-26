package customer;

import obj.Seat;
import obj.Config;
import obj.Projection;
import obj.Film;
import obj.Booking;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

public class PageFour extends JPanel {

    private CustomerController controller;

    private Film film;
    private Projection screening;
    private Booking booking;

    private Config config;

    private JLabel totalPrice = new JLabel();
    private JTextField email;
    private Glasses spinner;

    Component popUpWindow;

    public PageFour(Booking booking) {
        this.controller = CustomerController.getInstance();

        this.booking = booking;
        this.screening = this.booking.getProjection();
        this.film = this.screening.getFilm();

        try {
            this.config = controller.getConfig();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Errore del server, riprovare più tardi.", "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        draw();
    }

    private void draw() {
        this.setLayout(new BorderLayout());

        //aggiunta pannello copertina e relativi elementi
        JPanel pannelloCopertina = new JPanel();
        pannelloCopertina.setBackground(java.awt.Color.WHITE);
        pannelloCopertina.add(downloadImageFromUrl(film.getLinkCover())); //scarica e aggiunge immagine copertina
        this.add(pannelloCopertina, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(0, 1));

        //creazione pannello carrello
        SimpleDateFormat giorno = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat ora = new SimpleDateFormat("HH:mm");
        JPanel carrello = new JPanel(new GridLayout(0, 2));
        carrello.add(new JLabel("<html><font size=\"5\"> Giorno: " + giorno.format(screening.getData_ora().getTime()) + " Alle Ore: " + ora.format(screening.getData_ora().getTime()) + "</font></html>"));
        carrello.add(new JLabel("<html><font size=\"5\">Sala: " + screening.getRoom().getIdHall() + " Tipo di proiezione: " + screening.getType_String() + "</font></html>"));
        carrello.add(new JLabel("<html><b><font size=\"5\">Carrello:</font></b><html>"));
        carrello.add(new JLabel(""));

        for (Seat s : booking.getBookedSeat()) {
            carrello.add(new JLabel("<html><font size=\"4\"><i> Fila: " + s.getx() + " Posto: " + s.gety() + "</font></html>"));
            if (s.isVip()) {
                double price = screening.getPrice() + config.getVipOverprice();
                carrello.add(new JLabel(price + " €"));
            } else if (s.isDisable()) {
                double price = config.getDisabledPrice();
                carrello.add(new JLabel(price + " €"));
            } else {
                double price = screening.getPrice();
                carrello.add(new JLabel(price + " €"));
            }
        }

        if (screening.getpProjectionType() == 1 || screening.getpProjectionType() == 2) {  // 0- Normale 1- 3D 2-IMAX 3D 3- Live Event
            JPanel price = new JPanel(new GridLayout(1, 2));
            spinner = new Glasses("Occhiali 3D", config.getGlassesPrice(), new SpinnerNumberModel(0, 0, booking.getBookedSeat().size(), 1));
            spinner.addChangeListener(updateTotal());
            price.add(spinner);
            price.add(new JLabel("X " + spinner.getPrice() + " €"));
            carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner.getName() + "</font></html>"));
            carrello.add(price);
        }

        carrello.add(new JLabel("<html><font size=\"5\"><i>Totale</font></html>"));
        totalPrice.setText("<html><font size=\"6\">" + booking.getPrice() + " €</font></html>");
        carrello.add(totalPrice);
        carrello.add(new JLabel("<html><font size=\"5\"><i>Email per il pagamento:</font></html>"));
        email = new JTextField();
        email.setText("laTua@Email.it");
        carrello.add(email);
        centro.add(carrello);

        this.add(centro, BorderLayout.CENTER);

        JButton next = new JButton("Next");
        next.addActionListener(nextActionListener());
        this.add(next, BorderLayout.AFTER_LINE_ENDS);

        JButton back = new JButton("Indietro");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                openPage(new PageThree(screening));
            }
        });
        this.add(back, BorderLayout.BEFORE_LINE_BEGINS);
    }

    private ActionListener nextActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (email.getText().compareTo("laTua@Email.it") != 0) { //Altri controlli possono essere messi qui
                    booking.setEmail(email.getText());
                    if (screening.isType3D() || screening.isTypeIMAX3D()) { //Considero anche il prezzo dei occhiali 3D
                        booking.setPrice(spinner.getTotalPrice() + booking.getPrice());
                        booking.setNumberOfGlasses(spinner.getNumber_of_glasses());
                    }
                    saveAndSendBooking();
                } else {
                    JOptionPane.showMessageDialog(popUpWindow,
                            "Inserire la propria email per ricevere la richiesta di pagamento.",
                            "Attenzione!!!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        };
    }

    private void saveAndSendBooking() {
        try {
            int idBooking = controller.writeBooking(booking);
            if (idBooking == 0) { //Durante la scelta dei posti qualcunaltro li ha comprati
                JOptionPane.showMessageDialog(popUpWindow,
                        "I Posti da lei scielti sono stati prenotati da qualcun'altro, si prega di scieglere dei nuovi posti.",
                        "Attenzione!!!",
                        JOptionPane.WARNING_MESSAGE);
                openPage(new PageThree(screening));
            } else {
                booking.setIdBooking(idBooking);

                openPage(new PageFive(booking));
                if (!controller.makeEmailRequest(booking)) {
                    JOptionPane.showMessageDialog(popUpWindow,
                            "Attenzione errore inaspettato, contattare l'assistenza.",
                            "Attenzione!!!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(popUpWindow,
                    "Errore durante la prenotazione.",
                    "Attenzione!!!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private ImageIcon imageScaling(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }

    private ChangeListener updateTotal() {
        ChangeListener evento = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double price = spinner.getTotalPrice() + booking.getPrice();
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

    private JLabel downloadImageFromUrl(String link_url) {

        try {
            //scarica immagine copertina film
            URL url = new URL(link_url);
            Image image = ImageIO.read(url);
            JLabel label1 = new JLabel(imageScaling(new ImageIcon(image), 210, 300));
            return label1;
        } catch (IOException e) {

            JOptionPane.showMessageDialog(null,
                    "Errore: scaricamento copertina non riuscito",
                    "Errore",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

    }
}
