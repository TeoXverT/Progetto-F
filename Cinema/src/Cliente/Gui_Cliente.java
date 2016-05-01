/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import input_output.Adapter_SQL;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import oggetti.Film;

/**
 *
 * @author Yoga
 */
public class Gui_Cliente extends JFrame{
    
    private final Controller_Cliente cliente;
    private JPanel pannello;
    private JPanel pannello1, pannello2, pannello3, pannello4, pannello5, pannello6, pannello7;
    Adapter_SQL adapter  = new Adapter_SQL();
     //viene usato nel thread  
    
    ArrayList<Film> listaFilmFiltratiGiornalmente; //qui dichiaro l'arrayList che conterrà la futura lista filtrata
    JPanel visualizzazioneGiornaliera;
    
    
    Calendar inizioPrimoGiorno;
    Calendar temporaneo;
    Calendar finePrimoGiorno;
    
    public Gui_Cliente() {
        
        cliente = new Controller_Cliente();
        
        pannello = new JPanel();
        
        creaGui();
        
        
        //VARIABILI FINALI PER IL  THREAD
    
        inizioPrimoGiorno = Calendar.getInstance();
        temporaneo = Calendar.getInstance();
        temporaneo.add(Calendar.DAY_OF_MONTH,1);
        finePrimoGiorno = temporaneo;
        
    }
    
    
    
    public void creaGui() {
        
       
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Pannello Cliente");
        this.setBounds(100, 100, 880, 700);
//        this.setResizable(false);

        ImageIcon icona = new ImageIcon("immagini/logo_trasparente.png");
        setIconImage(icona.getImage());

        
        JTabbedPane tab = new JTabbedPane();
        
        
        this.add(tab);
        /*_______________________________________
        |                                        |
        |ISTANZIAZIONE PANNELLI GIORNI SETTIMANA |
        |________________________________________|
        */
        pannello1 = new JPanel();
        pannello1.setName("pannello1");
        pannello2  = new JPanel(new GridLayout( 100, 3));
        pannello2.setName("pannello2");
        pannello3 = new JPanel(new GridLayout( 100, 3));
        pannello3.setName("pannello3");
        pannello4  = new JPanel(new GridLayout( 100, 3));
        pannello4.setName("pannello4");
        pannello5 = new JPanel(new GridLayout( 100, 3));
        pannello5.setName("pannello5");
        pannello6  = new JPanel(new GridLayout( 100, 3));
        pannello6.setName("pannello6");
        pannello7 = new JPanel(new GridLayout( 100, 3));
        pannello7.setName("pannello7");
        
        /*____________________________________
        |                                    |
        |AGGIUNTA AL TABBEDPANE DEI PANNELLI |
        |____________________________________|
        */

        Calendar dataAttuale = Calendar.getInstance();
          
        tab.add(pannello1, giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " "+ dataAttuale.get(Calendar.DAY_OF_MONTH) +" " + elaboraMese(dataAttuale.get(Calendar.MONTH)) );
        dataAttuale.add(Calendar.DAY_OF_MONTH , 1);
        
        tab.add(pannello2, giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " "+ dataAttuale.get(Calendar.DAY_OF_MONTH) +" " + elaboraMese(dataAttuale.get(Calendar.MONTH)) );
        dataAttuale.add(Calendar.DAY_OF_MONTH , 1);

        tab.add(pannello3,giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " "+ dataAttuale.get(Calendar.DAY_OF_MONTH) +" " + elaboraMese(dataAttuale.get(Calendar.MONTH)) );
        dataAttuale.add(Calendar.DAY_OF_MONTH , 1);
       
        tab.add(pannello4,giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " "+ dataAttuale.get(Calendar.DAY_OF_MONTH) +" " + elaboraMese(dataAttuale.get(Calendar.MONTH)) );
           dataAttuale.add(Calendar.DAY_OF_MONTH , 1);
       
        tab.add(pannello5,giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " "+ dataAttuale.get(Calendar.DAY_OF_MONTH) +" " + elaboraMese(dataAttuale.get(Calendar.MONTH)) );
        dataAttuale.add(Calendar.DAY_OF_MONTH , 1);
        
        tab.add(pannello6,giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " "+ dataAttuale.get(Calendar.DAY_OF_MONTH) +" " + elaboraMese(dataAttuale.get(Calendar.MONTH)) );
        dataAttuale.add(Calendar.DAY_OF_MONTH , 1);
       
        tab.add(pannello7,giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " "+ dataAttuale.get(Calendar.DAY_OF_MONTH) +" " + elaboraMese(dataAttuale.get(Calendar.MONTH)) );
        
        
        tab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
         
                Component c = tab.getSelectedComponent();
                System.out.println(c.getName());
                if(c.getName().equals("pannello1")) {
                    System.out.println("ciclo if funziona");
                    ThreadFilmPerGiorno().start();
                }
            }
        });
        
        
        
        
        
        
    }
    
    
    
    private String giornoDellaSettimana(int numero) {
        
        if(numero == 1) {
            
            return "Domenica";
        }
        
        if(numero == 2) {
            
            return "lunedì";
        }
        if(numero == 3) {
            
            return "martedì";
        }
        if(numero == 4) {
            
            return "mercoledì";
        }
        if(numero == 5) {
            
            return "giovedì";
        }
        if(numero == 6) {
            
            return "venerdì";
        }
        if(numero == 7) {
            
            return "sabato";
        }
        
        else{
            return "giorno non valido";
        }
        
    }
    
    
    private String elaboraMese(int mese) {
        
        if(mese==0){
            return "Gennaio";
        }
        if(mese==1){
            return "Febbraio";
        }
        if(mese==2){
            return "Marzo";
        }
        if(mese==3){
            return "Aprile";
        }
        if(mese==4){
            return "Maggio";
        }
        if(mese==5){
            return "Giugno";
        }
        if(mese==6){
            return "Luglio";
        }
        if(mese==7){
            return "Agosto";
        }
        if(mese==8){
            return "Settembre";
        }
        if(mese==9){
            return "Ottobre";
        }
        if(mese==10){
            return "Novembre";
        }
        if(mese==11){
            return "Dicembre";
        }
        else{
            return "numero inserito sbagliato";
        }
        
        
        
        
    }
    
    private ActionListener tabPrimoGiorno() {
        
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                ThreadFilmPerGiorno().start();
               
            }
        };
        return evento;
        
    }
               
    
    //IL PROBLEMA è CHE NEL MAIN QUANDO RICHIAMO LA FUNZIONE 'visualizzaFilmFiltratiRispettoOraEData' MI FUNZIONA
    //MENTRE INVECE QUI MI RESTITUISCE UN ARRAY VUOTO E NON CAPISCO PERCHè
    
    private Thread ThreadFilmPerGiorno() {
        
        Thread t  = new Thread(new Runnable () {
            
            public void run() {
                
                try{
                    System.out.println("linea 265 del thread");    //TEST
                  
                    visualizzazioneGiornaliera = new JPanel(new  GridLayout(100, 3)); //creo il pannello che andrò ad aggiungere nel JPanel lunedì
                    
                    
                    System.out.println(inizioPrimoGiorno.getTime()); //TEST
                    System.out.println(finePrimoGiorno.getTime());  //TEST
                    
                    //QUI UTILIZZO LA FUNZIONE CHE NEL MAIN FUNZIONA PASSANDOGLI LE DUE DATE CHE HO DICHIARATO ALL'INIZIO DEL FILE
                    //N.B. AL MOMENTO DEL TEST DELLA FUNZIONE NEL DB ERA PRESENTE ALMENO UN FILM CONTENUTO TRA LE DUE DATE
                    //dopo vari test a quanto pare l'arraylist mi rimane vuoto, nonostante il metodo nel main funzioni, HELP
                    listaFilmFiltratiGiornalmente = adapter.visualizzaFilmFiltratiRispettoOraEData(inizioPrimoGiorno, finePrimoGiorno);
                    
                    //CICLO CHE PRINTA E AGGIUNGE LA COPERTINA AL JPANEL
                    for(int i = 0; i < listaFilmFiltratiGiornalmente.size(); i++) {
                        System.out.println("ciclo for riga 285");
                        ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(listaFilmFiltratiGiornalmente.get(i).getLink_copertina())));
                        System.out.println(listaFilmFiltratiGiornalmente.get(i).getLink_copertina());
                        visualizzazioneGiornaliera.add(new JLabel (scalaImmagine(immagine, 400, 300)));
                        
                        //METODO PER IMPOSTAZIONE IL JPANEL (GLI PASSO IL NUMERO 1 PERCHè VOGLIO CREARE QUELLO DELLA PRIMA TAB)
                        aggiornaGuiGiornaliera(1, visualizzazioneGiornaliera);
                    }
                    
                    
                } catch(IOException ex) {
                    System.out.println("errore");
                } catch (SQLException ex) {
                    Logger.getLogger(Gui_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        }
        
        );
        
        return t;
        
    }
    
    ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }
    
    public void aggiornaGuiGiornaliera(int i, JPanel pannelloGiornaliero){
        
        /*  PRIMA TAB = 1
            SECONDA TAB = 2
            TERZA TAB = 3
            QUARTA TAB = 4
            QUINTA TAB = 5
            SESTA TAB = 6
            SETTIMA TAB = 7
        */
        if(i == 1) {
            
            pannello1.add(pannelloGiornaliero);
            pannello1.revalidate();
            pannello1.repaint();
        }
        
        if(i == 2) {
            pannello2.add(pannelloGiornaliero);
            pannello2.revalidate();
            pannello2.repaint();
        }
        
        if(i == 3) {
            
            pannello3.add(pannelloGiornaliero);
            pannello3.revalidate();
            pannello3.repaint();
        }
        
        if(i == 4) {
            
            pannello4.add(pannelloGiornaliero);
            pannello4.revalidate();
            pannello4.repaint();
        }
        
        if(i == 5) {
            
            pannello5.add(pannelloGiornaliero);
            pannello5.revalidate();
            pannello5.repaint();
            
        }
        
        if(i == 6) {
            
            pannello6.add(pannelloGiornaliero);
            pannello6.revalidate();
            pannello6.repaint();
        }
        
        if(i == 7) {
            
            pannello7.add(pannelloGiornaliero);
            pannello7.revalidate();
            pannello7.repaint();
        }
        
        
        
        
    }
    
    
}
