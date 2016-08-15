package core.obj;

import java.util.ArrayList;

/*
 * Classe che rappresenta il concetto di sala, ha al suo interno oltre che a vari attributi un array list di posti
 *
 */

public class Hall {

    private int idHall, rows, columns;
    private ArrayList<Seat> seats = new ArrayList<>();

    public Hall(int idHall, int rows, int columns) {
        this.idHall = idHall;
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public Hall(int rows, int columns, ArrayList<Seat> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public Hall(int id_hall) {
        this.idHall = id_hall;
    }

    public int getIdHall() {
        return idHall;
    }

    public void setIdHall(int idHall) {
        this.idHall = idHall; 
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
        return idHall + "";
    }
}
