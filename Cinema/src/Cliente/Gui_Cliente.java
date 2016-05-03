/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Gestore.PanelAddHall;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import oggetti.Film;

/**
 *
 * @author Yoga
 */
public class Gui_Cliente extends JFrame {

    JPanel display = new JPanel(new BorderLayout());
    Controller_Cliente controller = new Controller_Cliente();

    private JPanel pannelloCaricamento;
    private JLabel imagineCaricamento;

    public Gui_Cliente() {
        imagineCaricamento = new JLabel(new ImageIcon("immagini/caricamento.gif"));
        pannelloCaricamento = new JPanel();
        pannelloCaricamento.add(imagineCaricamento);

        Create_Gui();

    }

    public void Create_Gui() {

        this.setTitle("Pannello Cliente");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 810, 700);
        ImageIcon icona = new ImageIcon("immagini/logo_trasparente.png");
        setIconImage(icona.getImage());
//        this.setResizable(false);
        this.add(display);
        display.add(new PageOne(controller));
    }
//
//    private ActionListener OpenFilm(final Film film) {
//        ActionListener evento = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                aggiornaGUI(pannelloCaricamento);
//                aggiornaGUI(new Page2(film, controller));
//                System.out.println(film.toString());
//            }
//        };
//        return evento;
//    }

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
}
