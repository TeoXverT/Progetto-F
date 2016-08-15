/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.obj;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import core.obj.Film;

/**
 * Tasto utilizzato per sciegleire il film corretto, la grafica è definita dalla copertina del film di punta
 * Utilizzato in: PanelViewFilm e PanelDayAndFilmSelection
 */
public class ButtonCover extends JButton {

    private Film film;

    public ButtonCover(Film film) throws IOException {
        this.film = film;
        setCover(film.getLinkCover());
//        this.setBorder(BorderFactory.createEmptyBorder());
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
    }

    private void setCover(String linkCover) throws IOException {
        this.setIcon(scaleImage(new ImageIcon(ImageIO.read(new URL(linkCover))), 200, 300));
    }

    private ImageIcon scaleImage(ImageIcon image, int l, int h) {
        return new ImageIcon(image.getImage().getScaledInstance(l, h, java.awt.Image.SCALE_SMOOTH));
    }

    @Override
    public String toString() {
        return film.toString();
    }

}
