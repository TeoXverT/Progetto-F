/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import input_output.Adapter_SQL;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

/**
 *
 * @author Yoga
 */
public class Gui_Cliente extends JFrame{
    
    private final Controller_Cliente cliente;
    private JPanel pannello;
    private JPanel pannello1, pannello2, pannello3, pannello4, pannello5, pannello6, pannello7;
    Adapter_SQL adapter  = new Adapter_SQL();
    
    
    public Gui_Cliente() {
        
        cliente = new Controller_Cliente();
        
        pannello = new JPanel();
        
        creaGui();
        
        
        
        
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
        pannello1 = new JPanel(new GridLayout( 100, 3));
        pannello2  = new JPanel(new GridLayout( 100, 3));
        pannello3 = new JPanel(new GridLayout( 100, 3));
        pannello4  = new JPanel(new GridLayout( 100, 3));
        pannello5 = new JPanel(new GridLayout( 100, 3));
        pannello6  = new JPanel(new GridLayout( 100, 3));
        pannello7 = new JPanel(new GridLayout( 100, 3));
        
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
    
    
                //IN COSTRUZIONE( DON'T TOUCH THIS) 
    //MEMO: gli passo un pannello, la data inizio e la data fine. Poi richiamo la funzione in COntrollerDaticliente visualizzaFilmFiltratiRispettoOraEData e istanzio i bottoni con immagine il link della copertina
    // RICORDARSI DI USARE I THREAD
    private void scaricaListaFilmFiltrati(JPanel pannello, Calendar dataInizio, Calendar dataFine) {
        
        
        
        
    }
    
    
    
}
