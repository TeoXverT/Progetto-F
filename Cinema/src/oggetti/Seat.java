/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Yatin
 */
public class Seat extends JButton {

    public void setId_seat(int id_seat) {
        this.id_seat = id_seat;
    }

    public boolean isVip() {
        return vip;
    }

    public boolean isHandicap() {
        return handicap;
    }

    public boolean isOccupato() {
        return occupato;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }

    public void setOccupato(boolean occupato) {
        this.occupato = occupato;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    private int id_seat;
    private int x;
    private int y;

    private boolean vip;
    private boolean handicap;
    private boolean occupato;
    private boolean disable;

    public int getId() {
        return id_seat;
    }

    public Seat(int x, int y, ImageIcon img) {
        this.x = x;
        this.y = y;
        this.setIcon(img);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setSize(30, 30);
    }

    public Seat(int x, int y) {          // Ho creato due costruttori, altriment il ParseOBJ andava in conflitto
        this.x = x;
        this.y = y;
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setSize(40, 40);

    }

    public Seat(int id_seat, int x, int y) {          // Serve a Umeer
        this.id_seat = id_seat;
        this.x = x;
        this.y = y;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int giveType() {
        if (vip == true) {
            return 2;
        }
        if (handicap = true) {
            return 3;
        }
        if (disable = true) {
            return 4;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Seat{" + "x=" + x + ", y=" + y + ", vip=" + vip + ", handicap=" + handicap + ", occupato=" + occupato + ", disable=" + disable + '}';
    }

}
