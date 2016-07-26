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
            URL url = new URL(film.getLinkCover());
            Image image = ImageIO.read(url);
            JLabel label1 = new JLabel(imageScaling(new ImageIcon(image), 210, 300));
            pannelloCopertina.add(label1);
        } catch (IOException e) {
        }
        this.add(pannelloCopertina, BorderLayout.NORTH);
        
        JPanel centro = new JPanel(new GridLayout(0, 1));
        
        JPanel carrello = new JPanel(new GridLayout(0, 2));
        
        carrello.add(new JLabel("<html><font size=\"5\">" + screening.getData_ora_friendly_2() + "</font></html>"));
        carrello.add(new JLabel("<html><font size=\"5\">Room: " + screening.getRoom().getIdHall() + " Type of Projection: " + screening.getType_String() + "</font></html>"));
        
        carrello.add(new JLabel("<html><b><font size=\"5\">Cart:</font></b><html>"));
        carrello.add(new JLabel(""));
        
        for (Seat s : booking.getBookedSeat()) {
            carrello.add(new JLabel("<html><font size=\"4\"><i> Line: " + s.getx() + " Seat: " + s.gety() + "</font></html>"));
            if (s.isVip()) {
                double price = screening.getPrice() + config.getVipOverprice() + config.getOver_price();
                carrello.add(new JLabel(price + " €"));
            } else if (s.isDisable()) {
                double price = screening.getPrice() + config.getDisabledPrice() + config.getOver_price();
                carrello.add(new JLabel(price + " €"));
            } else {
                double price = screening.getPrice() + config.getOver_price();
                carrello.add(new JLabel(price + " €"));
            }
        }
        
        if (screening.getpProjectionType() == 1 || screening.getpProjectionType() == 2) {  // 0- Normale 1- 3D 2-IMAX 3D 3- Live Event
            JPanel price = new JPanel(new GridLayout(1, 2));
            spinner = new Glasses("3D Glasses", config.getGlassesPrice(), new SpinnerNumberModel(0, 0, booking.getBookedSeat().size(), 1));
            spinner.addChangeListener(updateTotal());
            price.add(spinner);
            price.add(new JLabel("X " + spinner.getPrice() + " €"));
            carrello.add(new JLabel("<html><font size=\"4\"><i>" + spinner.getName() + "</font></html>"));
            carrello.add(price);
        }
        
        carrello.add(new JLabel("<html><font size=\"5\"><i>Total</font></html>"));
        totalPrice.setText("<html><font size=\"6\">" + booking.getPrice() + " €</font></html>");
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
                        booking.setPrice(spinner.getTotalPrice() + booking.getPrice());
                        booking.setNumberOfGlasses(spinner.getNumber_of_glasses());
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
                            booking.setIdBooking(idBooking);
                            
                            JLabel imagineCaricamento = new JLabel(new ImageIcon("images/loading.gif"));
                            JPanel pannelloCaricamento = new JPanel();
                            pannelloCaricamento.add(imagineCaricamento);
                            openPage(pannelloCaricamento);
                            
                            SendEmail().start();
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(popUpWindow,
                                "Error while writing booking.",
                                "Attenction!!!",
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
