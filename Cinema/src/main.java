
import Gestore.*;
import java.io.IOException;




public class main {

    public static void main(String[] args) throws IOException {

        
        Gui_Gestore gestore = new Gui_Gestore();
        gestore.setVisible(true);
        
        
       Gestore gest = new Gestore();
        boolean addmovie = gest.aggiungiFilm("Fight Club", "Action", 120, "once upon a time...", "www.fight");


    }

}
