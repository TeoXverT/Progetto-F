/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class Gui_Gestore extends JFrame {

    private final Controller_Gestore controller;

    private JPanel display;
    private JLabel outputGrafico; //Scritte in basso

    private JLabel imagineCaricamento;
    private JPanel pannelloCaricamento;

//    private Component frameErrore = null;
    public Gui_Gestore() {

//        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            // If Nimbus is not available, you can set the GUI to another look and feel.
//        }
        controller = new Controller_Gestore();
        display = new JPanel(new BorderLayout());
        creaGui();
        imagineCaricamento = new JLabel(new ImageIcon("immagini/logo_2.gif"));
        pannelloCaricamento = new JPanel();
        pannelloCaricamento.add(imagineCaricamento);

    }

    private void creaGui() {
        this.setJMenuBar(creaMenuBar());  //Costruttore del menu a tendina

        display = defaultSchermata();     //Visualizzo logo in splash 

        JPanel sud = new JPanel(new BorderLayout());
        outputGrafico = new JLabel("Output del risultato", SwingConstants.CENTER);
        sud.add(outputGrafico, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(display, BorderLayout.CENTER);
        this.add(sud, BorderLayout.SOUTH);
        this.setTitle("Pannello Gestore");
        this.setBounds(900, 100, 850, 700);

//        this.setResizable(false);
        ImageIcon icona = new ImageIcon("immagini/logo_trasparente.png");
        setIconImage(icona.getImage());

    }

    private JMenuBar creaMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Viewer");
        menuBar.add(menu);

        menu.addSeparator();
        submenu = new JMenu("Hall Status");

        menu.add(CreateHallList(submenu));

        menuItem = new JMenuItem("Film");
        menuItem.addActionListener(visualizzaFilm());
        menu.add(menuItem);

        menuItem = new JMenuItem("Prenotazioni");
        menuItem.addActionListener(showBooking());
        menu.add(menuItem);

        menu.addSeparator();
        submenu = new JMenu("Screening");

        menuItem = new JMenuItem("Now");
        menuItem.addActionListener(showScreening(0));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Next");
        menuItem.addActionListener(showScreening(1));
        submenu.add(menuItem);
        menu.add(submenu);

        menu = new JMenu("Add / Remove");
        menuBar.add(menu);

        menuItem = new JMenuItem("Add Movie");
        menuItem.addActionListener(aggiungiFilm()); 
        menu.add(menuItem);

        menuItem = new JMenuItem("Add Screening");
        menuItem.addActionListener(aggiungiProiezione()); 
        menu.add(menuItem);

        menuItem = new JMenuItem("Add Hall");
        menuItem.addActionListener(addHall()); 
        menu.add(menuItem);

        menuItem = new JMenuItem("Remover");
        menuItem.addActionListener(Remover()); 
        menu.add(menuItem);

        menu = new JMenu("Gestione Fatturati");
        menuBar.add(menu);

        menuItem = new JMenuItem("Sales Volume");
        menuItem.addActionListener(salesVolume()); 
        menu.add(menuItem);

        menu = new JMenu("Configuration");
        menuBar.add(menu);

        menuItem = new JMenuItem("Modify/View");
        menuItem.addActionListener(modificaImpostazioni()); 
        menu.add(menuItem);

        menu = new JMenu("Help");
        menuBar.add(menu);

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(About()); 
        menu.add(menuItem);

        return menuBar;
    }

    private JPanel defaultSchermata() {
        JPanel pannello = new JPanel();
        ImageIcon immagine = new ImageIcon("immagini/logo_trasparente.png");
        pannello.add(new JLabel(scalaImmagine(immagine, 640, 433)));
        return pannello;
    }

//////////////////////////////////////////////////// AZIONI /////////////////////////////////////////////////////
    private ActionListener showBooking() {

        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();

                String[] columnNames = {"ID ", "ID Screening", "Date&Time", "No° Glasses", "Price", "Booking Status"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);
                table.setFillsViewportHeight(true);
                JScrollPane scrollPane = new JScrollPane(table);
                try {
                    ArrayList<Booking> booking = controller.showBooking();
                    for (Booking b : booking) {
                        Object[] datas = {b.getId_prenotazione(), b.getScreening().getId_proiezione(), b.getData_ora_sql(), b.getNumber_of_glasses(), b.getPrezzo(), b.getBooking_status()};
                        tableModel.addRow(datas);
                    }
                    display.add(scrollPane);

                } catch (SQLException ex) {
                    serverError();
                }
                outputGrafico.setText("Booking list");
            }
        };
        return evento;
    }

    private ActionListener showScreening(final int tipo) {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();
                String[] columnNames = {"ID", "Date&Time", "ID Movie", "ID Hall", "Type", "Price"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);
                table.setFillsViewportHeight(true);
                JScrollPane scrollPane = new JScrollPane(table);
                try {
                    ArrayList<Screening> Proiezioni = controller.visualizzaProiezione(tipo);
                    for (Screening p : Proiezioni) {
                        Object[] datas = {p.getId_proiezione(), p.getData_ora_friendly(), p.getFilm().getId_film(), p.getRoom().getId_sala(), p.getTipo_proiezione(), p.getPrezzo()};
                        tableModel.addRow(datas);
                    }
                    display.add(scrollPane);

                } catch (SQLException ex) {
                    serverError();
                }
                outputGrafico.setText("Visualzatore Proiezioni, val tipo: " + tipo);
            }
        };
        return evento;
    }

    private ActionListener aggiungiProiezione() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaGUI(pannelloCaricamento);
                aggiornaGUI(new PanelAddScreening(controller, outputGrafico));
            }
        };
        return evento;
    }

    private ActionListener visualizzaFilm() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaGUI(pannelloCaricamento);
                aggiornaGUI(new PanelViewFilm(controller, outputGrafico));
            }
        };
        return evento;
    }

    private ActionListener aggiungiFilm() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaGUI(pannelloCaricamento);
                aggiornaGUI(new PanelAddFilm(controller, outputGrafico));
            }
        };
        return evento;
    }

    private ActionListener Remover() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaGUI(pannelloCaricamento);
                aggiornaGUI(new PanelRemover(controller, outputGrafico));
            }
        };
        return evento;
    }

    private ActionListener addHall() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaGUI(pannelloCaricamento);
                aggiornaGUI(new PanelAddHall(controller, outputGrafico));
            }
        };
        return evento;
    }

    private ActionListener modificaImpostazioni() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaGUI(pannelloCaricamento);
                aggiornaGUI(new PanelConfig(controller, outputGrafico));
            }
        };
        return evento;
    }

    private ActionListener About() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaGUI(pannelloCaricamento);
                aggiornaGUI(new PanelAbout(controller, outputGrafico));
            }
        };
        return evento;
    }

    private ActionListener salesVolume() { //fatturato
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaGUI(pannelloCaricamento);
                aggiornaGUI(new PanelSalesVolume(controller, outputGrafico));
            }
        };
        return evento;
    }

    private JMenu CreateHallList(JMenu submenu) {
        ArrayList<Room> sale = new ArrayList<>();
        ArrayList<JMenuItem> menuItem = new ArrayList<>();
        try {
            sale = controller.visualizzaSale();
        } catch (SQLException ex) {
            System.out.println("Da Gestire l'eccezione!!!!");
        } catch (java.lang.NullPointerException ex) {
            System.out.println("Da Gestire l'eccezione java.lang.NullPointerException ex nel menu bar CreateHallList !!!!");
        }

        for (int i = 0; i < sale.size(); i++) {
            menuItem.add(new JMenuItem("Sala " + sale.get(i).getId_sala()));
            menuItem.get(i).addActionListener(hallStatus(sale.get(i).getId_sala()));
            submenu.add(menuItem.get(i));
        }
        return submenu;
    }

    private ActionListener hallStatus(final int id_sala) {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaGUI(pannelloCaricamento);
                aggiornaGUI(new PanelHallState(controller, outputGrafico, id_sala));
            }
        };
        return evento;
    }
///////////////////////////////////////////////////////   METODI DI USO COMUNE      ////////////////////////////////

    private void aggiornaGUI(final JPanel displayPanel) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                display.removeAll();
                display.add(displayPanel, BorderLayout.CENTER);
                display.revalidate();
                display.repaint();
            }
        });
    }

    ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }

    private Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    private Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    void serverError() {
        outputGrafico.setText("Errore collegamento con il server.");
    }

}
