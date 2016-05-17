package Cliente;


import oggetti.ButtonCover;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import oggetti.Film;

public class PageOne extends JPanel {

    JTabbedPane tab = new JTabbedPane();
    Controller_Cliente controller;
    Component frameErrore;
    
    int SliderValue ;
    JSlider slider;
    Calendar ora = Calendar.getInstance();
    
    
    static  int oraStart = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    
    JPanel pannelloSlider;
    
    
    public PageOne(Controller_Cliente controller) {
        this.controller = controller;
     
        this.setLayout(new BorderLayout());
        this.add(tab, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(tab,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);    
        //istanziazione aggiunta slider a sinistra del frame
        pannelloSlider = new JPanel(); 
        pannelloSlider.setPreferredSize(new Dimension(75,100));
        this.add(pannelloSlider, BorderLayout.WEST);
      if(oraStart < 15){
           oraStart = 15;
      }
       
        slider = new JSlider(JSlider.VERTICAL, 15, 23, oraStart);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                if(!slider.getValueIsAdjusting()) {
                    
                    ThreadScaricaFilm(0).start();
                    
                }
            }
        });
        
        pannelloSlider.add(slider);
        slider.setPreferredSize(new Dimension(50,500));
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        
        
        Calendar dataAttuale = Calendar.getInstance();
        tab.add(new JPanel(), "Oggi");

        for (int i = 0; i < 6; i++) {
            dataAttuale.add(Calendar.DAY_OF_MONTH, 1);
            tab.add(new JPanel(), giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + ", " + dataAttuale.get(Calendar.DAY_OF_MONTH) + " " + elaboraMese(dataAttuale.get(Calendar.MONTH)));
        }
        ThreadScaricaFilm(0).start();

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

    private String elaboraMese(int numero) {
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

    private Thread ThreadScaricaFilm(final int deltaData ) {
        Thread t= new Thread(new Runnable() {
            public void run() {
                try {
                    JPanel pannello = new JPanel(new GridLayout(0, 3, 20, 30));
                    SliderValue = slider.getValue();
                    ArrayList<Film> Films = controller.FilmFuturoBySlider(deltaData, SliderValue);
                    
                    for (final Film f : Films) {
//                        System.out.println("In Download immagine URL: " + f.getLink_copertina());
                        ButtonCover cover = new ButtonCover(f);
                        cover.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    OpenPageTwo(f, deltaData, SliderValue);
                                } catch (SQLException ex) {
                                    Logger.getLogger(PageOne.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(PageOne.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        pannello.add(cover);
//                        System.out.println("finito caricamento immagini");
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

    public void aggiornaGui(int deltaDate, JPanel pannelloGiornaliero) {
        JPanel Pannello = (JPanel) tab.getComponentAt(deltaDate);
        Pannello.removeAll();
        Pannello.add(pannelloGiornaliero);
        Pannello.revalidate();
        Pannello.repaint();
    }

    public void OpenPageTwo(Film film, int deltaData, int valueSlider) throws SQLException, IOException {
        this.removeAll();
        this.add(new PageTwo(film, deltaData,controller, valueSlider));
        this.revalidate();
        this.repaint();
    }
}
