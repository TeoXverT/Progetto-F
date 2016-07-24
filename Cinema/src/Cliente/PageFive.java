package Cliente;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JPanel;
import oggetti.Prenotazione;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PageFive extends JPanel {

    private final int MAX_TIME; //Min
    private final int DB_READ_POLLING = 5; //Sec

    private Controller_Cliente controller;
    private Prenotazione prenotazione;

    private JLabel countdownDispaly = new JLabel();
    private JPanel pannelloContenitore;

    public PageFive(final Prenotazione prenotazione) {
        controller = Controller_Cliente.getInstance();
        this.prenotazione = prenotazione;

        int time = 0;
        try {
            time = controller.getConfig().getBooking_validation_time();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Errore di connessione con il server, riporovare piu tardi.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        MAX_TIME = time;

        draw();
    }

    void draw() {
        pannelloContenitore = new JPanel(new BorderLayout(3, 2));
        this.add(pannelloContenitore);

        JPanel nord = new JPanel(new GridLayout(2, 1));
        nord.add(countdownDispaly);

        JButton confirmButton = new JButton("Conferma Prenotazione");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    controller.getInsertPaymentForced(prenotazione);
                } catch (SQLException ex) {
                    System.out.println("Problema: inserimento pagamento fake");
                }
            }
        });
        nord.add(confirmButton);

        pannelloContenitore.add(nord, BorderLayout.NORTH);
        JLabel imagineCaricamento = new JLabel(new ImageIcon("immagini/caricamento.gif"));
        pannelloContenitore.add(imagineCaricamento, BorderLayout.CENTER);

        ThreadTimer().start();
        ThreadCountdown().start();
    }

    private Thread ThreadCountdown() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                int minute = MAX_TIME;
                int second = 0;
                for (int i = 0; i < MAX_TIME * 60; i++) {

                    countdownDispaly.setText("<html><center><h1><b>" + String.valueOf(minute) + " minuti e " + second + " secondi rimasti.</center><br>Allo scadere del tempo la prenotazione non sarà più valida.<b></h1></html>");

                    if (second == 0) {
                        second = 59;
                        minute = minute - 1;
                    } else {
                        second--;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("PROBLEMA CON Sleep");
                    }
                }
            }
        });

        return t;
    }

    private Thread ThreadTimer() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                int checkPayment = 0;
                for (int i = 0; i < (MAX_TIME * 60) / DB_READ_POLLING; i++) {
                    try {
                        Thread.sleep(DB_READ_POLLING * 1000);
                        checkPayment = controller.checkBookingPayment(prenotazione.getId_prenotazione());
                        if (checkPayment == 1) {
                            break;
                        }

                    } catch (InterruptedException ex) {
                        System.out.println("PROBLEMA: con istruzine Sleep");
                    } catch (SQLException ex) {
                        System.out.println("PROBLEMA: lettura avvenuto pagamento");
                    }
                }

                pannelloContenitore.removeAll();
                pannelloContenitore.add(makeHomeButton(), BorderLayout.CENTER);
                if (checkPayment == 1) {
                    pannelloContenitore.add(new JLabel("<html><h1><b>PAGAMENTO EFFETTUATO.</b></html>"), BorderLayout.NORTH);
                } else {
                    pannelloContenitore.add(new JLabel("<html><h1><b>PAGAMENTO NON PERVENUTO.</b></html>"), BorderLayout.NORTH);
                }
                pannelloContenitore.revalidate();
                pannelloContenitore.repaint();

            }
        });
        return t;
    }

    private JButton makeHomeButton() {
        JButton homeButton = new JButton();
        homeButton.setIcon(new ImageIcon(new ImageIcon("immagini/home.png").getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)));
        homeButton.setBorderPainted(true);
        homeButton.setContentAreaFilled(false);
        homeButton.addActionListener(goBackEvent());
        return homeButton;
    }

    private void goBack() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new PageOne());
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

}
