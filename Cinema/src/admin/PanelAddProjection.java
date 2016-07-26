package admin;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import obj.*;

public class PanelAddProjection extends JPanel {

    public PanelAddProjection(final AdminController controller, final JLabel outputGrafico) {

        this.setLayout(new BorderLayout(10, 30));

        try {
            JPanel pannelloNord = new JPanel(new GridLayout(0, 2, 20, 20));
            JPanel pannello = new JPanel(new BorderLayout());

            pannello.add(new JLabel("Film: "), BorderLayout.NORTH);

            final DefaultListModel model = new DefaultListModel();
            final JList<Film> listaFilm = new JList(model);
            JScrollPane pane = new JScrollPane(listaFilm);

            ArrayList<Film> Films = controller.getFilm(0);
            for (Film f : Films) {
                model.addElement(f);
            }

            pannello.add(pane, BorderLayout.CENTER);
            pannelloNord.add(pannello);

            pannello = new JPanel(new BorderLayout());
            pannello.add(new JLabel("Hall: "), BorderLayout.NORTH);

            final DefaultListModel model1 = new DefaultListModel();
            final JList<Hall> listaSale = new JList(model1);
            JScrollPane pane1 = new JScrollPane(listaSale);

            ArrayList<Hall> Sale = controller.getHall();
            for (Hall s : Sale) {
                model1.addElement(s);
            }
            pannello.add(pane1, BorderLayout.CENTER);
            pannelloNord.add(pannello);
            this.add(pannelloNord, BorderLayout.NORTH);

            pannello = new JPanel(new BorderLayout());

            Date today = new Date();

            pannello.add(new JLabel("Date & Time: "), BorderLayout.WEST);

            final JSpinner selettoreDataOra = new JSpinner(new SpinnerDateModel(today, addDays(today, -1), null, Calendar.MONTH));
            JSpinner.DateEditor selettoreGiornoGui = new JSpinner.DateEditor(selettoreDataOra, "dd-MM-yyyy (EEEE) HH:mm");
            selettoreDataOra.setEditor(selettoreGiornoGui);
            pannello.add(selettoreDataOra, BorderLayout.CENTER);

            this.add(pannello, BorderLayout.CENTER);

            pannello = new JPanel(new GridLayout(0, 4));

            pannelloNord = new JPanel(new GridLayout(0, 1, 10, 50));

            pannello.add(new JLabel("Type: "));
            String[] stringaLista = {"Normal", "3D", "IMAX 3D", "Live Event"};

            final JComboBox tipoLista = new JComboBox(stringaLista);
            tipoLista.setSelectedIndex(0);
            pannello.add(tipoLista);

            pannello.add(new JLabel("Price: "));

            final JSpinner spinnerPrezzo = new JSpinner(new SpinnerNumberModel(6, 3, 20, 0.5));
            pannello.add(spinnerPrezzo);

            pannelloNord.add(pannello);
            JButton aggiungiProiezione = new JButton("Save");
            pannelloNord.add(aggiungiProiezione);

            aggiungiProiezione.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (listaFilm.getSelectedValue() == null || listaSale.getSelectedValue() == null) {
                        outputGrafico.setText("Please fill all form.");
                    } else {
                        //Creazione oggetto
                        Projection proiezione = new Projection(0, dateToCalendar((Date) selettoreDataOra.getValue()), new Film(listaFilm.getSelectedValue().getIdFilm()), new Hall(listaSale.getSelectedValue().getIdHall()), tipoLista.getSelectedIndex(), (double) spinnerPrezzo.getValue());
                        try {
                            if (controller.writeProjection(proiezione)) {
                                outputGrafico.setText("New Projection has been saved.");
                            } else {
                                outputGrafico.setText("The Hall is occupied by another projection.");
                            }
                        } catch (SQLException ex) {
                            outputGrafico.setText("ERROR: Server Failure");
                        }
                    }
                }
            });
            this.add(pannelloNord, BorderLayout.SOUTH);
        } catch (SQLException ex) {
            outputGrafico.setText("ERROR: Server Failure");
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
