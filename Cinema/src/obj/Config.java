package obj;

/**
 *
 * @author YatinBhutnai
 */
public class Config {

    private double vipOverprice,  glassesPrice,  handicapPrice;
    private int offsetTime, bookingValidationTime;

    public Config(double vipOverprice, double glassesPrice,  double handicapPrice, int offsetTime, int bookingValidationTime) {
        this.vipOverprice = vipOverprice;
        this.glassesPrice = glassesPrice;
        this.handicapPrice = handicapPrice;
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

    public double getHandicapPrice() {
        return handicapPrice;
    }

    public int getBookingValidationTime() {
        return bookingValidationTime;
    }
}
