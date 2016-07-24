/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Cliente.Controller_Cliente;
import oggetti.Prenotazione;
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
        Controller_Cliente c = Controller_Cliente.getInstance();
    }

}
