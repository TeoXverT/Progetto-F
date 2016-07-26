/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import com.sun.org.apache.xerces.internal.util.FeatureState;
import static com.sun.org.apache.xerces.internal.util.FeatureState.is;
import customer.CustomerController;
import obj.Booking;
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
    }

    private void assertThat(boolean b, FeatureState is) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
