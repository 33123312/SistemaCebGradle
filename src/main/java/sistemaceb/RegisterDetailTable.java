/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import JDBCController.ViewSpecs;
import JDBCController.infoPackage;
import SpecificViews.LinearVerticalLayout;
import Tables.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 *
 * @author escal
 */
public abstract class RegisterDetailTable extends JPanel{
    protected AdapTableFE outputTable;
    private reactiveSearchBar searchInput;
    protected  ViewSpecs viewSpecs;
    public ConsultTableBuild consultTable;
    protected ViewSpecs parentSpecs;
    protected  String critValue;
    private boolean isInit;

    public RegisterDetailTable(String view){
        viewSpecs = new ViewSpecs(view);
    }

    public void initRegister(String critKey, ViewSpecs parentSpecs){
        critValue = critKey;
        this.parentSpecs = parentSpecs;
        isInit = false;
    }

    public void build(){
        if (isInit)
            reOpen();
        else {
            isInit = true;
            firstImplementation();
        }
    }

    protected void firstImplementation(){
        consultTable.updateSearch();
        deployGUI();
    };

    protected void reOpen(){
        consultTable.updateSearch();
    }

    public String getTableName() {
        return consultTable.getName();
    }

    public void setbuild(ConsultTableBuild consultTable){
        this.consultTable = consultTable;

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
        outputTable.getFactory().addTitleStyle(new StyleRowModel(){
            @Override
                public TableRow setStyleModel(TableRow title) {
                    title.setBackground(new Color(52, 174, 219));
                    title.setOpaque(false);
                    title.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

                    return title;
                }
            });

            outputTable.getFactory().addRowStyle(RowsStyles.iluminateOnOver(new Color(245,245,245)));
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
        LinearVerticalLayout headerPanel = new LinearVerticalLayout();

         SearchOptionsContainer = new JPanel(new BorderLayout());
            SearchOptionsContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

        headerPanel.addElement(SearchOptionsContainer);

        if (consultTable.hasBehavior()){
            JPanel layout = consultTable.getInstructionsPanel();
                layout.setBorder(new EmptyBorder(10,0,0,20));
                headerPanel.addElement(layout);
        }

        return headerPanel;
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
