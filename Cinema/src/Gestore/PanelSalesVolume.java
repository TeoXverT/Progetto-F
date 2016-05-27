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
import java.util.Calendar;
import java.util.Date;
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

/**
 *
 * @author NEVERMIND
 */
public class PanelSalesVolume extends JPanel {
    
    public PanelSalesVolume(final Controller_Gestore controller, final JLabel outputGrafico) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(North());
        this.add(Center());
        this.add(South());
    }
    
    private JPanel North(){
         //RICORDATI DI SETTARE L'ORA A MEZZANOTTE per From e 23:59 per To
        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton search = new JButton("Search");
        final Date today = new Date();
        final JSpinner dateFrom = new JSpinner(new SpinnerDateModel(setDate(today, 1), null, today, Calendar.MONTH));
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
               
            }
        });
        
        return north;
    }
    
    private JPanel Center(){
        JPanel center = new JPanel();
        String[] columnNames = {"ID", "Date & Time", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        TableColumn id = table.getColumnModel().getColumn(0);
        id.setMinWidth(40); id.setMaxWidth(40); id.setPreferredWidth(40);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        center.add(scrollPane);
        return center;
    }
    
    private JPanel South(){
        JPanel south = new JPanel();
        JLabel tot = new JLabel("Tot: ");
        final JTextField total = new JTextField("0", 10);
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
    private Date setDate(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -month);
        return cal.getTime();
    }
    public long getTimeDiff(Date dateOne, Date dateTwo) {
        long timeDiff = (dateOne.getTime() - dateTwo.getTime());
        return timeDiff;
    }
}
