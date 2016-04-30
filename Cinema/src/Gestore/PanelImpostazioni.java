/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Yoga
 */
public class PanelImpostazioni extends JPanel {

    public PanelImpostazioni(Controller_Gestore controller, JLabel outputGrafico) {

                //final ArrayList<Config> c = new ArrayList<>();
        //c.clear();
        this.setLayout(new GridLayout(0, 2));

        JTextField[] text = new JTextField[8];
        for (int i = 0; i < 8; i++) {
            text[i] = new JTextField();
        }

//               JButton refresh = new JButton("Refresh");
        JButton submit = new JButton("Submit");

        try {
            //c.add(gestore.aggiornaConfig());
            Config c = controller.visualizzaConfig();

            text[0].setText(String.valueOf(c.getPrezzo_vip()));
            text[1].setText(String.valueOf(c.getSconto()));
            text[2].setText(String.valueOf(c.getPopcorn_s()));
            text[3].setText(String.valueOf(c.getPopcorn_m()));
            text[4].setText(String.valueOf(c.getPopcorn_l()));
            text[5].setText(String.valueOf(c.getBibita_s()));
            text[6].setText(String.valueOf(c.getBibita_m()));
            text[7].setText(String.valueOf(c.getBibita_l()));

            this.add(new JLabel("Prezzo VIP:"));
            this.add(text[0]);
            this.add(new JLabel("Sconto:"));
            this.add(text[1]);
            this.add(new JLabel("Popcorn Piccolo:"));
            this.add(text[2]);
            this.add(new JLabel("Popcorn Medio:"));
            this.add(text[3]);
            this.add(new JLabel("Popcorn Jumbo:"));
            this.add(text[4]);
            this.add(new JLabel("Bibita Piccola:"));
            this.add(text[5]);
            this.add(new JLabel("Bibita Media:"));
            this.add(text[6]);
            this.add(new JLabel("Bibita Grande:"));
            this.add(text[7]);
            this.add(submit);
//                    impostazioni.add(refresh);

        } catch (SQLException ex) {
            Logger.getLogger(Gui_Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }

                //Sei sicuro di volerlo mettere è un pò una ripetizione di questo stesso action listener
//                refresh.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent ae) {
//                        
//                        try {
//                            Config c c.add(gestore.aggiornaConfig());
//                        } catch (SQLException ex) {
//                            Logger.getLogger(Gui_Gestore.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        text[0].setText(String.valueOf(c.get(0).getPrezzo_vip()));
//                        text[1].setText(String.valueOf(c.get(0).getSconto()));
//                        text[2].setText(String.valueOf(c.get(0).getPopcorn_s()));
//                        text[3].setText(String.valueOf(c.get(0).getPopcorn_m()));
//                        text[4].setText(String.valueOf(c.get(0).getPopcorn_l()));
//                        text[5].setText(String.valueOf(c.get(0).getBibita_s()));
//                        text[6].setText(String.valueOf(c.get(0).getBibita_m()));
//                        text[7].setText(String.valueOf(c.get(0).getBibita_l()));
//
//                    }
//                });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

//                        String qry = "INSERT INTO Config(prezzo_vip,sconto,popcorn_s,popcorn_m,popcorn_l,bibita_s,bibita_m,bibita_l) VALUES("
//                                + "'" + text[0].getText() + "','" + text[1].getText() + "','"
//                                + text[2].getText() + "','" + text[3].getText() + "','" + text[4].getText() + "','"
//                                + text[5].getText() + "','" + text[6].getText() + "','" + text[7].getText() + "')";
//                        SQLConnessione db = new SQLConnessione();
//                        db.creaConnessione();
//                        try {
//                            db.updateQuery(qry);
//                        } catch (SQLException ex) {
//                            Logger.getLogger(Gui_Gestore.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        db.chiudiConnessione();
//    public Config(double prezzo_vip, double sconto, double popcorn_s, double popcorn_m, double popcorn_l, double bibita_s, double bibita_m, double bibita_l) {
                if (controller.scriviConfig(new Config(Double.parseDouble(text[0].getText()), Double.parseDouble(text[1].getText()), Double.parseDouble(text[2].getText()), Double.parseDouble(text[3].getText()), Double.parseDouble(text[4].getText()), Double.parseDouble(text[5].getText()), Double.parseDouble(text[6].getText()), Double.parseDouble(text[7].getText())))) {
                    outputGrafico.setText("Modifica registrata con successo.");
                } else {
                    outputGrafico.setText("Errore durante il caricamento dei dati.");
                }
            }
        });
        outputGrafico.setText("Visualizzazione Impostazioni in Corso");
    }

}
