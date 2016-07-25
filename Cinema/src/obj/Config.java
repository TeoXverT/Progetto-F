package obj;

/**
 *
 * @author YatinBhutnai
 */
public class Config {

    private double vipOverprice, sconto, glassesPrice, over_price, disabledPrice;
    private int offsetTime, bookingValidationTime;

    public Config(double vipOverprice, double sconto, double glassesPrice, double over_price, double disabledPrice, int offsetTime, int bookingValidationTime) {
        this.vipOverprice = vipOverprice;
        this.sconto = sconto;
        this.glassesPrice = glassesPrice;
        this.over_price = over_price;
        this.disabledPrice = disabledPrice;
        this.offsetTime = offsetTime;
        this.bookingValidationTime = bookingValidationTime;
    }

    public double getVipOverprice() {
        return vipOverprice;
    }

    public int getOffsetTime() {
        return offsetTime;
    }

    public double getGlassesPrice() {
        return glassesPrice;
    }

    public double getDisabledPrice() {
        return disabledPrice;
    }

    public int getBookingValidationTime() {
        return bookingValidationTime;
    }

//    @Override
//    public String toString() {
//        return "Config{" + "prezzo_poltrona_vip=" + vipOverprice + ", sconto_acquisti_maggiori_3=" + sconto + '}';
//    }

    public double getSconto() {
        return sconto;
    }

    public double getOver_price() {
        return over_price;
    }

    
    
    
    
}
