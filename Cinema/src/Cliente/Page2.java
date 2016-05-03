/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Riccardo
 */
public class Page2 extends JPanel {
    
    
    
    
    public Page2(int id_film,Controller_Cliente controller ){
        controller = new Controller_Cliente();
        id_film=id_film;
        this.setLayout(new GridLayout(0, 2, 1, 50));
     
        
       }
    
}
