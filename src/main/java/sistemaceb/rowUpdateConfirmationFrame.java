/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.StyleRowModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author escal
 */
public class rowUpdateConfirmationFrame extends SubmitFrame {
    
    private ArrayList<String> titles;
    private ArrayList<String> data;
    
    public rowUpdateConfirmationFrame (String title,ArrayList<String> titles,ArrayList<String> data){
        super(title);

        this.titles = titles;
        this.data = data;
        addBody(deployTable());
        setSize(new Dimension(800,300));
        
        
    }

    private JPanel deployTable() {
        JPanel tableContainer = new JPanel(new GridBagLayout());
        AdapTableFE rowTable = new AdapTableFE();
            
            rowTable.setTitles(titles);
            rowTable.addRow(data);
            rowTable.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            
            rowTable.addRowStyle(new StyleRowModel(){
                 public RowsFactory.row setStyleModel(RowsFactory.row component){
                     
                     component.setBackground(Color.white);
                     return component;
                 }     
            });
           
            GridBagConstraints tableConstraints = new GridBagConstraints();
            tableConstraints.weightx  = 1;
            tableConstraints.fill = GridBagConstraints.HORIZONTAL;
            
            rowTable.showAll();
            
            tableContainer.add(rowTable,tableConstraints);
        
        return tableContainer;
    }
    
    
}
