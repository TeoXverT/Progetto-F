package gui.obj;

import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import core.obj.Projection;

/**
 * Bottone del carrello viene utlilizzato in PanelProjectionSelection per effetuare al scielta tra diversi orari
 * 
 */
public class ButtonCart extends JButton{
    
    private Projection proiezione;
    
    public ButtonCart(Projection proiezione) throws IOException {
        
        this.proiezione = proiezione;
        this.setIcon(imageScaling(new ImageIcon("images/cart.png"), 30, 30));       
    }
    
    private ImageIcon imageScaling(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }
    
    
    
    
}
