/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import obj.Hall;
import obj.Projection;
import obj.Booking;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Yoga
 */
public class AdminGui extends JFrame {

    private final AdminController controller;

    private JPanel display;
    private JLabel bottomText;
    private JPanel loadingPanel;

    public AdminGui() {

        controller = new AdminController();
        display = new JPanel(new BorderLayout());
        draw();
        JLabel imagineCaricamento = new JLabel(new ImageIcon("immagini/caricamento.gif"));
        loadingPanel = new JPanel();
        loadingPanel.add(imagineCaricamento);

    }

    private void draw() {
        this.setJMenuBar(craftMenuBar());  //Costruttore del menu a tendina

        display = splashScreen();     //Visualizzo logo in splash 

        JPanel sud = new JPanel(new BorderLayout());
        bottomText = new JLabel("Output del risultato", SwingConstants.CENTER);
        sud.add(bottomText, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(display, BorderLayout.CENTER);
        this.add(sud, BorderLayout.SOUTH);
        this.setTitle("Admin");
        this.setBounds(900, 100, 850, 700);

//        this.setResizable(false);
        ImageIcon icona = new ImageIcon("images/logo.png");
        setIconImage(icona.getImage());

    }

    private JMenuBar craftMenuBar() {
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
        menuItem.addActionListener(viewFilmList());
        menu.add(menuItem);

        menuItem = new JMenuItem("Booking");
        menuItem.addActionListener(showBooking());
        menu.add(menuItem);

        menu.addSeparator();
        submenu = new JMenu("Projection");

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
        menuItem.addActionListener(addFilm());
        menu.add(menuItem);

        menuItem = new JMenuItem("Add Projection");
        menuItem.addActionListener(addProjection());
        menu.add(menuItem);

        menuItem = new JMenuItem("Add Hall");
        menuItem.addActionListener(addHall());
        menu.add(menuItem);

        menuItem = new JMenuItem("Remover");
        menuItem.addActionListener(Remover());
        menu.add(menuItem);

        menu = new JMenu("Income Analysis");
        menuBar.add(menu);

        menuItem = new JMenuItem("Sales Volume");
        menuItem.addActionListener(salesVolume());
        menu.add(menuItem);

        menu = new JMenu("Configuration");
        menuBar.add(menu);

        menuItem = new JMenuItem("Modify/View");
        menuItem.addActionListener(modifyConfiguration());
        menu.add(menuItem);

        menu = new JMenu("Help");
        menuBar.add(menu);

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(About());
        menu.add(menuItem);

        return menuBar;
    }

    private JPanel splashScreen() {
        JPanel pannello = new JPanel();
        ImageIcon immagine = new ImageIcon("images/logo.png");
        pannello.add(new JLabel(new ImageIcon(immagine.getImage().getScaledInstance(640, 433, java.awt.Image.SCALE_SMOOTH))));
        return pannello;
    }

//////////////////////////////////////////////////// AZIONI /////////////////////////////////////////////////////
    private ActionListener showBooking() {

        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();

                String[] columnNames = {"ID ", "Projection", "Date&Time", "No° Glasses", "Price", "Booking Status", "Email"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);
                table.setFillsViewportHeight(true);
                JScrollPane scrollPane = new JScrollPane(table);
                try {
                    ArrayList<Booking> booking = controller.getBooking();
                    for (Booking b : booking) {
                        Object[] datas = {b.getIdBooking(), b.getProjection().getIdProjection(), b.getData_ora_sql(), b.getNumberOfGlasses(), b.getPrice(), b.getBookingStatus(), b.getEmail()};
                        tableModel.addRow(datas);
                    }
                    display.add(scrollPane);

                } catch (SQLException ex) {
                    serverError();
                }
                bottomText.setText("Booking list");
            }
        };
        return event;
    }

    private ActionListener showScreening(final int tipo) {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();
                String[] columnNames = {"ID", "Date&Time", "ID Movie", "ID Hall", "Type", "Price"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);
                TableColumn id = table.getColumnModel().getColumn(0);
                id.setMinWidth(50); id.setMaxWidth(50); id.setPreferredWidth(50);
                table.setFillsViewportHeight(true);
                JScrollPane scrollPane = new JScrollPane(table);
                try {
                    ArrayList<Projection> projection = controller.getProjection(tipo);
                    for (Projection p : projection) {
                        Object[] datas = {p.getIdProjection(), p.getData_ora_friendly(), p.getFilm().getIdFilm(), p.getRoom().getIdHall(), p.getpProjectionType(), p.getPrice()};
                        tableModel.addRow(datas);
                    }
                    display.add(scrollPane);

                } catch (SQLException ex) {
                    serverError();
                }
                display.revalidate();
                display.repaint();
                bottomText.setText("Visualzatore Proiezioni, val tipo: " + tipo);
            }
        };
        return event;
    }

    private ActionListener addProjection() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI(loadingPanel);
                updateGUI(new PanelAddProjection(controller, bottomText));
            }
        };
        return evento;
    }

    private ActionListener viewFilmList() {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI(loadingPanel);
                updateGUI(new PanelViewFilm(controller, bottomText));
            }
        };
        return event;
    }

    private ActionListener addFilm() {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI(loadingPanel);
                updateGUI(new PanelAddFilm(controller, bottomText));
            }
        };
        return event;
    }

    private ActionListener Remover() {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI(loadingPanel);
                updateGUI(new PanelRemover(controller, bottomText));
            }
        };
        return event;
    }

    private ActionListener addHall() {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI(loadingPanel);
                updateGUI(new PanelAddHall(controller, bottomText));
            }
        };
        return event;
    }

    private ActionListener modifyConfiguration() {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI(loadingPanel);
                updateGUI(new PanelConfig(controller, bottomText));
            }
        };
        return event;
    }

    private ActionListener About() {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI(loadingPanel);
                updateGUI(new PanelAbout(controller, bottomText));
            }
        };
        return event;
    }

    private ActionListener salesVolume() { //fatturato
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI(loadingPanel);
                updateGUI(new PanelSalesVolume(controller, bottomText));
            }
        };
        return event;
    }

    private JMenu CreateHallList(JMenu submenu) {
        ArrayList<Hall> hall = new ArrayList<>();
        ArrayList<JMenuItem> menuItem = new ArrayList<>();
        try {
            hall = controller.getHall();
        } catch (SQLException ex) {
            System.out.println("ERRORE: lettura sale");
            serverError();
        } catch (java.lang.NullPointerException ex) {
            System.out.println("ERRORE: lettura sale");
            serverError();
        }

        for (int i = 0; i < hall.size(); i++) {
            menuItem.add(new JMenuItem("Sala " + hall.get(i).getIdHall()));
            menuItem.get(i).addActionListener(hallStatus(hall.get(i).getIdHall()));
            submenu.add(menuItem.get(i));
        }
        return submenu;
    }

    private ActionListener hallStatus(final int id_sala) {
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI(loadingPanel);
                updateGUI(new PanelHallState(controller, bottomText, id_sala));
            }
        };
        return event;
    }
///////////////////////////////////////////////////////   COMMON USE STUFF     ////////////////////////////////

    private void updateGUI(final JPanel displayPanel) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                display.removeAll();
                display.add(displayPanel, BorderLayout.CENTER);
                display.revalidate();
                display.repaint();
            }
        });
    }

    void serverError() {
        bottomText.setText("Errore collegamento con il server.");
    }

}
