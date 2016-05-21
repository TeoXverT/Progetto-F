package oggetti;

/**
 *
 * @author YatinBhutnai
 */
public class Config {

    private double prezzo_vip, sconto, glasses_price, over_price, disabled_price;
    private int offset_time;

    public Config(double prezzo_vip, double sconto, double glasses_price, double over_price, double disabled_price, int offset_time) {
        this.prezzo_vip = prezzo_vip;
        this.sconto = sconto;
        this.glasses_price = glasses_price;
        this.over_price = over_price;
        this.disabled_price = disabled_price;
        this.offset_time = offset_time;
    }

    public double getPrezzo_vip() {
        return prezzo_vip;
    }

    public double getSconto() {
        return sconto;
    }

    public int getOffset_time() {
        return offset_time;
    }

    public double getGlasses_price() {
        return  glasses_price;
    }

    public double getOver_price() {
        return over_price;
    }

    public double getDisabled_price() {
        return disabled_price;
    }

    @Override
    public String toString() {
        return "Config{" + "prezzo_poltrona_vip=" + prezzo_vip + ", sconto_acquisti_maggiori_3=" + sconto + '}';
    }

}
