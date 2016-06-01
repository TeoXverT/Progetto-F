/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import oggetti.Prenotazione;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
    public PageFive(Prenotazione prenotazione, Controller_Cliente controller) {
        this.controller = controller;
        this.prenotazione = prenotazione;
        
         //CREARE UN CONTO ALLA ROVESCIA DI 5 MIN  E CONTROLLARE OGNI 5 SEC SE SUL DB IL PARAMETRO BOOKING.BOOKING_STATUS E DIVENTATO UNO IN TAL CASO 
        //USCIRE DAL PROGRAMMA ALTRIMENTI ASPETTARE I 5 MIN E Visualizzare pagamento fallito e andare al page one
        //PER SIMUALRE LA RICEVUTA DI PAGAMENTO METTERE UN TASTO CHE MODIFICA IL PARAMETRO  BOOKING.BOOKING_STATUS AD 1
        ThreadTimer(0).start();
            
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
  
  
  

