package obj;

/**
 *
 * @author YatinBhutnai
 */
public class Config {

    private double vipOverprice,  glassesPrice,  handicapPrice;
    private int offsetTime, bookingValidationTime;
    private String ticket_validation_ip;

    public Config(double vipOverprice, double glassesPrice,  double handicapPrice, int offsetTime, int bookingValidationTime, String ticket_validation_ip) {
        this.vipOverprice = vipOverprice;
        this.glassesPrice = glassesPrice;
        this.handicapPrice = handicapPrice;
        this.offsetTime = offsetTime;
        this.bookingValidationTime = bookingValidationTime;
        this.ticket_validation_ip = ticket_validation_ip;
    }

    public String getTicket_validation_ip() {
        return ticket_validation_ip;
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
