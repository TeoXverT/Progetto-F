package core.obj;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * Classe che rappresenta il concetto di proiezione, ha al suo interno una serie di attributi e gli oggetti film e sala
 *
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
        return "Hall:" + idProjection + " Del:" + sdf.format(data_ora.getTime());
    }

            
            
            
    public int getIdProjection() {
        return idProjection;
    }

    public void setFilm(Film film) {
        this.film = film;
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
