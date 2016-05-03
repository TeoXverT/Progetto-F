package Cliente;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import oggetti.Film;

public class PageOne extends JPanel {

    JTabbedPane tab = new JTabbedPane();
    Controller_Cliente controller;
    Component frameErrore;

    public PageOne(Controller_Cliente controller) {
        this.controller = controller;

        this.setLayout(new BorderLayout());
        this.add(tab, BorderLayout.CENTER);

        Calendar dataAttuale = Calendar.getInstance();
        tab.add(new JPanel(), "Oggi");

        for (int i = 0; i < 6; i++) {
            dataAttuale.add(Calendar.DAY_OF_MONTH, 1);
            tab.add(new JPanel(), giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " " + dataAttuale.get(Calendar.DAY_OF_MONTH) + "        ");
        }

        tab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                ThreadScaricaFilm(tab.getSelectedIndex()).start();
            }
        });
    }

    private String giornoDellaSettimana(int numero) {

        switch (numero) {
            case 1:
                return "Domenica";

            case 2:
                return "lunedì";

            case 3:
                return "martedi";

            case 4:
                return "mercoledì";

            case 5:
                return "giovedi";

            case 6:
                return "venerdì";

            case 7:
                return "sabato";

            default:
                return "giorno non valido";

        }
    }

    private Thread ThreadScaricaFilm(int deltaData) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {

                    JPanel pannello = new JPanel(new GridLayout(0, 3, 20, 30));
                    ArrayList<Film> Films = controller.FilmFuturo(deltaData);

                    for (int i = 0; i < Films.size(); i++) {
                        System.out.println("In Download immagine URL: " + Films.get(i).getLink_copertina());
                        ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(Films.get(i).getLink_copertina())));
                        pannello.add(new JLabel(scalaImmagine(immagine, 200, 300)));
                        System.out.println("finito caricamento immagini");
                        aggiornaGui(deltaData, pannello);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frameErrore, "Errore con scaricamento immagini", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frameErrore, "Errore con il server", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        );
        return t;
    }

    ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }

    public void aggiornaGui(int deltaDate, JPanel pannelloGiornaliero) {
        JPanel Pannello = (JPanel) tab.getComponentAt(deltaDate);

        Pannello.removeAll();
        Pannello.add(pannelloGiornaliero);
        Pannello.revalidate();
        Pannello.repaint();
    }
}
