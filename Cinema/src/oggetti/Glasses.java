/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

public class Glasses extends JSpinner {

    String name;
    double price;

    public Glasses(String name, double price, SpinnerModel model) {
        super(model);
        this.name = name;
        this.price = price;
    }

    public double getTotalPrice() {
        return Integer.parseInt(this.getValue().toString()) * price;
    }

    public int getNumber_of_glasses() {
        return Integer.parseInt(this.getValue().toString());
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}
