/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

import java.util.ArrayList;

/**
 *
 * @author pippo
 */
public class Booking {
    private double prezzo;
    private int id_proiezione;
    private ArrayList<Seat> booked_seats;
    
    public Booking(int id_proiezione, double prezzo, ArrayList booked_seats) {
        this.id_proiezione = id_proiezione;
        this.prezzo = prezzo;
        this.booked_seats = booked_seats;
    }
}
