package admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import obj.Film;
import obj.FilmGenere;

/**
 *      BUG NOTI:
 *      -Descrizione troppo lunga: return false dall'Adapter (non so se il database abbia
 *       abbia limiti di lunghezza..)
 */

public class PanelAddFilm extends JPanel {
    JPanel Center = new JPanel(new GridLayout(0, 2, 1, 1));
    JPanel CenterFull = new JPanel(new BorderLayout());
    JPanel BoxPanel = new JPanel(new BorderLayout(12,12));
    JPanel North = new JPanel(new GridLayout(0, 3));
    JPanel NorthImg = new JPanel();
    JPanel Dispy = new JPanel(new GridLayout(0,1,1,1));
    JPanel Nordico = new JPanel(new BorderLayout());
    JPanel Descript = new JPanel(new GridLayout(0, 2, 1, 1));
    
    JButton addMovie = new JButton("ADD MOVIE");
    JLabel imgCopertina = new JLabel();
    
    JTextField copertinaField = new JTextField();
    ImageIcon icona = new ImageIcon("immagini/prova_immagine_copertina.gif");
    
    public PanelAddFilm(final AdminController controller, final JLabel outputGrafico) {
        this.setLayout(new BorderLayout(10,10));         
        
        final JTextField titoloField = new JTextField("", 30);
        //final JTextField genereField = new JTextField("", 30);
        final JComboBox<FilmGenere> genereComboBox = new JComboBox<>();
        genereComboBox.setModel(new DefaultComboBoxModel<>(FilmGenere.values()));
        final JTextField durataField = new JTextField("90");
        final JTextArea descrizioneArea = new JTextArea("Non troppo lunga perchè il database non la prende",5,1);
        final JTextField linkField = new JTextField();
        descrizioneArea.setLineWrap(true);
        copertinaField.setPreferredSize(new Dimension(1, 10));
        JButton anteprima = new JButton("Anteprima"); 
        JScrollPane scroll = new JScrollPane(descrizioneArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
//-----     Building -durata+
        JButton plus = new JButton("+"); //incrementa durata
        JButton less = new JButton("-"); //decrementa durata
        JPanel durata = new JPanel(new GridLayout(0, 3));
        durataField.setHorizontalAlignment(JTextField.CENTER);
        durata.add(less);
        durata.add(durataField);
        durata.add(plus);

//-----     Building Panel Center
        Center.add(new JLabel("Title: "));
        Center.add(titoloField);
        Center.add(new JLabel("Genre: "));
        //Center.add(genereField);
        Center.add(genereComboBox);
        Center.add(new JLabel("Runtime: "));
        Center.add(durata);
        Center.add(new JLabel("Link Trailer: "));
        Center.add(linkField);

//-----     Building Panel North
        North.add(new JLabel("Cover Image: "));
        North.add(copertinaField);
        North.add(anteprima);
        imgCopertina.setIcon(icona);
        NorthImg.add(imgCopertina);
        Nordico.add(North, BorderLayout.NORTH);
        Nordico.add(NorthImg, BorderLayout.CENTER);
        
//------    Building descrizione
        Descript.add(new JLabel("Description: "));
        Descript.add(scroll);
        CenterFull.add(Center, BorderLayout.PAGE_START);
        CenterFull.add(Descript, BorderLayout.PAGE_END);
        
//------    Building this
        Dispy.add(Nordico);
        Dispy.add(CenterFull);
        BoxPanel.add(Dispy, BorderLayout.PAGE_START);
        BoxPanel.add(addMovie, BorderLayout.PAGE_END);
        this.add(BoxPanel);
        
//******    ACTION PERFORMED
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int durataInt = Integer.parseInt(durataField.getText()) + 1;
                durataField.setText("" + durataInt);
            }
        });

        less.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(durataField.getText())>1){
                    int durataInt = Integer.parseInt(durataField.getText()) - 1;
                    durataField.setText("" + durataInt);
                }
            }
        });

        addMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String link_normalizzato;
                if (linkField.getText().contains("=")) {
                    String Genere =  String.valueOf(genereComboBox.getSelectedItem());
                    String[] tmp = linkField.getText().split("=");
                    link_normalizzato = tmp[1];
                    link_normalizzato = "https://www.youtube.com/v/" + link_normalizzato + "?autoplay=1";
                    if (controller.writeFilm(new Film(titoloField.getText(), Genere, Integer.parseInt(durataField.getText()), descrizioneArea.getText(), link_normalizzato, copertinaField.getText()))) {
                        outputGrafico.setText("The movie has been added.");
                    } else {
                        outputGrafico.setText("Error. Be sure that there are not empity fields.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid YouTube's Link. \n Example of right link: https://www.youtube.com/watch?v=aT_CaVUKp00", "Error", JOptionPane.WARNING_MESSAGE);
                
                }
                
            }
        });
        
        anteprima.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               threadAnteprima().start();
            }
        });
    } 
 
    private Thread threadAnteprima() {
        Thread t = new Thread(new Runnable() {
            public void run() {

                try {
                    ImageIcon immagine = new ImageIcon(ImageIO.read(new URL(copertinaField.getText())));
                    //EVENTUALMENTE SCALARLA LEGGENDO PRIMA LE DIMENSIONI ORIGINALI
                    imgCopertina.setIcon(scalaImmagine(immagine, 170, 200));
                    Nordico.removeAll();
                    NorthImg.add(imgCopertina);
                    Nordico.add(North, BorderLayout.NORTH);
                    Nordico.add(NorthImg, BorderLayout.CENTER);  
                    aggiornaGUI(Nordico);
                } catch (IOException ex) {
                    imgCopertina.setIcon(icona);
                    Nordico.removeAll();
                    NorthImg.add(imgCopertina);
                    Nordico.add(North, BorderLayout.NORTH);
                    Nordico.add(NorthImg, BorderLayout.CENTER);  
                    aggiornaGUI(Nordico);
                    JOptionPane.showMessageDialog(null, "Errore caricamento anteprima. \n Assicurati che il link sia corretto e che il computer sia connesso a internet. ", "Attenzione!!!", JOptionPane.WARNING_MESSAGE); 
                }
            }
        }
        );
        return t;
    }
    
    private void aggiornaGUI(final JPanel panel) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //String tmp = copertinaField.getText();
                //copertinaField.setText("");
                CenterFull.removeAll();
                BoxPanel.removeAll();
                Dispy.removeAll();
                CenterFull.add(Center, BorderLayout.PAGE_START);
                CenterFull.add(Descript, BorderLayout.PAGE_END);
                Dispy.add(panel);
                Dispy.add(CenterFull);
                BoxPanel.add(Dispy, BorderLayout.PAGE_START);
                BoxPanel.add(addMovie, BorderLayout.PAGE_END);
                BoxPanel.revalidate();
                BoxPanel.repaint();
                
            }
        });
    }
    
    public ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }
    
    
}
