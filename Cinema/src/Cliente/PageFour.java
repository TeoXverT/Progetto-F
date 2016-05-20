
package Cliente;

import javax.swing.JPanel;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class PageFour extends JPanel {
    
    Controller_Cliente controller;
    Film film;
    Proiezione proiezione;
    Prenotazione prenotazione;

    public PageFour(Film film, Proiezione proiezione, Prenotazione prenotazione,Controller_Cliente controller) {
        this.controller = controller;
        this.film = film;
        this.proiezione = proiezione;
        this.prenotazione = prenotazione;
    }
    
    
    
    
    
}
