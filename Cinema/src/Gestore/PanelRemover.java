/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class PanelRemover extends JPanel {

    Controller_Gestore controller;
    JLabel outputGrafico;

    public PanelRemover(Controller_Gestore controller, JLabel outputGrafico) {
        this.controller = controller;
        this.outputGrafico = outputGrafico;

        outputGrafico.setText("Sistema di cancellazione dati.");

        this.setLayout(new BorderLayout());
        this.add(drawGui(new JPanel(new GridLayout(1, 3, 70, 0))), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private JPanel drawGui(JPanel pannelloDisplay) {
        JPanel ritorno = new JPanel(new BorderLayout());
        ritorno.add(new JLabel("<html><br><br><br><br><center><h2>Guida Al Utilizzo:</h2></center></br><h3>Per eliminare un elemento bisogna selezionarlo dalla lista e poi clikkare il relativo tasto cancella.</h3><br><br><br></html>", SwingConstants.CENTER), BorderLayout.NORTH);
        ritorno.add(pannelloDisplay, BorderLayout.CENTER);
        ritorno.add(new JLabel("<html><br><br><br><center>Attenzione:</center></br><i>*E' possibile eliminare un film solo se quest'ultimo non è presente in alcuna proiezione<br>*E' possibile eliminare una proiezione soltanto se non vi sono delle prenotazioni ad essa associate.<br>*E' possibile eliminare una sala solo se ad essa non sono associate proiezioni.</i></html>", SwingConstants.CENTER), BorderLayout.SOUTH);

        try {
            JPanel uno = new JPanel(new BorderLayout());

            uno.add(new JLabel("Film: ", SwingConstants.CENTER), BorderLayout.NORTH);

            final DefaultListModel modelFilm = new DefaultListModel();
            final JList<Film> listaFilm = new JList(modelFilm);
            JScrollPane paneFilm = new JScrollPane(listaFilm);
            ArrayList<Film> Films;
            Films = controller.visualizzaFilm(0);
            for (Film f : Films) {
                modelFilm.addElement(f);
            }

            uno.add(paneFilm, BorderLayout.CENTER);

            JButton deleteFilm = new JButton("delete");

            deleteFilm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (listaFilm.getSelectedValue() == null) {
                        outputGrafico.setText("Selezionare il film dalla lista prima di premere il tasto.");
                    } else {
                        //Creazione oggetto
                        if (controller.eliminaFilm(listaFilm.getSelectedValue().getId_film())) {
                            outputGrafico.setText("Film eliminato con successo.");
                            reDrawGui();
                        } else {
                            outputGrafico.setText("Errore eliminazione, accertarsi che il film non sia in programmazione.");
                        }
                    }

                }
            });
            uno.add(deleteFilm, BorderLayout.SOUTH);
            pannelloDisplay.add(uno);

            JPanel due = new JPanel(new BorderLayout());

            due.add(new JLabel("Proiezioni: ", SwingConstants.CENTER), BorderLayout.NORTH);

            final DefaultListModel modelPro = new DefaultListModel();
            final JList<Screening> listaProiezioni = new JList(modelPro);
            JScrollPane panePro = new JScrollPane(listaProiezioni);
            ArrayList<Screening> Proiezioni;

            Proiezioni = controller.visualizzaProiezione(2);
            for (Screening p : Proiezioni) {
                modelPro.addElement(p);
            }

            due.add(panePro, BorderLayout.CENTER);

            JButton deletePro = new JButton("delete");
            deletePro.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (listaProiezioni.getSelectedValue() == null) {
                        outputGrafico.setText("Selezionare la proiezione dalla lista prima di premere il tasto.");
                    } else {
                        //Creazione oggetto
                        if (controller.eliminaProiezione(listaProiezioni.getSelectedValue().getId_proiezione())) {
                            outputGrafico.setText("Proiezione eliminata con successo.");
                            reDrawGui();
                        } else {
                            outputGrafico.setText("Errore eliminazione, la proiezione risilta avere dei bigletti venduti.");
                        }
                    }
                }
            });
            due.add(deletePro, BorderLayout.SOUTH);
            pannelloDisplay.add(due);

            JPanel tre = new JPanel(new BorderLayout());

            tre.add(new JLabel("Sale: ", SwingConstants.CENTER), BorderLayout.NORTH);

            final DefaultListModel modelSale = new DefaultListModel();
            final JList<Room> listaSale = new JList(modelSale);
            JScrollPane paneSale = new JScrollPane(listaSale);
            ArrayList<Room> Sale;

            Sale = controller.visualizzaSale();
            for (Room s : Sale) {
                modelSale.addElement(s);
            }

            tre.add(paneSale, BorderLayout.CENTER);

            JButton deleteSale = new JButton("delete");
            deleteSale.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (listaSale.getSelectedValue() == null) {
                        outputGrafico.setText("Selezionare la sala dalla lista prima di premere il tasto.");
                    } else {
                        //Creazione oggetto
                        if (controller.eliminaSale(listaSale.getSelectedValue().getId_sala())) {
                            outputGrafico.setText("Sala eliminata con successo.");
                            reDrawGui();
                        } else {
                            outputGrafico.setText("Errore eliminazione, la sala è in uso da qualche proiezione.");
                        }
                    }

                }
            });

            tre.add(deleteSale, BorderLayout.SOUTH);
            pannelloDisplay.add(tre);

        } catch (SQLException ex) {
            outputGrafico.setText("Errore con il server");
        }
        return ritorno;
    }

    private void reDrawGui() {
        this.removeAll();
        this.add(drawGui(new JPanel(new GridLayout(1, 3, 70, 0))), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
