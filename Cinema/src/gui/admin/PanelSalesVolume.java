package gui.admin;

import core.AdminController;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import core.obj.Booking;

/**
 * Pannello contenente l'interfaccia che permette di vedere e salvare le prenotazioni al interno di una fascia temporale
 * 
 */
public class PanelSalesVolume extends JPanel {
    private String From, To;
    private ArrayList<Booking> books;
//    private double tot;
    
    public PanelSalesVolume(final AdminController controller, final JLabel outputGrafico) {
        outputGrafico.setText("Sales Volume");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(North(controller));
        this.add(Center());
        this.add(South(controller));
    }
    
    private JPanel North(final AdminController c){
        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton search = new JButton("Search");
        final Date today = new Date();
        final JSpinner dateFrom = new JSpinner(new SpinnerDateModel(setDate(today, 1, true), null, today, Calendar.MONTH));
        final JSpinner dateTo = new JSpinner(new SpinnerDateModel(today, null, today, Calendar.MONTH));
        JSpinner.DateEditor formatFrom = new JSpinner.DateEditor(dateFrom, "dd-MM-yyyy");
        JSpinner.DateEditor formatTo = new JSpinner.DateEditor(dateTo, "dd-MM-yyyy");
        dateFrom.setEditor(formatFrom);
        dateTo.setEditor(formatTo);
        search.setPreferredSize(new Dimension(120, 30));
        
        north.add(new JLabel("From: "));
        north.add(dateFrom);
        north.add(new JLabel("To: "));
        north.add(dateTo);
        north.add(search);
        
        //***************************** LISTENERS        
                dateTo.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                long x = getTimeDiff((Date)dateTo.getValue(), (Date)(dateFrom.getValue()));
                if (x<0) {
                    search.setEnabled(false);
                }else{
                    search.setEnabled(true);
                }
            }
        });
                
        dateFrom.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                long x = getTimeDiff((Date)dateTo.getValue(), (Date)(dateFrom.getValue()));
                if (x<0) {
                    search.setEnabled(false);
                }else{
                    
                    search.setEnabled(true);
                }
            }
        }); 
        
         search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    From = dateToString((Date)dateFrom.getValue()).substring(0, 10) + " 00:00:00"; 
                    To = dateToString((Date)dateTo.getValue()).substring(0, 10) + " 23:59:59";
                    books = c.salesVolume(From, To);                          
                    double tot = tableUpdateAndCount(books);
                    total.setText(String.valueOf(tot));
                    
                } catch (SQLException ex) {
                    Logger.getLogger(PanelSalesVolume.class.getName()).log(Level.SEVERE, null, ex);
                }
                // do something
            }
        });
        
        return north;
    }
    
    String[] columnNames = {"ID", "Date & Time", "3D Glasses", "Price"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    private JPanel Center(){
        JPanel center = new JPanel();
        JTable table = new JTable(tableModel);
        TableColumn id = table.getColumnModel().getColumn(0);
        id.setMinWidth(40); id.setMaxWidth(40); id.setPreferredWidth(40);
        TableColumn glasses = table.getColumnModel().getColumn(3);
        glasses.setMinWidth(80); glasses.setMaxWidth(80); glasses.setPreferredWidth(80);
        JScrollPane scrollPane = new JScrollPane(table); 
//        table.setPreferredSize(new Dimension(600, 200));
//        table.setPreferredScrollableViewportSize(table.getPreferredSize());
//        table.setFillsViewportHeight(true);
        center.add(scrollPane);
        return center;
    }
    
    final JTextField total = new JTextField("0", 10);
    private JPanel South(final AdminController contr){
        JPanel south = new JPanel();
        JLabel tot = new JLabel("Tot: ");
        total.setEditable(false); 
        JButton save = new JButton("Save");
        save.setPreferredSize(new Dimension(120, 30));
        
        south.add(tot);
        south.add(total);
        south.add(save);
        
        //***************************** LISTENERS   
         save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("TXT file", "txt");
                c.addChoosableFileFilter(filter);
                int rVal = c.showSaveDialog(null);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    String filename = (c.getSelectedFile().getName());
                    String path = (c.getCurrentDirectory().toString());
                    if (contr.fileWriter(path + "/" + filename  + ".txt", From, To, books, total.getText())) {
                    }else{
                        JOptionPane.showMessageDialog(null, "Error while trying to export the data. \n", "Save Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
//                    outputGrafico.setText("You pressed cancel");
                }
            }
        });

        return south;
    }

//******************************************************************************
    private Date setDate(Date date, int month, boolean check) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (check) { //FROM
            cal.add(Calendar.MONTH, -month);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        } else {        //TO
            cal.set(Calendar.HOUR_OF_DAY, 22);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 0);

            return cal.getTime();
        }
    }
    
    public long getTimeDiff(Date dateOne, Date dateTwo) {
        long timeDiff = (dateOne.getTime() - dateTwo.getTime());
        return timeDiff;
    }
    
    public double tableUpdateAndCount(ArrayList<Booking> x){
        tableModel.setRowCount(0);
        double tot = 0D;
        for (Booking p : x) {      
            Object[] datas = {p.getIdBooking(), p.getData_ora_sql(),p.getNumberOfGlasses(), p.getPrice()}; 
            tot += p.getPrice();
            tableModel.addRow(datas);
        }
        return tot;
    }       
    
    public String dateToString(Date date){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}
