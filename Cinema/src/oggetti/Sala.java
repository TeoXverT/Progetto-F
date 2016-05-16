package oggetti;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yatin
 */
public class Sala {

    private int id_sala, rows,columns;
    private ArrayList<Seat> seats = new ArrayList<>();
    
    public Sala(int id_sala, int rows, int columns) {
        this.id_sala = id_sala;
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }
    
    public Sala(int rows, int columns, ArrayList<Seat> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public int getId_sala() {
        return id_sala;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }
    
      public String toString() {
        return id_sala + "";
    }
}
