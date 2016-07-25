package customer;

import obj.ButtonCover;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import obj.Film;

public class PageOne extends JPanel {

    private CustomerController controller;

private    JTabbedPane tab = new JTabbedPane();
    private Component frameErrore;

    private int SliderValue;
    private JSlider slider;
    private Calendar ora = Calendar.getInstance();

    private static int oraStart = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

    private JPanel pannelloSlider;

    public PageOne() {
        this.controller = CustomerController.getInstance();

        this.setLayout(new BorderLayout());
        this.add(tab, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(tab, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);
      
        pannelloSlider = new JPanel();
        pannelloSlider.setBackground(Color.white);
        
        pannelloSlider.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;

        this.add(pannelloSlider, BorderLayout.WEST);
        if (oraStart < 15) {
            oraStart = 15;
        }

        slider = new JSlider(JSlider.VERTICAL, 15, 23, oraStart);
        slider.setBackground(Color.white);
        
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                if (!slider.getValueIsAdjusting()) {
                    ThreadDownloadFilm(tab.getSelectedIndex()).start();
                }
            }
        });

        pannelloSlider.add(slider, c);
        slider.setPreferredSize(new Dimension(50, 500));
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        Calendar dataAttuale = Calendar.getInstance();
        tab.add(new JPanel(), "Oggi");

        for (int i = 0; i < 6; i++) {
            dataAttuale.add(Calendar.DAY_OF_MONTH, 1);
            tab.add(new JPanel(), dayOfTheWeek(dataAttuale.get(Calendar.DAY_OF_WEEK)) + ", " + dataAttuale.get(Calendar.DAY_OF_MONTH) + " " + processMonth(dataAttuale.get(Calendar.MONTH)));
        }
        ThreadDownloadFilm(0).start();

        tab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                ThreadDownloadFilm(tab.getSelectedIndex()).start();
            }
        });
    }

    private String dayOfTheWeek(int numero) {
        switch (numero) {
            case 1:
                return "domenica";
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

    private String processMonth(int numero) {
        switch (numero) {
            case 0:
                return "Gennaio";
            case 1:
                return "Febbraio";
            case 2:
                return "Marzo";
            case 3:
                return "Aprile";
            case 4:
                return "Maggio";
            case 5:
                return "Giugno";
            case 6:
                return "Luglio";
            case 7:
                return "Agosto";
            case 8:
                return "Settembre";
            case 9:
                return "Ottobre";
            case 10:
                return "Novembre";
            case 11:
                return "Dicembre";
            default:
                return "giorno non valido";
        }
    }

    private Thread ThreadDownloadFilm(final int deltaData) {    //scarica i film secondo i filtri selezionati
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    JPanel pannello = new JPanel(new GridLayout(0, 3, 20, 30));
                    SliderValue = slider.getValue();
                    ArrayList<Film> Films = controller.futureFilmBySlider(deltaData, SliderValue);

                    for (final Film f : Films) {
                        ButtonCover cover = new ButtonCover(f);
                        cover.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                try {

                                    Calendar focusedDateTime = Calendar.getInstance();
                                    focusedDateTime.add(Calendar.DAY_OF_YEAR, deltaData);
                                    focusedDateTime.set(Calendar.HOUR_OF_DAY, SliderValue);
                                    focusedDateTime.set(Calendar.MINUTE, 0);
                                    focusedDateTime.set(Calendar.SECOND, 0);

                                    OpenPageTwo(f, focusedDateTime);
                                } catch (SQLException ex) {
                                    Logger.getLogger(PageOne.class.getName()).log(Level.SEVERE, null, ex);
                                  
                                } catch (IOException ex) {
                                    Logger.getLogger(PageOne.class.getName()).log(Level.SEVERE, null, ex);
                                  
                                }
                            }
                        });
                        pannello.add(cover);

                    }
                    updateGui(deltaData, pannello);
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

    public void updateGui(int deltaDate, JPanel pannelloGiornaliero) { //aggiorna la gui quando necessario(modifica filtri orari o giorni)
        JPanel Pannello = (JPanel) tab.getComponentAt(deltaDate);
        Pannello.removeAll();
        Pannello.add(pannelloGiornaliero);
        Pannello.revalidate();
        Pannello.repaint();
    }

    public void OpenPageTwo(Film film, Calendar focusedDateTime) throws SQLException, IOException { //apre pagina selezione proiezioni del film scelto
        this.removeAll();
        this.add(new PageTwo(film, focusedDateTime), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
