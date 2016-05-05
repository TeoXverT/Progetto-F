package Cliente;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import oggetti.Film;
import oggetti.Proiezione;


/**
 *
 * @author Riccardo
 */
public class PageTwo extends JPanel {

    private Controller_Cliente controller;
    private Film film;
    private int deltaData;
    private ArrayList<Proiezione> proiezione;
    public PageTwo(Film film, int deltaData, Controller_Cliente controller) {
        //Il controller ti permettera di parlare con il database
        //deltaData è l'offset in giorni rispetto ad oggi, ad es: se oggi è lunedi ed il tab da dove viene selezionato il film è martedi allora questo valore vale 1
        //deltaData è temporaneo in attesa dello slider  laterale di selezione del orario        
        //film è proprio il film che viene clickato con tutti i dati del caso, guarda i suoi metodi
        //Tè dovrai eseguire una query per scoprire in quali orari viene proiettatto questo film il giorno oggi+deltaData 
        this.controller = controller;
        this.film = film;
        this.deltaData = deltaData;
        this.setLayout(new BorderLayout());
        crea_gui();
    }

    private void crea_gui() {
        JButton cover = new JButton("Indietro");
     
        this.add(cover, BorderLayout.SOUTH);
        
        cover.addActionListener(goBackEvent());
        
        this.add(new JLabel(" Titolo film: " + film.toString(), SwingConstants.CENTER), BorderLayout.NORTH);
        this.add(new JLabel(" Numero di Giorni dopo oggi: " + deltaData, SwingConstants.CENTER), BorderLayout.EAST);
        this.add(new JLabel(film.getDescrizione()), BorderLayout.WEST);
        
        Image image = null;
try {
    URL url = new URL(film.getLink_copertina());
    image = ImageIO.read(url);
} catch (IOException e) {
}
this.add(new JLabel (new ImageIcon(image)), BorderLayout.NORTH);        
        String ora = null; //ora sarà passata dallo slider in page 1
        try {        
         //           this.add(new PanelYoutube(film.getLink_youtube(), 200, 200), BorderLayout.CENTER);  da errore
         proiezione=controller.showByFilm(film.getId_film(), deltaData, ora);
        } catch (SQLException ex) {
            Logger.getLogger(PageTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
  /*  for(int i = 0; i < proiezione.size(); i++){

     this.add(new JLabel(proiezione.get(i).getTipo_proiezione()), BorderLayout.SOUTH);
     this.add(new JLabel((Icon) proiezione.get(i).getData_ora()), BorderLayout.SOUTH);
     this.add(new JLabel(proiezione.get(i).getId_sala()), BorderLayout.SOUTH);
     
}*/
  
    }
  

      
    

    private void goBack() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new PageOne(controller));
        this.revalidate();
        this.repaint();
    }

    private ActionListener goBackEvent() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        };
        return evento;}}
    