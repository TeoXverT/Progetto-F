/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

/**
 *
 * @author Yoga
 */
public class Config {
    
    private double prezzo_poltrona_vip, sconto_acquisti_maggiori_3;

    public Config(double prezzo_poltrona_vip, double sconto_acquisti_maggiori_3) {
        this.prezzo_poltrona_vip = prezzo_poltrona_vip;
        this.sconto_acquisti_maggiori_3 = sconto_acquisti_maggiori_3;
    }

    @Override
    public String toString() {
        return "Config{" + "prezzo_poltrona_vip=" + prezzo_poltrona_vip + ", sconto_acquisti_maggiori_3=" + sconto_acquisti_maggiori_3 + '}';
    }
    
    
}
