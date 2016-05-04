/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Yoga
 */
public class Seat extends JLabel{
    private int x;
    private int y;
    private boolean vip;
    private boolean handicap;
    private boolean occupato;
    

    public Seat(int x, int y, ImageIcon img) {
        this.x = x;
        this.y = y;
        this.setIcon(img);
    }
    
    public Seat(int x, int y) {          // Ho creato due costruttori, altriment il ParseOBJ andava in conflitto
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
