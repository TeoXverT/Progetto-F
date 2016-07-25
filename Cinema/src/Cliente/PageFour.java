package Cliente;

import java.awt.BorderLayout;
import java.awt.Color;
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
    
    private Controller_Cliente controller;
    
    private Film film;
    private Screening screening;
    private Booking booking;
    
    private Config config;
    
    private JLabel totalPrice = new JLabel();
    private JTextField email;
    private Glasses spinner;
    
    Component popUpWindow;
    
    public PageFour(Booking booking) {
        this.controller = Controller_Cliente.getInstance();
        
        this.booking = booking;
        this.screening = this.booking.getScreening();
        this.film = this.screening.getFilm();
        
        try {
            this.config = controller.getConfig();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Server Error, try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        draw();
    }
    
    private void draw() {
        this.setLayout(new BorderLayout());
        
        JPanel pannelloCopertina = new JPanel();
        pannelloCopertina.setBackground(java.awt.Color.WHITE);
        
        try {
            URL url = new URL(film.getLink_copertina());
            Image image = ImageIO.read(url);
            JLabel label1 = new JLabel(imageScaling(new ImageIcon(image), 210, 300));
            pannelloCopertina.add(label1);
        } catch (IOException e) {
        }
        this.add(pannelloCopertina, BorderLayout.NORTH);
        
        JPanel centro = new JPanel(new GridLayout(0, 1));
        
        JPanel carrello = new JPanel(new GridLayout(0, 2));
        
        carrello.add(new JLabel("<html><font size=\"5\">" + screening.getData_ora_friendly_2() + "</font></html>"));
        carrello.add(new JLabel("<html><font size=\"5\">Room: " + screening.getRoom().getId_sala() + " Type of Projection: " + screening.getType_String() + "</font></html>"));
        
        carrello.add(new JLabel("<html><b><font size=\"5\">Cart:</font></b><html>"));
        carrello.add(new JLabel(""));
        
        for (Seat s : booking.getPosti_prenotati()) {
            carrello.add(new JLabel("<html><font size=\"4\"><i> Line: " + s.getx() + " Seat: " + s.gety() + "</font></html>"));
            if (s.isVip()) {
                double price = screening.getPrezzo() + config.getPrezzo_vip() + config.getOver_price();
                carrello.add(new JLabel(price + " €"));
            } else if (s.isDisable()) {
                double price = screening.getPrezzo() + config.getDisabled_price() + config.getOver_price();
                carrello.add(new JLabel(price + " €"));
            } else {
                double price = screening.getPrezzo() + config.getOver_price();
                carrello.add(new JLabel(price + " €"));
            }
        }
        
        if (screening.getTipo_proiezione() == 1 || screening.getTipo_proiezione() == 2) {  // 0- Normale 1- 3D 2-IMAX 3D 3- Live Event
            JPanel price = new JPanel(new GridLayout(1, 2));
            spinner = new Glasses("3D Glasses", config.getGlasses_price(), new SpinnerNumberModel(0, 0, booking.getPosti_prenotati().size(), 1));
            spinner.addChangeListener(updateTotal());
            price.add(spinner);
            price.add(new JLabel("X " + spinner.getPrice() + " €"));
            carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner.getName() + "</font></html>"));
            carrello.add(price);
        }
        
        carrello.add(new JLabel("<html><font size=\"5\"><i>Total</font></html>"));
        totalPrice.setText("<html><font size=\"6\">" + booking.getPrezzo() + " €</font></html>");
        carrello.add(totalPrice);
        carrello.add(new JLabel("<html><font size=\"5\"><i>Email for Payment:</font></html>"));
        email = new JTextField();
        email.setText("laTua@Email.it");
        carrello.add(email);
        centro.add(carrello);
        
        this.add(centro, BorderLayout.CENTER);
        
        JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                
                if (email.getText().compareTo("laTua@Email.it") != 0) { //Altri controlli possono essere messi qui
                    booking.setEmail(email.getText());
                    if (screening.isType3D() || screening.isTypeIMAX3D()) { //Considero anche il prezzo dei occhiali 3D
                        booking.setPrezzo(spinner.getTotalPrice() + booking.getPrezzo());
                        booking.setNumber_of_glasses(spinner.getNumber_of_glasses());
                    }
                    try {
                        int idBooking = controller.writeBooking(booking);
                        if (idBooking == 0) { //Durante la scelta dei posti qualcunaltro li ha comprati
                            JOptionPane.showMessageDialog(popUpWindow,
                                    "I Posti da lei scielti sono stati prenotati da qualcun'altro, si prega di scieglere dei nuovi posti.",
                                    "Attenzione!!!",
                                    JOptionPane.WARNING_MESSAGE);
                            openPage(new PageThree(screening));
                        } else {
                            System.out.println("ID Booking: " + idBooking);
                            booking.setId_prenotazione(idBooking);
                            
                            JLabel imagineCaricamento = new JLabel(new ImageIcon("immagini/caricamento.gif"));
                            JPanel pannelloCaricamento = new JPanel();
                            pannelloCaricamento.add(imagineCaricamento);
                            openPage(pannelloCaricamento);
                            
                            SendEmail().start();
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(popUpWindow,
                                "Errore con la scrittura della prenotazione.",
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
                openPage(new PageThree(screening));
            }
        });
        this.add(back, BorderLayout.BEFORE_LINE_BEGINS);
    }
    
    private ImageIcon imageScaling(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }
    
    private ChangeListener updateTotal() {
        ChangeListener evento = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double price = spinner.getTotalPrice() + booking.getPrezzo();
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
    
    private Thread SendEmail() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                EmailSender emailSender = new EmailSender();
                emailSender.SendEmailRequest(booking);
                openPage(new PageFive(booking));
            }
        }
        );
        return t;
    }
}
