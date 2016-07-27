/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import customer.CustomerController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.Booking;
import obj.Film;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yoga
 */
public class TesterAdmin {

    public TesterAdmin() {
    }

    @Test
    public void hello() {
        assertThat(true, is(true));
    }

    @Test
    public void eliminazioneBiglettiNonPagati() {
        CustomerController c = CustomerController.getInstance();
        try {
           
             assertThat( c.writeBooking(new Booking(1, null, null, null, 0, 0, 0, ""))==0 , is(true));
        } catch (SQLException ex) {
            Logger.getLogger(TesterAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void creazioneFilmCampoVuoto() {
        CustomerController c = CustomerController.getInstance();
        try {
           
             assertThat(c.writeFilm(new Film(null, null, null, null, null, null)) , is(false));
        } catch (SQLException ex) {
            Logger.getLogger(TesterAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void fileWriterPercorsoNullo() {
        CustomerController c = CustomerController.getInstance();
        try {
           
             assertThat(c.fileWriter(null, "..", "..", null, "") , is(false));
        } catch (SQLException ex) {
            Logger.getLogger(TesterAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Test
    public void rimozioneApici() {
        CustomerController c = CustomerController.getInstance();
        try {
           
             assertThat(c.stringCharReplacer("a'a")=="a a" , is(true));
        } catch (SQLException ex) {
            Logger.getLogger(TesterAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
