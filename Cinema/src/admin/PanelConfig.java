package admin;

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
import obj.Config;

/**
 *
 * @author Yatin
 */
public class PanelConfig extends JPanel {

    public PanelConfig(final AdminController controller, final JLabel outputGrafico) {

        //final ArrayList<Config> c = new ArrayList<>();
        //c.clear();
        this.setLayout(new GridLayout(0, 2, 60, 20));

        final JTextField[] text = new JTextField[9];

        for (int i = 0; i < 6; i++) {
            text[i] = new JTextField();
        }

//               JButton refresh = new JButton("Refresh");
        JButton submit = new JButton("Submit");

        try {
            //c.add(gestore.aggiornaConfig());
            Config c = controller.getConfig();

            text[0].setText(String.valueOf(c.getVipOverprice()));
            text[1].setText(String.valueOf(c.getGlassesPrice()));
            text[2].setText(String.valueOf(c.getHandicapPrice()));
            text[3].setText(String.valueOf(c.getOffsetTime()));
            text[4].setText(String.valueOf(c.getBookingValidationTime()));
            text[5].setText(String.valueOf(c.getTicket_validation_ip()));


            this.add(new JLabel("VIP Price:"));
            this.add(text[0]);
            this.add(new JLabel("Glasses Price:"));
            this.add(text[1]);
            this.add(new JLabel("Handicap Price:"));
            this.add(text[2]);
            this.add(new JLabel("Hall Preparation Time: [MIN]"));
            this.add(text[3]);
            this.add(new JLabel("Booking Validation Time: [MIN]"));
            this.add(text[4]);
            this.add(new JLabel("Ip ticket validation:"));
            this.add(text[5]);
            this.add(submit);
//                    impostazioni.add(refresh);

        } catch (SQLException ex) {
            Logger.getLogger(AdminGui.class.getName()).log(Level.SEVERE, null, ex);
        }

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (controller.writeConfig(new Config(Double.parseDouble(text[0].getText()), Double.parseDouble(text[1].getText()), Double.parseDouble(text[2].getText()), Integer.parseInt(text[3].getText()), Integer.parseInt(text[4].getText()),text[5].getText()))) {
                        outputGrafico.setText("New configuration has been saved.");
                    } else {
                        outputGrafico.setText("ERROR: Be sure you insert corret value");
                    }
                } catch (SQLException ex) {
                    outputGrafico.setText("ERROR: Upload Fail");
                }
            }
        });
        outputGrafico.setText("Configuration");
    }

}
