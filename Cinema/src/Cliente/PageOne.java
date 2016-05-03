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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import oggetti.Film;

/**
 *
 * @author Yoga
 */
public class PageOne extends JPanel {

    JPanel pannello;
    JPanel pannello1, pannello2, pannello3, pannello4, pannello5, pannello6, pannello7;
    Adapter_SQL adapter = new Adapter_SQL();
     //viene usato nel thread  

    ArrayList<Film> listaFilmFiltratiGiornalmente; //qui dichiaro l'arrayList che conterrà la futura lista filtrata
    JPanel visualizzazioneGiornaliera;

    //variabile temporanea per il settaggio del inizio e fine giorno
    Calendar temporaneo;
    Calendar temporaneo1;
    Calendar temporaneo2;
    Calendar temporaneo3;
    Calendar temporaneo4;
    Calendar temporaneo5;
    Calendar temporaneo6;
    //i nomi si commentano da soli, qui li definisco
    Calendar inizioPrimoGiorno;
    Calendar finePrimoGiorno;

    Calendar fineSecondoGiorno;

    Calendar fineTerzoGiorno;

    Calendar fineQuartoGiorno;

    Calendar fineQuintoGiorno;

    Calendar fineSestoGiorno;

    Calendar fineSettimoGiorno;

    public PageOne() {

        this.setLayout(new BorderLayout());

         //VARIABILI PER IL PRIMO GIORNO
        inizioPrimoGiorno = Calendar.getInstance();
        temporaneo = Calendar.getInstance();
        temporaneo.add(Calendar.DAY_OF_MONTH, 1);
        temporaneo.set(temporaneo.get(Calendar.YEAR), temporaneo.get(Calendar.MONTH), temporaneo.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        finePrimoGiorno = temporaneo;

        //VARIABILI PER IL SECONDO GIORNO
        temporaneo1 = Calendar.getInstance();
        temporaneo1.add(Calendar.DAY_OF_MONTH, 2);
        temporaneo1.set(temporaneo1.get(Calendar.YEAR), temporaneo1.get(Calendar.MONTH), temporaneo1.get(Calendar.DAY_OF_MONTH), 00, 00, 00);

        fineSecondoGiorno = temporaneo1;

        //VARIABILI PER IL TERZO GIORNO
        temporaneo2 = Calendar.getInstance();
        temporaneo2.add(Calendar.DAY_OF_MONTH, 3);
        temporaneo2.set(temporaneo2.get(Calendar.YEAR), temporaneo2.get(Calendar.MONTH), temporaneo2.get(Calendar.DAY_OF_MONTH), 00, 00, 00);

        fineTerzoGiorno = temporaneo2;

        //VARIABILI PER IL QUARTO GIORNO
        temporaneo3 = Calendar.getInstance();
        temporaneo3.set(temporaneo3.get(Calendar.YEAR), temporaneo3.get(Calendar.MONTH), temporaneo3.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        temporaneo3.add(Calendar.DAY_OF_MONTH, 4);
        fineQuartoGiorno = temporaneo3;

        //VARIABILI PER IL QUINTO GIORNO
        temporaneo4 = Calendar.getInstance();
        temporaneo4.set(temporaneo4.get(Calendar.YEAR), temporaneo4.get(Calendar.MONTH), temporaneo4.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        temporaneo4.add(Calendar.DAY_OF_MONTH, 5);
        fineQuintoGiorno = temporaneo4;

        //VARIABILI PER SESTO GIORNO
        temporaneo5 = Calendar.getInstance();
        temporaneo5.set(temporaneo5.get(Calendar.YEAR), temporaneo5.get(Calendar.MONTH), temporaneo5.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        temporaneo5.add(Calendar.DAY_OF_MONTH, 6);
        fineSestoGiorno = temporaneo5;

        //VARIABILI PER SETTIMO GIORNO
        temporaneo6 = Calendar.getInstance();
        temporaneo6.set(temporaneo6.get(Calendar.YEAR), temporaneo6.get(Calendar.MONTH), temporaneo6.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        temporaneo6.add(Calendar.DAY_OF_MONTH, 7);
        fineSettimoGiorno = temporaneo6;

        JTabbedPane tab = new JTabbedPane();

        this.add(tab, BorderLayout.CENTER);
        /*_______________________________________
         |                                        |
         |ISTANZIAZIONE PANNELLI GIORNI SETTIMANA |
         |________________________________________|
         */
        pannello1 = new JPanel();
        pannello1.setName("pannello1");
        pannello2 = new JPanel();
        pannello2.setName("pannello2");
        pannello3 = new JPanel();
        pannello3.setName("pannello3");
        pannello4 = new JPanel();
        pannello4.setName("pannello4");
        pannello5 = new JPanel();
        pannello5.setName("pannello5");
        pannello6 = new JPanel();
        pannello6.setName("pannello6");
        pannello7 = new JPanel();
        pannello7.setName("pannello7");

        /*____________________________________
         |                                    |
         |AGGIUNTA AL TABBEDPANE DEI PANNELLI |
         |____________________________________|
         */
        Calendar dataAttuale = Calendar.getInstance();

        tab.add(pannello1, "Oggi");
        dataAttuale.add(Calendar.DAY_OF_MONTH, 1);

        tab.add(pannello2, giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " " + dataAttuale.get(Calendar.DAY_OF_MONTH) + " " + elaboraMese(dataAttuale.get(Calendar.MONTH)));
        dataAttuale.add(Calendar.DAY_OF_MONTH, 1);

        tab.add(pannello3, giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " " + dataAttuale.get(Calendar.DAY_OF_MONTH) + " " + elaboraMese(dataAttuale.get(Calendar.MONTH)));
        dataAttuale.add(Calendar.DAY_OF_MONTH, 1);

        tab.add(pannello4, giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " " + dataAttuale.get(Calendar.DAY_OF_MONTH) + " " + elaboraMese(dataAttuale.get(Calendar.MONTH)));
        dataAttuale.add(Calendar.DAY_OF_MONTH, 1);

        tab.add(pannello5, giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " " + dataAttuale.get(Calendar.DAY_OF_MONTH) + " " + elaboraMese(dataAttuale.get(Calendar.MONTH)));
        dataAttuale.add(Calendar.DAY_OF_MONTH, 1);

        tab.add(pannello6, giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " " + dataAttuale.get(Calendar.DAY_OF_MONTH) + " " + elaboraMese(dataAttuale.get(Calendar.MONTH)));
        dataAttuale.add(Calendar.DAY_OF_MONTH, 1);

        tab.add(pannello7, giornoDellaSettimana(dataAttuale.get(Calendar.DAY_OF_WEEK)) + " " + dataAttuale.get(Calendar.DAY_OF_MONTH) + " " + elaboraMese(dataAttuale.get(Calendar.MONTH)));

//        tab.add(pannello7,"helo");
        /*_________________________________________________________
         |                                                         |
         |QUI CREO IL CHANGELISTENER PER GESTIRE IL CAMBIO DEI TAB |
         |NEL JTABBEDPANE E FAR PARTIRE I RELATIVI THREAD          |
         |_________________________________________________________|     */
        tab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {

                Component c = tab.getSelectedComponent();
                System.out.println(tab.getSelectedIndex());
                System.out.println(c.getName());
                if (c.getName().equals("pannello1")) {
                    ThreadFilmPrimoTab().start();
                }
                if (c.getName().equals("pannello2")) {
                    ThreadFilmSecondoTab().start();
                }
                if (c.getName().equals("pannello3")) {
                    ThreadFilmTerzoTab().start();
                }
                if (c.getName().equals("pannello4")) {
                    ThreadFilmQuartoTab().start();
                }
                if (c.getName().equals("pannello5")) {
                    ThreadFilmQuintoTab().start();
                }
                if (c.getName().equals("pannello6")) {
                    ThreadFilmSestoTab().start();
                }
                if (c.getName().equals("pannello7")) {
                    ThreadFilmSettimoTab().start();
                }
            }
        });

        ThreadFilmPrimoTab().start();

    }

    //METODO PER CAMBIARE IL METODO DI IDENTIFICAZIONE DEI GIORNI DA NUMERO(INT) A NOME(STRING)
    private String giornoDellaSettimana(int numero) {

        if (numero == 1) {

            return "Domenica";
        }

        if (numero == 2) {

            return "lunedì";
        }
        if (numero == 3) {

            return "martedì";
        }
        if (numero == 4) {

            return "mercoledì";
        }
        if (numero == 5) {

            return "giovedì";
        }
        if (numero == 6) {

            return "venerdì";
        }
        if (numero == 7) {

            return "sabato";
        } else {
            return "giorno non valido";
        }

    }

    //METODO PER CAMBIARE IL METODO DI IDENTIFICAZIONE DEI MESI DA NUMERO(INT) A NOME(STRING)
    private String elaboraMese(int mese) {

        if (mese == 0) {
            return "Gennaio";
        }
        if (mese == 1) {
            return "Febbraio";
        }
        if (mese == 2) {
            return "Marzo";
        }
        if (mese == 3) {
            return "Aprile";
        }
        if (mese == 4) {
            return "Maggio";
        }
        if (mese == 5) {
            return "Giugno";
        }
        if (mese == 6) {
            return "Luglio";
        }
        if (mese == 7) {
            return "Agosto";
        }
        if (mese == 8) {
            return "Settembre";
        }
        if (mese == 9) {
            return "Ottobre";
        }
        if (mese == 10) {
            return "Novembre";
        }
        if (mese == 11) {
            return "Dicembre";
        } else {
            return "numero inserito sbagliato";
        }

    }

    /*_______________________________________           
     |                                       |
     |QUI CREO I THREAD PER OGNI SCHEDA      |
     |_______________________________________|
     */
    private Thread ThreadFilmPrimoTab() {

        Thread t = new Thread(new Runnable() {

            public void run() {

                try {

                    visualizzazioneGiornaliera = new JPanel(new GridLayout(0, 3));

                    listaFilmFiltratiGiornalmente = adapter.visualizzaFilmFiltratiRispettoOraEData(inizioPrimoGiorno, finePrimoGiorno);
                    System.out.println("inizio giorno: " + inizioPrimoGiorno.getTime() + "\nsecondo giorno: " + finePrimoGiorno.getTime());

                    //CICLO CHE PRINTA E AGGIUNGE LA COPERTINA AL JPANEL
                    for (int i = 0; i < listaFilmFiltratiGiornalmente.size(); i++) {
                        System.out.println("In Download immagine URL: " + listaFilmFiltratiGiornalmente.get(i).getLink_copertina());
                        ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(listaFilmFiltratiGiornalmente.get(i).getLink_copertina())));

                        visualizzazioneGiornaliera.add(new JLabel(scalaImmagine(immagine, 200, 300)));

                        //METODO PER IMPOSTAZIONE IL JPANEL (GLI PASSO IL NUMERO 1 PERCHè VOGLIO CREARE QUELLO DELLA PRIMA TAB)
                        aggiornaGuiGiornaliera(1, visualizzazioneGiornaliera);
                        System.out.println("finito caricamento immagini");
                    }

                } catch (IOException ex) {
                    System.out.println("errore nello scaricamento immagini");
                } catch (SQLException ex) {
                    Logger.getLogger(Gui_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

        return t;

    }

    private Thread ThreadFilmSecondoTab() {

        Thread t = new Thread(new Runnable() {

            public void run() {

                try {

                    visualizzazioneGiornaliera = new JPanel(new GridLayout(0, 3));

                    listaFilmFiltratiGiornalmente = adapter.visualizzaFilmFiltratiRispettoOraEData(finePrimoGiorno, fineSecondoGiorno);
                    System.out.println("inizio giorno: " + finePrimoGiorno.getTime() + "\nfine giorno: " + fineSecondoGiorno.getTime());
                    //CICLO CHE PRINTA E AGGIUNGE LA COPERTINA AL JPANEL
                    for (int i = 0; i < listaFilmFiltratiGiornalmente.size(); i++) {
                        System.out.println("In Download immagine URL: " + listaFilmFiltratiGiornalmente.get(i).getLink_copertina());
                        ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(listaFilmFiltratiGiornalmente.get(i).getLink_copertina())));
                        visualizzazioneGiornaliera.add(new JLabel(scalaImmagine(immagine, 200, 300)));

                        //METODO PER IMPOSTAZIONE IL JPANEL (GLI PASSO IL NUMERO 1 PERCHè VOGLIO CREARE QUELLO DELLA PRIMA TAB)
                        aggiornaGuiGiornaliera(2, visualizzazioneGiornaliera);
                        System.out.println("finito caricamento immagini");
                    }

                } catch (IOException ex) {
                    System.out.println("errore nello scaricamento immagini");
                } catch (SQLException ex) {
                    Logger.getLogger(Gui_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

        return t;

    }

    private Thread ThreadFilmTerzoTab() {

        Thread t = new Thread(new Runnable() {

            public void run() {

                try {

                    visualizzazioneGiornaliera = new JPanel(new GridLayout(0, 3));
                    listaFilmFiltratiGiornalmente = adapter.visualizzaFilmFiltratiRispettoOraEData(fineSecondoGiorno, fineTerzoGiorno);
                    System.out.println("inizio giorno: " + fineSecondoGiorno.getTime() + "\nfine giorno: " + fineTerzoGiorno.getTime());

                    //CICLO CHE PRINTA E AGGIUNGE LA COPERTINA AL JPANEL
                    for (int i = 0; i < listaFilmFiltratiGiornalmente.size(); i++) {
                        System.out.println("In Download immagine URL: " + listaFilmFiltratiGiornalmente.get(i).getLink_copertina());
                        ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(listaFilmFiltratiGiornalmente.get(i).getLink_copertina())));
                        visualizzazioneGiornaliera.add(new JLabel(scalaImmagine(immagine, 200, 300)));

                        //METODO PER IMPOSTAZIONE IL JPANEL (GLI PASSO IL NUMERO 1 PERCHè VOGLIO CREARE QUELLO DELLA PRIMA TAB)
                        aggiornaGuiGiornaliera(3, visualizzazioneGiornaliera);
                        System.out.println("finito caricamento immagini");
                    }

                } catch (IOException ex) {
                    System.out.println("errore nello scaricamento immagini");
                } catch (SQLException ex) {
                    Logger.getLogger(Gui_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

        return t;

    }

    private Thread ThreadFilmQuartoTab() {

        Thread t = new Thread(new Runnable() {

            public void run() {

                try {

                    visualizzazioneGiornaliera = new JPanel(new GridLayout(0, 3));

                    listaFilmFiltratiGiornalmente = adapter.visualizzaFilmFiltratiRispettoOraEData(fineTerzoGiorno, fineQuartoGiorno);
                    System.out.println("inizio giorno: " + fineTerzoGiorno.getTime() + "\nfine giorno: " + fineQuartoGiorno.getTime());
                    //CICLO CHE PRINTA E AGGIUNGE LA COPERTINA AL JPANEL
                    for (int i = 0; i < listaFilmFiltratiGiornalmente.size(); i++) {
                        System.out.println("In Download immagine URL: " + listaFilmFiltratiGiornalmente.get(i).getLink_copertina());
                        ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(listaFilmFiltratiGiornalmente.get(i).getLink_copertina())));
                        visualizzazioneGiornaliera.add(new JLabel(scalaImmagine(immagine, 200, 300)));

                        //METODO PER IMPOSTAZIONE IL JPANEL (GLI PASSO IL NUMERO 1 PERCHè VOGLIO CREARE QUELLO DELLA PRIMA TAB)
                        aggiornaGuiGiornaliera(4, visualizzazioneGiornaliera);
                        System.out.println("finito caricamento immagini");
                    }

                } catch (IOException ex) {
                    System.out.println("errore nello scaricamento immagini");
                } catch (SQLException ex) {
                    Logger.getLogger(Gui_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

        return t;

    }

    private Thread ThreadFilmQuintoTab() {

        Thread t = new Thread(new Runnable() {

            public void run() {

                try {

                    visualizzazioneGiornaliera = new JPanel(new GridLayout(0, 3));

                    listaFilmFiltratiGiornalmente = adapter.visualizzaFilmFiltratiRispettoOraEData(fineQuartoGiorno, fineQuintoGiorno);
                    System.out.println("inizio giorno: " + fineQuartoGiorno.getTime() + "\nfine giorno: " + fineQuintoGiorno.getTime());
                    //CICLO CHE PRINTA E AGGIUNGE LA COPERTINA AL JPANEL
                    for (int i = 0; i < listaFilmFiltratiGiornalmente.size(); i++) {
                        System.out.println("In Download immagine URL: " + listaFilmFiltratiGiornalmente.get(i).getLink_copertina());
                        ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(listaFilmFiltratiGiornalmente.get(i).getLink_copertina())));
                        visualizzazioneGiornaliera.add(new JLabel(scalaImmagine(immagine, 200, 300)));

                        //METODO PER IMPOSTAZIONE IL JPANEL (GLI PASSO IL NUMERO 1 PERCHè VOGLIO CREARE QUELLO DELLA PRIMA TAB)
                        aggiornaGuiGiornaliera(5, visualizzazioneGiornaliera);
                        System.out.println("finito caricamento immagini");
                    }

                } catch (IOException ex) {
                    System.out.println("errore nello scaricamento immagini");
                } catch (SQLException ex) {
                    Logger.getLogger(Gui_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

        return t;

    }

    private Thread ThreadFilmSestoTab() {

        Thread t = new Thread(new Runnable() {

            public void run() {

                try {

                    visualizzazioneGiornaliera = new JPanel(new GridLayout(0, 3));

                    listaFilmFiltratiGiornalmente = adapter.visualizzaFilmFiltratiRispettoOraEData(fineQuintoGiorno, fineSestoGiorno);
                    System.out.println("inizio giorno: " + fineQuintoGiorno.getTime() + "\nfine giorno: " + fineSestoGiorno.getTime());
                    //CICLO CHE PRINTA E AGGIUNGE LA COPERTINA AL JPANEL
                    for (int i = 0; i < listaFilmFiltratiGiornalmente.size(); i++) {
                        System.out.println("In Download immagine URL: " + listaFilmFiltratiGiornalmente.get(i).getLink_copertina());
                        ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(listaFilmFiltratiGiornalmente.get(i).getLink_copertina())));
                        visualizzazioneGiornaliera.add(new JLabel(scalaImmagine(immagine, 200, 300)));

                        //METODO PER IMPOSTAZIONE IL JPANEL (GLI PASSO IL NUMERO 1 PERCHè VOGLIO CREARE QUELLO DELLA PRIMA TAB)
                        aggiornaGuiGiornaliera(6, visualizzazioneGiornaliera);
                        System.out.println("finito caricamento immagini");
                    }

                } catch (IOException ex) {
                    System.out.println("errore nello scaricamento immagini");
                } catch (SQLException ex) {
                    Logger.getLogger(Gui_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

        return t;

    }

    private Thread ThreadFilmSettimoTab() {

        Thread t = new Thread(new Runnable() {

            public void run() {

                try {

                    visualizzazioneGiornaliera = new JPanel(new GridLayout(0, 3));
                    listaFilmFiltratiGiornalmente = adapter.visualizzaFilmFiltratiRispettoOraEData(fineSestoGiorno, fineSettimoGiorno);
                    System.out.println("inizio giorno: " + fineSestoGiorno.getTime() + "\nfine giorno: " + fineSettimoGiorno.getTime());
                    //CICLO CHE PRINTA E AGGIUNGE LA COPERTINA AL JPANEL
                    for (int i = 0; i < listaFilmFiltratiGiornalmente.size(); i++) {
                        System.out.println("In Download immagine URL: " + listaFilmFiltratiGiornalmente.get(i).getLink_copertina());
                        ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(listaFilmFiltratiGiornalmente.get(i).getLink_copertina())));
                        visualizzazioneGiornaliera.add(new JLabel(scalaImmagine(immagine, 200, 300)));

                        //METODO PER IMPOSTAZIONE IL JPANEL (GLI PASSO IL NUMERO 1 PERCHè VOGLIO CREARE QUELLO DELLA PRIMA TAB)
                        aggiornaGuiGiornaliera(7, visualizzazioneGiornaliera);
                        System.out.println("finito caricamento immagini");
                    }

                } catch (IOException ex) {
                    System.out.println("errore nello scaricamento immagini");
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

    public void aggiornaGuiGiornaliera(int i, JPanel pannelloGiornaliero) {

        /*  PRIMA TAB = 1
         SECONDA TAB = 2
         TERZA TAB = 3
         QUARTA TAB = 4
         QUINTA TAB = 5
         SESTA TAB = 6
         SETTIMA TAB = 7
         */
        if (i == 1) {
            pannello1.removeAll();
            pannello1.add(pannelloGiornaliero);
            pannello1.revalidate();
            pannello1.repaint();
        }

        if (i == 2) {
            pannello2.removeAll();
            pannello2.add(pannelloGiornaliero);
            pannello2.revalidate();
            pannello2.repaint();
        }

        if (i == 3) {
            pannello3.removeAll();
            pannello3.add(pannelloGiornaliero);
            pannello3.revalidate();
            pannello3.repaint();
        }

        if (i == 4) {
            pannello4.removeAll();
            pannello4.add(pannelloGiornaliero);
            pannello4.revalidate();
            pannello4.repaint();
        }

        if (i == 5) {
            pannello5.removeAll();
            pannello5.add(pannelloGiornaliero);
            pannello5.revalidate();
            pannello5.repaint();

        }

        if (i == 6) {
            pannello6.removeAll();
            pannello6.add(pannelloGiornaliero);
            pannello6.revalidate();
            pannello6.repaint();
        }

        if (i == 7) {
            pannello7.removeAll();
            pannello7.add(pannelloGiornaliero);
            pannello7.revalidate();
            pannello7.repaint();
        }

    }

}
