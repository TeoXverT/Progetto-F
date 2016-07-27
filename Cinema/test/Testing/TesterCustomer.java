/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import customer.CustomerController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.Booking;
import obj.Hall;
import obj.Seat;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cris
 */
public class TesterCustomer {
    
    CustomerController c;
    
    public TesterCustomer() {
         c = CustomerController.getInstance();
    }
    
    @Test
    public void scritturaPrenotazione() {
        
        try {
            //testo scrittura prenotazione vuota se ritorna 0
            assertThat( c.writeBooking(new Booking(1, null, null, null, 0, 0, 0, ""))==0 , is(true));
        } catch (SQLException ex) {
            Logger.getLogger(TesterAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @Test
    public void filmFuturiGiornoEOra() throws SQLException {
        //testo se dato un giorno molto lontano in cui non ci sono proiezioni effettivamente ritorna un array vuoto
        assertThat(c.futureFilmBySlider(125, 12).isEmpty(), is(true));
        
        
    }
    
    
    
    @Test
    public void getTakenSeats() throws SQLException {
        //testo se dato un id Sala ci sono dei posti prenotati: in questo caso voglio che sia vuoto fornendo un id inesistente
        assertThat(c.getTakenSeats(155).isEmpty(), is(true));
    }
    
    
    
    @Test
    public void projectionFilteredByFilmAndTime() throws SQLException {
        //testo se le proiezioni filtrate tramite un id Film inesistente restituisce un array vuoto
        assertThat(c.projectionFilteredByFilmAndTime(154, Calendar.getInstance()).isEmpty(), is(true));
        
    }
    
    
    @Test
    public void configIsNotNULL() throws SQLException {
        //testo se Config non è NULL 
        assertThat(c.getConfig() == null, is(false));
    }
    
    
    @Test
    public void getSeatsByIdHall() {
        //testo che l'array di posti di una sala inesistente sia effettivamente vuoto
        assertThat(c.getSeatsByIdHall(1121).isEmpty(), is(true));
        
    }
    
    
    
    
    
}
