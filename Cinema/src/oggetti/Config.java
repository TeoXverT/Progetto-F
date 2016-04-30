/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oggetti;

/**
 *
 * @author YatinBhutnai
 */
public class Config {

    public double getPrezzo_vip() {
        return prezzo_vip;
    }

    public double getSconto() {
        return sconto;
    }

    public double getPopcorn_s() {
        return popcorn_s;
    }

    public double getPopcorn_m() {
        return popcorn_m;
    }

    public double getPopcorn_l() {
        return popcorn_l;
    }

    public double getBibita_s() {
        return bibita_s;
    }

    public double getBibita_m() {
        return bibita_m;
    }

    public double getBibita_l() {
        return bibita_l;
    }
    
    private double prezzo_vip, sconto, popcorn_s, popcorn_m, popcorn_l, bibita_s, bibita_m, bibita_l;

    public Config(double prezzo_vip, double sconto, double popcorn_s, double popcorn_m, double popcorn_l, double bibita_s, double bibita_m, double bibita_l) {
        this.prezzo_vip = prezzo_vip;
        this.sconto = sconto;
        this.popcorn_s = popcorn_s;
        this.popcorn_m = popcorn_m;
        this.popcorn_l = popcorn_l;
        this.bibita_s = bibita_s;
        this.bibita_m = bibita_m;
        this.bibita_l = bibita_l;
    }

    @Override
    public String toString() {
        return "Config{" + "prezzo_poltrona_vip=" + prezzo_vip + ", sconto_acquisti_maggiori_3=" + sconto + '}';
    }

}
