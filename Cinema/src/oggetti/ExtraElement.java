/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

public class ExtraElement extends JSpinner {

    String name;
    double price;

    public ExtraElement(String name, double price, SpinnerModel model) {
        super(model);
        this.name = name;
        this.price = price;
    }

    public double getTotalPrice() {
        return Double.parseDouble(this.getValue().toString()) * price;
        
        
        
        
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}
