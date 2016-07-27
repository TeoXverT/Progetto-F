package customer;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

public class Glasses extends JSpinner {

   private String name;
    private double price;

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
