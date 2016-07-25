package obj;

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
