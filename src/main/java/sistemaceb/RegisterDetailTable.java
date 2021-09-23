/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import JDBCController.ViewSpecs;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.RowsStyles;
import Tables.StyleRowModel;
import sistemaceb.form.MultipleAdderConsultBuild;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author escal
 */
public class RegisterDetailTable extends JPanel {
    
    private AdapTableFE outputTable;
    private reactiveSearchBar searchInput;
    protected final ViewSpecs viewSpecs;
    public ConsultTableBuild consultTable;
    protected ViewSpecs parentSpecs;
    protected final String critValue;

    public RegisterDetailTable(String view,String critKey, ViewSpecs parentSpecs){
        viewSpecs = new ViewSpecs(view);
        critValue = critKey;
        this.parentSpecs = parentSpecs;

    }

    public void setbuild(ConsultTableBuild consultTable){
        this.consultTable = consultTable;
        consultTable.updateSearch();
        deployGUI();

    }

    public ConsultTableBuild getConsultTable(){
        return consultTable;
    }

    private void deployGUI(){
        setLayout(new BorderLayout());
        add(deployHeader(),BorderLayout.NORTH);
        add(deployTable(),BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(0,0,100,0));

    }

    private JPanel deployHeader(){
        JPanel header = new JPanel(new GridBagLayout());
        
        GridBagConstraints titleCons = new GridBagConstraints();
            titleCons.gridy = 1;
            titleCons.weightx =  1;
            titleCons.fill = GridBagConstraints.HORIZONTAL;
            
        header.add(deployTitle(),titleCons);
        
        GridBagConstraints searchCons = new GridBagConstraints();
            searchCons.gridy = 2;
            searchCons.weightx =  1;
            searchCons.fill = GridBagConstraints.HORIZONTAL;
        header.add(deploySearchOptions(),searchCons);
        
        return header;
        
    }

    private JPanel deployTable(){

        outputTable = consultTable.getOutputTable();
        outputTable.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        outputTable.addTitleStyle(new StyleRowModel(){
            @Override
                public RowsFactory.row setStyleModel(RowsFactory.row title) {
                    title.setBackground(new Color(52, 174, 219));
                    title.setOpaque(false);
                    title.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

                    return title;
                }
            });
        
        outputTable.addRowStyle(new StyleRowModel(){
            @Override
            public RowsFactory.row setStyleModel(RowsFactory.row component) {
                component.setBackground(Color.WHITE);
               component.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(253,253,253)));
                return component;
            }

        });
            outputTable.addRowStyle(RowsStyles.iluminateOnOver(new Color(245,245,245)));
            outputTable.setBackground(Color.white);
            return outputTable;
    }

    private JPanel deployTitle() {
        
        JPanel panelTitle = new JPanel(new GridLayout(1,1));
            panelTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel(viewSpecs.getInfo().getHumanName());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            titleLabel.setForeground(new Color(46, 134, 222));
            titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(200,200,200)));
        
        panelTitle.add(titleLabel);
            panelTitle.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        
        return panelTitle;
    }

    JPanel SearchOptionsContainer;
    private JPanel deploySearchOptions(){
         SearchOptionsContainer = new JPanel(new BorderLayout());
            SearchOptionsContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

        
        return SearchOptionsContainer;
    }
            
    public void addDefInsertButton(){
        BtnFE responseBtn = consultTable.getInsertButton();
        setBtnStyles(responseBtn);
        addLeftControls(responseBtn);

    }

    public void addSearchOptions(){
        searchInput = consultTable.getReactiveSearchBar();
        SearchOptionsContainer.add(searchInput,BorderLayout.EAST);
    }

    public void addLeftControls(JComponent component){
        SearchOptionsContainer.removeAll();
        SearchOptionsContainer.add(component,BorderLayout.WEST);
    }

    public void setBtnStyles(BtnFE btn){
        btn.setPadding(10, 10, 10, 10);
        btn.setBackground(new Color(46, 134, 222));
        
    }
    
    
    
}
