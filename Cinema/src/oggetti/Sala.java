package oggetti;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cri
 */
public class Sala {
    
    private int id_sala, posti_x, posti_y;    
    private int posti_vip[][];
    
    //X E Y CORRISPONDONO AL MASSIMO NUMERO DI RIGHE E COLONNE CHE CI SONO IN UNA SALA
    //il posto più alto a sinistra del proiettore è la riga zero e colonna zero

    public Sala(int id_sala, int posti_x, int posti_y, int[][] posti_vip) {
        this.id_sala = id_sala;
        this.posti_x = posti_x;
        this.posti_y = posti_y;
        this.posti_vip = posti_vip;
    }

    @Override
    public String toString() {
        return "Sala{" + "id_sala=" + id_sala + ", posti_x=" + posti_x + ", posti_y=" + posti_y + ", posti_vip=" + posti_vip + '}';
    }
    
    
}
