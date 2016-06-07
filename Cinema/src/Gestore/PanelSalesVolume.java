/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestore;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.HOUR_OF_DAY;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
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
import oggetti.Prenotazione;

/**
 *
 * @author NEVERMIND
 */
public class PanelSalesVolume extends JPanel {
    
    public PanelSalesVolume(final Controller_Gestore controller, final JLabel outputGrafico) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(North(controller));
        this.add(Center());
        this.add(South());
    }
    
    private JPanel North(final Controller_Gestore c){
         //RICORDATI DI SETTARE L'ORA A MEZZANOTTE per From e 23:59 per To
        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton search = new JButton("Search");
        final Date today = new Date();
        final JSpinner dateFrom = new JSpinner(new SpinnerDateModel(setDate(today, 1, 0), null, today, Calendar.MONTH));
        final JSpinner dateTo = new JSpinner(new SpinnerDateModel(setDate(today,0,1), null, today, Calendar.MONTH));
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
                    ArrayList<Prenotazione> books = c.salesVolume(dateToString((Date)dateFrom.getValue()), dateToString((Date)dateTo.getValue()));
                    System.out.println(dateToString((Date)dateTo.getValue()));                               
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
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        center.add(scrollPane);
        return center;
    }
    
    final JTextField total = new JTextField("0", 10);
    private JPanel South(){
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
//                    filename.setText(c.getSelectedFile().getName());
//                    dir.setText(c.getCurrentDirectory().toString());
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
//                    outputGrafico.setText("You pressed cancel");
                }
            }
        });
         
        return south;
    }
    
//******************************************************************************
    private Date setDate(Date date, int month, int check) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(check==0){ //FROM
            cal.add(Calendar.MONTH, -month);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }else{        //TO
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
    
    public double tableUpdateAndCount(ArrayList<Prenotazione> x){
        tableModel.setRowCount(0);
        double tot = 0D;
        for (Prenotazione p : x) {      
            Object[] datas = {p.getId_prenotazione(), p.getData_ora_sql(),p.getNumber_of_glasses(), p.getPrezzo()}; 
            tot += p.getPrezzo();
            tableModel.addRow(datas);
        }
        return tot;
    }       
    
    public String dateToString(Date date){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}
