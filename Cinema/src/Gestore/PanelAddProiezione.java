package Gestore;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import oggetti.Film;
import oggetti.Proiezione;
import oggetti.Sala;

public class PanelAddProiezione extends JPanel {

    public PanelAddProiezione(final Controller_Gestore controller, final JLabel outputGrafico) {

        this.setLayout(new BorderLayout(10, 30));

        try {
            JPanel pannelloNord = new JPanel(new GridLayout(0, 2, 20, 20));
            JPanel pannello = new JPanel(new BorderLayout());

            pannello.add(new JLabel("Film: "), BorderLayout.NORTH);

            final DefaultListModel model = new DefaultListModel();
            final JList<Film> listaFilm = new JList(model);
            JScrollPane pane = new JScrollPane(listaFilm);

            ArrayList<Film> Films = controller.visualizzaFilm(0);
            for (Film f : Films) {
                model.addElement(f);
            }
            pannello.add(pane, BorderLayout.CENTER);
            pannelloNord.add(pannello);

            pannello = new JPanel(new BorderLayout());
            pannello.add(new JLabel("Sale: "), BorderLayout.NORTH);

            final DefaultListModel model1 = new DefaultListModel();
            final JList<Sala> listaSale = new JList(model1);
            JScrollPane pane1 = new JScrollPane(listaSale);

            ArrayList<Sala> Sale = controller.visualizzaSale();
            for (Sala s : Sale) {
                model1.addElement(s);
            }
            pannello.add(pane1, BorderLayout.CENTER);
            pannelloNord.add(pannello);
            this.add(pannelloNord, BorderLayout.NORTH);

            pannello = new JPanel(new BorderLayout());

            Date today = new Date();

            pannello.add(new JLabel("Data Ora: "), BorderLayout.WEST);

            final JSpinner selettoreDataOra = new JSpinner(new SpinnerDateModel(today, addDays(today, -1), null, Calendar.MONTH));
            JSpinner.DateEditor selettoreGiornoGui = new JSpinner.DateEditor(selettoreDataOra, "dd-MM-yyyy (EEEE) HH:mm");
            selettoreDataOra.setEditor(selettoreGiornoGui);
            pannello.add(selettoreDataOra, BorderLayout.CENTER);

            this.add(pannello, BorderLayout.CENTER);

            pannello = new JPanel(new GridLayout(0, 4));

            pannelloNord = new JPanel(new GridLayout(0, 1, 10, 50));

            pannello.add(new JLabel("Tipo: "));
            String[] stringaLista = {"Normale", "3D", "IMAX 3D", "Spettacolo"};
            final JComboBox tipoLista = new JComboBox(stringaLista);
            tipoLista.setSelectedIndex(0);
            pannello.add(tipoLista);

            pannello.add(new JLabel("Prezzo: "));

            final JSpinner spinnerPrezzo = new JSpinner(new SpinnerNumberModel(6, 3, 20, 0.5));
            pannello.add(spinnerPrezzo);

            pannelloNord.add(pannello);
            JButton aggiungiProiezione = new JButton("Registra");
            pannelloNord.add(aggiungiProiezione);

            aggiungiProiezione.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (listaFilm.getSelectedValue() == null || listaSale.getSelectedValue() == null) {
                        outputGrafico.setText("Riempire tutti i campi");
                    } else {
                        //Creazione oggetto
                        Proiezione proiezione = new Proiezione(0, dateToCalendar((Date) selettoreDataOra.getValue()), listaFilm.getSelectedValue().getId_film(), (int) listaSale.getSelectedValue().getId_sala(), (String) tipoLista.getSelectedItem(), (double) spinnerPrezzo.getValue());
                        try {
                            if (controller.scriviProiezione(proiezione)) {
                                outputGrafico.setText("Nuova proiezione aggiunta con successo.");
                            } else {
                                outputGrafico.setText("La sala già occupata, cambiare l'orario.");
                            }
                        } catch (SQLException ex) {
                            outputGrafico.setText("Errore con il server.");
                        }
                    }
                }
            });
            this.add(pannelloNord, BorderLayout.SOUTH);
        } catch (SQLException ex) {
            outputGrafico.setText("Errore collegamento con il server.");
        }
    }

    private Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    private Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

}
