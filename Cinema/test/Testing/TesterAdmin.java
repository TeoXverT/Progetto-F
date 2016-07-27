/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import admin.AdminController;
import java.sql.SQLException;
import java.util.ArrayList;
import obj.Booking;
import obj.Config;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import static org.junit.Assert.*;


public class TesterAdmin {
    AdminController c;
    public TesterAdmin() {
        
        c = AdminController.getInstance();
        char[] codice = {'1', '2', '3'};
        c.codeVerification(codice);
        
    }

    @Test
    public void writeconfig() throws SQLException {
       
        assertThat(c.writeConfig(new Config(0, 0, 0, 0, 0)) == false, is(true));
        
    }
    
    @Test
    public void getBooking()  {
        
        ArrayList<Booking> booking = new ArrayList<>();
        try {
            booking = c.getBooking();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        assertThat(booking.isEmpty(), is(true));
        
        
    }
    
}
