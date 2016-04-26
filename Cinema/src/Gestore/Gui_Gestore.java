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

import javax.imageio.ImageIO;
import javax.swing.*;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class Gui_Gestore extends JFrame {

    private final Gestore gestore;

    private JPanel display;
    private JLabel outputGrafico; //Scritte in basso

    private final JLabel imagineCaricamento = new JLabel(new ImageIcon("immagini/caricamento.gif"));
    private Component frameErrore = null;

    public Gui_Gestore() {
        gestore = new Gestore();
        display = new JPanel(new BorderLayout());
        creaGui();
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
        this.setBounds(100, 100, 700, 700);
//        this.setResizable(false);

        ImageIcon icona = new ImageIcon("immagini/logo_trasparente.png");
        setIconImage(icona.getImage());

    }

    private JMenuBar creaMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Visualizza");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);

        menuItem = new JMenuItem("Stato Sale", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menuItem.addActionListener(visualizzaSale());
        menu.add(menuItem);

        menuItem = new JMenuItem("Film", KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menuItem.addActionListener(visualizzaFilm());
        menu.add(menuItem);

        menuItem = new JMenuItem("Prenotazioni");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.addActionListener(visualizzaPrenotazioni());
        menu.add(menuItem);

        menu.addSeparator();
        submenu = new JMenu("Proiezioni");
        submenu.setMnemonic(KeyEvent.VK_P);

        menuItem = new JMenuItem("Odierne");
        menuItem.addActionListener(visualizzaProiezioni(0));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Future");
        menuItem.addActionListener(visualizzaProiezioni(1));
        submenu.add(menuItem);
        menu.add(submenu);

        menu = new JMenu("Crea / Modifica");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Aggiungi Film", KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menuItem.addActionListener(aggiungiFilm()); // cosa deve fare una volta premuto
        menu.add(menuItem);

        menu = new JMenu("Gestione Fatturati");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
        menuBar.add(menu);

        menu = new JMenu("Impostazioni");
        menu.setMnemonic(KeyEvent.VK_I);
        menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
        menuBar.add(menu);

        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_I);
        menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
        menuBar.add(menu);

        return menuBar;
    }

    private JPanel defaultSchermata() {
        JPanel pannello = new JPanel();
        ImageIcon immagine = new ImageIcon("immagini/logo_trasparente.png");
        pannello.add(new JLabel(scalaImmagine(immagine, 640, 433)));
        return pannello;
    }

//////////////////////////////////////////////////// AZIONI /////////////////////////////////////////////////////
    private ActionListener provaActionListener(final String frase) {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();
                outputGrafico.setText(frase);
            }
        };
        return evento;
    }

    private ActionListener aggiungiFilm() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();
                JPanel aggiungiFilm = new JPanel(new GridLayout(0, 2, 1, 50));
                JLabel titoloText = new JLabel("Titolo: ");
                JLabel genereText = new JLabel("Genere: ");
                JLabel durataText = new JLabel("Durata: ");
                JLabel descrizioneText = new JLabel("Descrione: ");
                JLabel linkText = new JLabel("Link: ");
                JLabel copertinaText= new JLabel("Copertina: ");
                JTextField titoloArea = new JTextField("inserisci qui il titolo del cazzo");
                JTextArea genereArea = new JTextArea();
                JTextArea durataArea = new JTextArea();
                JTextArea descrizioneArea = new JTextArea();
                JTextArea linkArea = new JTextArea();
                JTextArea copertinaArea = new JTextArea();
                //--------------------------------------------------------------
                aggiungiFilm.add(titoloText);
                aggiungiFilm.add(titoloArea);
                
                aggiungiFilm.add(genereText);
                aggiungiFilm.add(genereArea);
                
                aggiungiFilm.add(durataText);
                aggiungiFilm.add(durataArea);
                
                aggiungiFilm.add(descrizioneText);
                aggiungiFilm.add(descrizioneArea);
                
                aggiungiFilm.add(linkText);
                aggiungiFilm.add(linkArea);

                aggiungiFilm.add(copertinaText);
                aggiungiFilm.add(copertinaArea);
                display.add(aggiungiFilm);
                outputGrafico.setText("Aggiunta Film in Corso");
            }
        };
        return evento;
    }
    
    private ActionListener visualizzaSale() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();

                display.add(imagineCaricamento);

                threadSale().start();

                outputGrafico.setText("Visualizzazione Sale in Corso");
            }
        };
        return evento;
    }

    private Thread threadSale() {
        Thread t = new Thread(new Runnable() {
            public void run() {

                try {
                    JPanel visualizzaSale = new JPanel();

                    //Qua creo la grid delle copertine dei film
                    ImageIcon immagine = new ImageIcon(ImageIO.read(new URL("https://s-media-cache-ak0.pinimg.com/736x/a8/f6/c5/a8f6c5f4440106e3e38b17935a7e6609.jpg")));
                    visualizzaSale.add(new JLabel(scalaImmagine(immagine, 600, 500)));

//                  display.add(visualizzaFilm, BorderLayout.SOUTH); //NON FUNGE... DA SPIEGARE A TUTTI IL PERCHE
                    aggiornaGUI(visualizzaSale);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frameErrore, "Errore scaricamento immagini", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        );
        return t;
    }

    private ActionListener visualizzaProiezioni(final int tipo) {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();

                try {
                    ArrayList<Proiezione> Proiezioni = gestore.visualizzaPrenotazione(tipo);

                    JPanel visualizzaProiezioni = new JPanel(new GridLayout(0, 1));
                    for (Proiezione p : Proiezioni) {
                        visualizzaProiezioni.add(new JLabel(p.toString()));
                    }

                    display.add(visualizzaProiezioni);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frameErrore, "Errore Esecuzione Query", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
                }

                outputGrafico.setText("Visualzatore Proiezioni, val tipo: " + tipo);
            }
        };
        return evento;
    }

    private ActionListener visualizzaFilm() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();

                outputGrafico.setText("Visualizzazione FIlm in Corso");
            }
        };
        return evento;
    }

    private Thread threadFilm() {
        Thread t = new Thread(new Runnable() {
            public void run() {

                try {
                    JPanel visualizzaSale = new JPanel();

                    //Qua creo la grid delle copertine dei film
                    ImageIcon immagine = new ImageIcon(ImageIO.read(new URL("https://s-media-cache-ak0.pinimg.com/736x/a8/f6/c5/a8f6c5f4440106e3e38b17935a7e6609.jpg")));
                    visualizzaSale.add(new JLabel(scalaImmagine(immagine, 600, 500)));

//                  display.add(visualizzaFilm, BorderLayout.SOUTH); //NON FUNGE... DA SPIEGARE A TUTTI IL PERCHE
                    aggiornaGUI(visualizzaSale);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frameErrore, "Errore scaricamento immagini", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        );
        return t;
    }

    private ActionListener visualizzaPrenotazioni() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.removeAll();

                JLabel provaVisualizzazionePrenotazioni = new JLabel("provaVisualizzazioneDinamicaPrenotazioni", SwingConstants.CENTER);
                display.add(provaVisualizzazionePrenotazioni, BorderLayout.CENTER);

                outputGrafico.setText("Visualizzazione Prenotazioni in Corso");
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
}
