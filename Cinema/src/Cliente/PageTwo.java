package Cliente;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import oggetti.Film;

/**
 *
 * @author Riccardo
 */
public class PageTwo extends JPanel {
    
    private Controller_Cliente controller;
    private Film film;
    private int deltaData;
    
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
        this.add(new JLabel(" Titolo film: "+film.toString(), SwingConstants.CENTER), BorderLayout.WEST);
        this.add(new JLabel(" Numero di GIorni dopo oggi: " + deltaData, SwingConstants.CENTER), BorderLayout.EAST);

        //Il native interface deve perforza essere chiuso prima di chiudere il programma, altrimenti crasha tutto
        JButton cover = new JButton("Indietro");
        this.add(cover, BorderLayout.NORTH);
        cover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NativeInterface.close();
                goBack();
            }
        });
        
        this.add(JVideo(film.getLink_youtube()), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    private void goBack() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new PageOne(controller));
        this.revalidate();
        this.repaint();
    }
    
    private JPanel JVideo(String link) {
        System.out.println(link);
        NativeInterface.open();
        
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        JWebBrowser webBrowser = new JWebBrowser();
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(link);
        webBrowser.setMaximumSize(new Dimension(200, 200));
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                NativeInterface.close();
            }
        }));
        return (webBrowserPanel);
    }
}
