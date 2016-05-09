package Cliente;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import oggetti.Film;
import oggetti.PanelYoutube;
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
    private int deltaTime;
    
    public PageTwo(Film film, int deltaData, Controller_Cliente controller, int deltaTime) throws SQLException {
        //Il controller ti permettera di parlare con il database
        //deltaData è l'offset in giorni rispetto ad oggi, ad es: se oggi è lunedi ed il tab da dove viene selezionato il film è martedi allora questo valore vale 1
        //deltaData è temporaneo in attesa dello slider  laterale di selezione del orario        
        //film è proprio il film che viene clickato con tutti i dati del caso, guarda i suoi metodi
        //Tè dovrai eseguire una query per scoprire in quali orari viene proiettatto questo film il giorno oggi+deltaData 
        this.controller = controller;
        this.film = film;
        this.deltaData = deltaData;
        this.deltaTime = deltaTime;
        this.setLayout(new BorderLayout());
        crea_gui();
    }

    private void crea_gui() throws SQLException {
        JButton cover = new JButton("Indietro");
        
        JPanel panel=new JPanel();
         this.add(panel,BorderLayout.WEST);
        Image image = null;
        try {
    URL url = new URL(film.getLink_copertina());
    image = ImageIO.read(url);
} catch (IOException e) {
}
   ImageIcon ii=new ImageIcon(image);
   JLabel label1=new JLabel(scalaImmagine(ii,250,350));
   panel.add(label1);
  // this.add(cover, BorderLayout.SOUTH);
    cover.addActionListener(goBackEvent());    
    this.add(new JLabel(" Titolo film: " + film.toString(), SwingConstants.CENTER), BorderLayout.NORTH);
    this.add(new JLabel(" Numero di Giorni dopo oggi: " + deltaData, SwingConstants.CENTER), BorderLayout.EAST);
    JLabel label2=new JLabel(film.getDescrizione());
    label2.setVerticalAlignment(JLabel.TOP);
    this.add(label2, BorderLayout.EAST);    
    

    proiezione = controller.showByFilm(film.getId_film(), deltaData, deltaTime);
    SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
    for(int i = 0; i < proiezione.size(); i++) {
        
        this.add(new JLabel(sdfDate.format(proiezione.get(i).getData_ora().getTime()) + "     " + proiezione.get(i).getTipo_proiezione() + "     " + proiezione.get(i).getId_sala() ), BorderLayout.SOUTH);
        
        
        
        
    }

//this.add(new PanelYoutube(film.getLink_youtube(), 200, 200), BorderLayout.SOUTH);    
  

      /*  String ora = null; //ora sarà passata dallo slider in page 1
        
    for(int i = 0; i < proiezione.size(); i++){

     this.add(new JLabel(proiezione.get(i).getTipo_proiezione()), BorderLayout.SOUTH);
     this.add(new JLabel((Icon) proiezione.get(i).getData_ora()), BorderLayout.SOUTH);
     this.add(new JLabel(proiezione.get(i).getId_sala()), BorderLayout.SOUTH);
     
}*/
  
    }
  
private ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
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
        return evento;}

    
}
    