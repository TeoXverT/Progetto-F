/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import admin.AdminController;
import customer.CustomerController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.Booking;
import obj.Config;
import obj.Film;
import obj.Hall;
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
    public void writeFilm() {
        //testo scrittura film
        AdminController c = AdminController.getInstance();
        assertThat( c.writeFilm(new Film(null, null, 0, null, null, null)) == false , is(true));
    }
    
    
    @Test
    public void test2() throws SQLException {
        
         AdminController c = AdminController.getInstance();
         assertThat(c.getFilm(0).isEmpty(), is(false));
        
    }
    
    
    @Test
    public void test3() throws SQLException {
        
        AdminController c = AdminController.getInstance();
        assertThat(c.writeConfig(new Config(0, 0, 0, 0, 0)) == false, is(true));
        
    }
    
    
   
    
    @Test
    public void test5() throws SQLException {
        AdminController c = AdminController.getInstance();
        ArrayList<Hall> hall = new ArrayList<Hall>();
        hall = c.getHall();
       
        assertThat(hall.isEmpty(), is(false));
        
        
    }
    
    
    
    
}
