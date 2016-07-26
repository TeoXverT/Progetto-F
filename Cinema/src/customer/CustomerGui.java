package customer;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CustomerGui extends JFrame {

    private JPanel display = new JPanel(new BorderLayout());

    public CustomerGui() {
        draw();
    }

    public void draw() {
        this.setTitle("Cliente");
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 920, 770);
        ImageIcon icon = new ImageIcon("images/logo.png");
        setIconImage(icon.getImage());

        this.add(display);
        display.add(new PageOne());
    }

}
