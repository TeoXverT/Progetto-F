/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import javax.swing.JPanel;
import oggetti.Prenotazione;

/**
 *
 * @author Yoga
 */
public class PageFive extends JPanel {

    Controller_Cliente controller;
    Prenotazione prenotazione;

    public PageFive(Prenotazione prenotazione, Controller_Cliente controller) {
        this.controller = controller;
        this.prenotazione = prenotazione;
        //CREARE UN CONTO ALLA ROVESCIA DI 5 MIN  E CONTROLLARE OGNI 5 SEC SE SUL DB IL PARAMETRO BOOKING.BOOKING_STATUS E DIVENTATO UNO IN TAL CASO 
        //USCIRE DAL PROGRAMMA ALTRIMENTI ASPETTARE I 5 MIN E Visualizzare pagamento fallito e andare al page one
        //PER SIMUALRE LA RICEVUTA DI PAGAMENTO METTERE UN TASTO CHE MODIFICA IL PARAMETRO  BOOKING.BOOKING_STATUS AD 1
    }

}
