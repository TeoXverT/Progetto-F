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
    ControlPayment cp;
    public PageFive(Prenotazione prenotazione, Controller_Cliente controller,ControlPayment cp) {
        this.controller = controller;
        this.prenotazione = prenotazione;
        this.cp=cp;
        //CREARE UN CONTO ALLA ROVESCIA DI 5 MIN  E CONTROLLARE OGNI 5 SEC SE SUL DB IL PARAMETRO BOOKING.BOOKING_STATUS E DIVENTATO UNO IN TAL CASO 
        //USCIRE DAL PROGRAMMA ALTRIMENTI ASPETTARE I 5 MIN E Visualizzare pagamento fallito e andare al page one
        //PER SIMUALRE LA RICEVUTA DI PAGAMENTO METTERE UN TASTO CHE MODIFICA IL PARAMETRO  BOOKING.BOOKING_STATUS AD 1
        
        //timerStart(cp);    
       //da scommetarte per far partire timestart
    }
 
 public void timerStart(ControlPayment cp){
     this.setLayout(new BorderLayout());
     Timer timer=new Timer();
    TimerTask ControlPayment=new TimerTask() {
        @Override
        public void run() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    for(int i=0;i<60;i++){
    timer.schedule(ControlPayment, 5000);//fa partire controlpayment ogni 5 secondi
    if(cp.cp1==1){i=60;}  
    }
    if(cp.cp1==1){this.add(label1,BorderLayout.NORTH);}else{this.add(label2,BorderLayout.SOUTH);}
   } 

}