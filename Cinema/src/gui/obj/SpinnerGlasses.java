package gui.obj;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * Selettore numerico utlizzizato nel caso di proiezioni 3d per sciegleire il numero di occhialini 3d
 * Utilizzato in: PanelCheckout
 */

public class SpinnerGlasses extends JSpinner {

   private String name;
    private double price;

    public SpinnerGlasses(String name, double price, SpinnerModel model) {
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
