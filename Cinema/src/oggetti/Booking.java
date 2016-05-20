/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

/**
 *
 * @author pippo
 */
public class Booking {
    private double prezzo;
    private int id_proiezione;
    
    public Booking(int id_proiezione, double prezzo) {
        this.id_proiezione = id_proiezione;
        this.prezzo = prezzo;
    }
}
