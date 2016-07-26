package obj;

/**
 *
 * @author YatinBhutnai
 */
public class Config {

    private double vipOverprice,  glassesPrice,  disabledPrice;
    private int offsetTime, bookingValidationTime;

    public Config(double vipOverprice, double glassesPrice,  double disabledPrice, int offsetTime, int bookingValidationTime) {
        this.vipOverprice = vipOverprice;
        this.glassesPrice = glassesPrice;
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
}
