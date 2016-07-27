/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import customer.CustomerController;
import inputoutput.AdapterSQLAdmin;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    public TesterCustomer() {
    }
    @Test
    public void testQuery() throws SQLException {
        ArrayList<Hall> hall =new ArrayList();
        
        AdapterSQLAdmin sql  = new AdapterSQLAdmin();
        assertThat(sql.getHall().isEmpty(), is(false));
        
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
}
