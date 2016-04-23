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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Yoga
 */
public class Gui_Gestore extends JFrame {

    private final Gestore gestore;

    private JLabel outputGrafico;

    private JPanel visualizzaSale;
    private JPanel visualizzaFilm;
    private JLabel schermataDefault;

    public Gui_Gestore() {
        gestore = new Gestore();
        creaGui();
    }

    private void creaGui() {
        JPanel nord = creaSchermate();

        JPanel sud = new JPanel(new BorderLayout());
        outputGrafico = new JLabel("Output del risultato", SwingConstants.CENTER);
        sud.add(outputGrafico, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setJMenuBar(creaMenuBar());
        this.add(nord, BorderLayout.NORTH);
        this.add(sud, BorderLayout.SOUTH);
        this.setTitle("Pannello Gestore");
        this.setBounds(100, 100, 700, 700);
        this.setResizable(false);

        try {
            ImageIcon immagine = new ImageIcon(ImageIO.read(new File("immagini/logo_trasparente.png")));
            setIconImage(immagine.getImage());

        } catch (IOException ex) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Errore caricamento icona", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
        }

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
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menuItem.addActionListener(visualizzaSale());
        menu.add(menuItem);

        menuItem = new JMenuItem("Film", KeyEvent.VK_F);
        //menuItem.setMnemonic(KeyEvent.VK_T); 
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menuItem.addActionListener(visualizzaFilm());
        menu.add(menuItem);

        menuItem = new JMenuItem("Prenotazioni");
        //menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.addActionListener(azione4("Visualizzatore Prenotazioni"));
        menu.add(menuItem);

        menu.addSeparator();
        submenu = new JMenu("Proiezioni");
        submenu.setMnemonic(KeyEvent.VK_P);

        menuItem = new JMenuItem("Odierne");
        menuItem.addActionListener(azione4("Visualizzazione Stato Proiezioni Odierne"));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Future");
        menuItem.addActionListener(azione4("Visualizzazione Stato Proiezioni Future"));
        submenu.add(menuItem);
        menu.add(submenu);

        menu = new JMenu("Crea / Modifica");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
        menuBar.add(menu);

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

    private void nascondiTutto() {
        visualizzaSale.setVisible(false);
        visualizzaFilm.setVisible(false);
        schermataDefault.setVisible(false);
    }

    //////////////////////////////////////////////////////// PANNELLI SCHERMATE ///////////////////////////////////////////////////////
    private JPanel creaSchermate() {
        JPanel pannello = new JPanel();

        try {
            ImageIcon immagine = new ImageIcon(ImageIO.read(new File("immagini/logo.png")));
            immagine = new ImageIcon(immagine.getImage().getScaledInstance(700, 700, java.awt.Image.SCALE_SMOOTH));
            schermataDefault = new JLabel(immagine);
            pannello.add(schermataDefault);
        } catch (IOException ex) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Errore caricamento logo", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
        }

        visualizzaFilm = new JPanel(new BorderLayout());
        JLabel provaVisualizzazioneFilm = new JLabel("provaVisualizzazioneDinamicaFilm", SwingConstants.CENTER);
        visualizzaFilm.add(provaVisualizzazioneFilm, BorderLayout.CENTER);
        try {            
            ImageIcon immagine = new ImageIcon(ImageIO.read(new URL("https://s-media-cache-ak0.pinimg.com/736x/a8/f6/c5/a8f6c5f4440106e3e38b17935a7e6609.jpg")));
            immagine = new ImageIcon(immagine.getImage().getScaledInstance(600, 500, java.awt.Image.SCALE_SMOOTH));
            JLabel immagineInternet = new JLabel(immagine);
            visualizzaFilm.add(immagineInternet, BorderLayout.SOUTH);

        } catch (IOException ex) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Errore caricamento copertina", "Attenzione!!!", JOptionPane.WARNING_MESSAGE);
        }
        visualizzaFilm.setVisible(false);
        pannello.add(visualizzaFilm);

        visualizzaSale = new JPanel(new BorderLayout());
        JLabel provaVisualizzazioneSale = new JLabel("provaVisualizzazioneDinamicaSale", SwingConstants.CENTER);
        visualizzaSale.add(provaVisualizzazioneSale, BorderLayout.CENTER);
        visualizzaSale.setVisible(false);
        pannello.add(visualizzaSale);

        return pannello;
    }

//////////////////////////////////////////////////// AZIONI /////////////////////////////////////////////////////
    private ActionListener azione1() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputGrafico.setText("Azione1");
            }
        };
        return evento;
    }

    private ActionListener azione2() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputGrafico.setText("Azione2");
            }
        };
        return evento;
    }

    private ActionListener azione3() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputGrafico.setText("Azione3");
            }
        };
        return evento;
    }

    private ActionListener azione4(final String frase) {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputGrafico.setText(frase);
            }
        };
        return evento;
    }

    private ActionListener visualizzaSale() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nascondiTutto();
                visualizzaSale.setVisible(true);
                outputGrafico.setText("Visualizzazione Sale in Corso");
            }
        };
        return evento;
    }

    private ActionListener visualizzaFilm() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nascondiTutto();
                visualizzaFilm.setVisible(true);
                outputGrafico.setText("Visualizzazione FIlm in Corso");
            }
        };
        return evento;
    }

}
