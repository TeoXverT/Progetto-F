package Cliente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import oggetti.ButtonCart;
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
    
    public PageTwo(Film film, int deltaData, Controller_Cliente controller, int deltaTime) throws SQLException, IOException {
        //Il controller ti permettera di parlare con il database
        //deltaData è l'offset in giorni rispetto ad oggi, ad es: se oggi è lunedi ed il tab da dove viene selezionato il film è martedi allora questo valore vale 1
        //deltaData è temporaneo in attesa dello slider  laterale di selezione del orario        
        //film è proprio il film che viene clickato con tutti i dati del caso, guarda i suoi metodi
        //Tè dovrai eseguire una query per scoprire in quali orari viene proiettatto questo film il giorno oggi+deltaData 
        this.controller = controller;
        this.film = film;
        this.deltaData = deltaData;
        this.deltaTime = deltaTime;
        this.setLayout(new GridLayout(2,2, 20, 20));
        crea_gui();
        
    }

    private void crea_gui() throws SQLException, MalformedURLException, IOException {
        JButton cover = new JButton("Indietro");
        
        JPanel pannelloCopertina=new JPanel();
         this.add(pannelloCopertina);
        Image image = null;
        try {
    URL url = new URL(film.getLink_copertina());
    image = ImageIO.read(url);
} catch (IOException e) {
}
   ImageIcon ii=new ImageIcon(image);
   JLabel label1=new JLabel(scalaImmagine(ii,250,350));
   pannelloCopertina.add(label1);
  // this.add(cover, BorderLayout.SOUTH); DA AGGIUNGERE SUCCESSIVAMENTE AL PANNELLO CON GLI ORARI
    cover.addActionListener(goBackEvent());  
    
    JPanel pannelloTrama = new JPanel(new GridLayout(2, 1, 10, 10));
    this.add(pannelloTrama);
    pannelloTrama.add(new JLabel(" Titolo film: " + film.toString()));
    
        JTextArea Trama=new JTextArea(film.getDescrizione());
        Trama.setLineWrap(true);
        Trama.setEditable(false);
        
    pannelloTrama.add(Trama);    
    
    this.add(new PanelYoutube(film.getLink_youtube(), 200, 200));
    
    proiezione = controller.showByFilm(film.getId_film(), deltaData, deltaTime);
    SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
   
    //ABBIAMO MODIFICAGTO QUI PRIMA ERA COSI new BorderLayout() DENTRO AL NEW JPANEL()
    JPanel pannelloContenitoreBackOrari = new JPanel();
    this.add(pannelloContenitoreBackOrari);
    JPanel pannelloOrari  = new JPanel(new GridLayout(proiezione.size(), 2, 10, 10));
    pannelloContenitoreBackOrari.add(pannelloOrari, BorderLayout.CENTER);
       // URL urlCarrelloBello= new URL("http://www.ergonotec.it/images/carrello.gif");
       // Image immagineCarrelloBello = ImageIO.read(urlCarrelloBello);
      //  immagineCarrelloBello = immagineCarrelloBello.getScaledInstance(100, 100, 2 );
      //  ImageIcon iconaCarrelloBello  =  new ImageIcon(immagineCarrelloBello);
     
        ButtonCart bottoneCarrello;
    for(int i = 0; i < proiezione.size(); i++) {
        
        pannelloOrari.add(new JLabel(sdfDate.format(proiezione.get(i).getData_ora().getTime()) + "     " + proiezione.get(i).getTipo_proiezione() + "     " + proiezione.get(i).getId_sala() ), BorderLayout.SOUTH);
        bottoneCarrello = new ButtonCart(proiezione.get(i));
        bottoneCarrello.setPreferredSize(new Dimension(100,100));
        pannelloOrari.add(bottoneCarrello);
       
    }
    
    
    pannelloContenitoreBackOrari.add(cover);
   
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
    