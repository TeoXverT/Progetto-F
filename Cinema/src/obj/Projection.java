package obj;

import admin.AdminController;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yoga
 */
public class Projection {

    private int idProjection;
    private Calendar data_ora;
    private Film film;
    private Hall hall;
    private int projectionType;// 0- Normale 1- 3D 2-IMAX 3D 3- Live Event
    private double price;

    public Projection(int id_projection, Calendar data_ora, Film film, Hall hall, int projectionType, double price) {
        this.idProjection = id_projection;
        this.data_ora = data_ora;
        this.film = film;
        this.hall = hall;
        this.projectionType = projectionType;
        this.price = price; 
    }

    public Projection(int idProjection) {
        this.idProjection = idProjection;
    }

    @Override
    public String toString() {
        return "Hall:" + idProjection + " Del:" + getData_ora_friendly();
    }

    public int getIdProjection() {
        return idProjection;
    }

    public String getData_ora_sql() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(data_ora.getTime());
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getData_ora_friendly() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
        return sdf.format(data_ora.getTime());
    }

    public String getData_ora_friendly_2() {
        SimpleDateFormat giorno = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat ora = new SimpleDateFormat("HH:mm");
        return "Giorno: " + giorno.format(data_ora.getTime()) + " Alle Ore: " + ora.format(data_ora.getTime());
    }

    public Calendar getData_ora() {
        return data_ora;
    }

    public int getpProjectionType() {
        return projectionType;
    }

    public boolean isTypeNormal() {
        return projectionType == 0;
    }

    public boolean isType3D() {
        return projectionType == 1;
    }

    public boolean isTypeIMAX3D() {
        return projectionType == 2;
    }

    public boolean isTypeLiveEvent() {
        return projectionType == 3;
    }

    public String getType_String() {
        switch (projectionType) {
            case 0:
                return "Normale";
            case 1:
                return "3D";
            case 2:
                return "IMAX 3D";
            case 3:
                return "Diretta Evento";
            default:
                return "Errore";
        }
    }

    public double getPrice() {
        return price;
    }

    public Film getFilm() {
        return film;
    }

    public Hall getRoom() {
        return hall;
    }

}
