/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import oggetti.Film;

/**
 *
 * @author Yoga
 */
public class ButtonCover extends JButton {

    private Film film;

    public ButtonCover(Film film) throws IOException {
        this.film = film;
        Set_Cover(film.getLink_copertina());
//        this.setBorder(BorderFactory.createEmptyBorder());
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
    }

    private void Set_Cover(String link_copertina) throws IOException {
        this.setIcon(scalaImmagine(new ImageIcon(ImageIO.read(new URL(link_copertina))), 200, 300));
    }

    private ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }

    @Override
    public String toString() {
        return film.toString();
    }
    
}
