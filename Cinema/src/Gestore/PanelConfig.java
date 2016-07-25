package Gestore;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import oggetti.Config;

/**
 *
 * @author Yatin
 */
public class PanelConfig extends JPanel {

    public PanelConfig(final Controller_Gestore controller, final JLabel outputGrafico) {

        //final ArrayList<Config> c = new ArrayList<>();
        //c.clear();
        this.setLayout(new GridLayout(0, 2, 60, 20));

        final JTextField[] text = new JTextField[9];

        for (int i = 0; i < 7; i++) {
            text[i] = new JTextField();
        }

//               JButton refresh = new JButton("Refresh");
        JButton submit = new JButton("Submit");

        try {
            //c.add(gestore.aggiornaConfig());
            Config c = controller.getConfig();

            text[0].setText(String.valueOf(c.getPrezzo_vip()));
            text[1].setText(String.valueOf(c.getSconto()));
            text[2].setText(String.valueOf(c.getGlasses_price()));
            text[3].setText(String.valueOf(c.getOver_price()));
            text[4].setText(String.valueOf(c.getDisabled_price()));
            text[5].setText(String.valueOf(c.getOffset_time()));
            text[6].setText(String.valueOf(c.getBooking_validation_time()));

            this.add(new JLabel("Prezzo VIP:"));
            this.add(text[0]);
            this.add(new JLabel("Sconto:"));
            this.add(text[1]);
            this.add(new JLabel("Glasses Price:"));
            this.add(text[2]);
            this.add(new JLabel("Over Price (on-line):"));
            this.add(text[3]);
            this.add(new JLabel("Disabled Price:"));
            this.add(text[4]);
            this.add(new JLabel("Tempo pulizia sala: [MIN]"));
            this.add(text[5]);
            this.add(new JLabel("Durata validità biglietti non pagati: [MIN]"));
            this.add(text[6]);

            this.add(submit);
//                    impostazioni.add(refresh);

        } catch (SQLException ex) {
            Logger.getLogger(Gui_Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (controller.writeConfig(new Config(Double.parseDouble(text[0].getText()), Double.parseDouble(text[1].getText()), Double.parseDouble(text[2].getText()), Double.parseDouble(text[3].getText()), Double.parseDouble(text[4].getText()), Integer.parseInt(text[5].getText()), Integer.parseInt(text[6].getText()) ))) {
                    outputGrafico.setText("Modifica registrata con successo.");
                } else {
                    outputGrafico.setText("Errore durante il caricamento dei dati.");
                }
            }
        });
        outputGrafico.setText("Visualizzazione Impostazioni in Corso");
    }

}
