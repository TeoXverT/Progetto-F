package gui.admin;

import core.AdminController;
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
import core.obj.Film;
import core.obj.FilmCategories;

/**
 *    Pannello contenente l'interfaccia che permette l'inserimento di nuovi film nel DB
 *      
 *       
 */

public class PanelAddFilm extends JPanel {
    private final JPanel Center = new JPanel(new GridLayout(0, 2, 1, 1));
    private final JPanel CenterFull = new JPanel(new BorderLayout());
    private final JPanel BoxPanel = new JPanel(new BorderLayout(12,12));
    private final JPanel North = new JPanel(new GridLayout(0, 3));
    private final JPanel NorthImg = new JPanel();
    private final JPanel Dispy = new JPanel(new GridLayout(0,1,1,1));
    private final JPanel Nordico = new JPanel(new BorderLayout());
    private final JPanel Descript = new JPanel(new GridLayout(0, 2, 1, 1));
    private final JButton addMovie = new JButton("Add Movie");
    private final JLabel imgCopertina = new JLabel();
    
    JTextField copertinaField = new JTextField();
    ImageIcon icona = new ImageIcon("images/place_holder.gif");
    
    public PanelAddFilm(final AdminController controller, final JLabel outputGrafico) {
        this.setLayout(new BorderLayout(10,10));         
        outputGrafico.setText("Add Movie");
        final JTextField titoloField = new JTextField("", 30);
        final JComboBox<FilmCategories> genereComboBox = new JComboBox<>();
        genereComboBox.setModel(new DefaultComboBoxModel<>(FilmCategories.values()));
        final JTextField durataField = new JTextField("90");
        final JTextArea descrizioneArea = new JTextArea("Not too long",5,1);
        final JTextField linkField = new JTextField();
        descrizioneArea.setLineWrap(true);
        copertinaField.setPreferredSize(new Dimension(1, 10));
        JButton anteprima = new JButton("Preview"); 
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
                    String DescrizioneMax100 = descrizioneArea.getText();
                    if(descrizioneArea.getText().length() > 50){
                        DescrizioneMax100 = DescrizioneMax100.substring(0, 47) +  "...";
                    }
                    if (controller.writeFilm(new Film(titoloField.getText(), Genere, Integer.parseInt(durataField.getText()), DescrizioneMax100, link_normalizzato, copertinaField.getText()))) {
                        outputGrafico.setText("The movie has been added.");
                    } else {
                        outputGrafico.setText("Error. Be sure that there are not empity fields and your internet connection works properly.");
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
                    imgCopertina.setIcon(imgResize(immagine, 170, 200));
                    Nordico.removeAll();
                    NorthImg.add(imgCopertina);
                    Nordico.add(North, BorderLayout.NORTH);
                    Nordico.add(NorthImg, BorderLayout.CENTER);  
                    refreshGUI(Nordico);
                } catch (IOException ex) {
                    imgCopertina.setIcon(icona);
                    Nordico.removeAll();
                    NorthImg.add(imgCopertina);
                    Nordico.add(North, BorderLayout.NORTH);
                    Nordico.add(NorthImg, BorderLayout.CENTER);  
                    refreshGUI(Nordico);
                    JOptionPane.showMessageDialog(null, "Loading preview error. \n Be sure the link is correct and that the computer is connected to internet. ", "Error", JOptionPane.WARNING_MESSAGE); 
                }
            }
        }
        );
        return t;
    }
    
    private void refreshGUI(final JPanel panel) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
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
    
    public ImageIcon imgResize(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }
}
