/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import chrriis.dj.nativeswing.swtimpl.components.JFlashPlayer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JPanel;
import oggetti.Prenotazione;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
/**
 *
 * @author Yoga
 */
public class PageFive extends JPanel {

    Controller_Cliente controller;
    Prenotazione prenotazione;
    JLabel label1=new JLabel("pagamento effettuato");
    JLabel label2=new JLabel("sessione scaduta");
    
    Calendar hourNow = Calendar.getInstance();
    Calendar hourPlus30 = Calendar.getInstance();
    
    String endTimeString ="prova";
    JLabel endTimeLabel;
    
    JPanel pannelloContenitore;
    
    public PageFive(Prenotazione prenotazione, Controller_Cliente controller) {
        this.controller = controller;
        this.prenotazione = prenotazione;
        
        hourPlus30.add(Calendar.MINUTE, 30);
        System.out.println(hourPlus30.toString() + "\n");
        //pannello più esterno
        pannelloContenitore = new JPanel(new GridLayout(3,1));   
        this.add(pannelloContenitore);
        
        

        endTimeLabel = new JLabel();
        
        pannelloContenitore.add(endTimeLabel);

        //in questo jlabel verrà scritto se la prenotazione è andata a buon fine
        JLabel displaySuccessoPrenotazione = new JLabel();
        pannelloContenitore.add(displaySuccessoPrenotazione);
        
        
        JButton confirmButton = new JButton("Conferma Prenotazione");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
            }
        });
        
        
         //CREARE UN CONTO ALLA ROVESCIA DI 5 MIN  E CONTROLLARE OGNI 5 SEC SE SUL DB IL PARAMETRO BOOKING.BOOKING_STATUS E DIVENTATO UNO IN TAL CASO 
        //USCIRE DAL PROGRAMMA ALTRIMENTI ASPETTARE I 5 MIN E Visualizzare pagamento fallito e andare al page one
        //PER SIMUALRE LA RICEVUTA DI PAGAMENTO METTERE UN TASTO CHE MODIFICA IL PARAMETRO  BOOKING.BOOKING_STATUS AD 1
        ThreadTimer(0).start();
        
        ThreadCountdown().start();

        
        
          }
   
    private Thread ThreadCountdown() {
        Thread t = null;
        
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                
      /*          try {
                    for(int i  = 0; i < 30; i++) {
                hourPlus30.add(Calendar.SECOND, -1);
                endTimeString = hourPlus30.get(Calendar.HOUR_OF_DAY) + ":" + hourPlus30.get(Calendar.MINUTE) + ":" + hourPlus30.get(Calendar.SECOND) ;
                endTimeLabel.setText(endTimeString);
                pannelloContenitore.revalidate();
                System.out.println("revalidate end time label");
                pannelloContenitore.repaint();
                System.out.println("repaint end time label");
                Thread.sleep(1000);
                }
                    
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(PageFive.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                    */
      
                    int minutes = 29;
                    int seconds = 59;
                    for(int i  = 0; i < 30; i++) {
                        
                        endTimeLabel.setText("<html><h1><b>" + String.valueOf(minutes+1) + " minuti e 00 secondi rimasti.<br>Allo scadere del tempo la prenotazione non sarà più valida<b></h1></html>");
                        endTimeLabel.revalidate();
                        endTimeLabel.repaint();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageFive.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        for(int e = 0; e < 59; e++) {
                            
                            endTimeLabel.setText("<html><h1><b>" + String.valueOf(minutes) + " minuti e "+ seconds+" secondi rimasti.<br>Allo scadere del tempo la prenotazione non sarà più valida<b></h1></html>");
                            seconds --;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(PageFive.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        seconds = 59;
                        
                        minutes --;
                    }
                    
                    endTimeLabel.setText("<html><b>ATTENZIONE TEMPO SCADUTO</b></html>");
            }
        });
        

        
        return t;
    }
    
    
    
    
    private Thread ThreadTimer(int par){
        Thread t = null;
       
        t=new Thread(new Runnable(){
            public void run(){
                int checkPayment = 0;
            for(int i=0;i<60;i++){
                try {
                    Thread.sleep(5000);
                checkPayment = controller.checkPayment();
               if(checkPayment==1){i=60;}
               
                } catch (InterruptedException ex) {
                    Logger.getLogger(PageFive.class.getName()).log(Level.SEVERE, null, ex);
                }
            
           }
            if(checkPayment==1){System.out.println("pagamento effettuato");}else{System.out.println("sessione scaduta");}
            
            }
            } );
        return t;
}
      
  }
  
  
  

