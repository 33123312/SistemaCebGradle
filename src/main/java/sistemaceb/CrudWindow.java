/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import SpecificViews.LinearHorizontalLayout;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.RowsStyles;
import Tables.StyleRowModel;
import Generals.TagList;
import JDBCController.ViewSpecs;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sistemaceb.form.FormWindow;
import sistemaceb.form.Formulario;

public class CrudWindow extends Window {
    
    private final SimpleInsertTableBuild consulterTable;
    private TagList tagsManager;
    private final ViewSpecs viewSpecs;
    
    public CrudWindow(String view){
        super();
        viewSpecs = new ViewSpecs(view);
        setTitle(viewSpecs.getInfo().getHumanName());
        consulterTable = new SimpleInsertTableBuild(view);
        consulterTable.updateSearch();
        deployAll();
        
    }

    @Override
    public void update() {
        consulterTable.updateSearch();
    }

    private void deployAll(){
        LinearHorizontalLayout layout = new LinearHorizontalLayout();
            layout.addElement(getResetButon());
            layout.addElement(getSearchBar());
        addToHeader(layout);
        addBody(deployBodyContainer());
    }

    private BtnFE getResetButon(){
        BtnFE btn = new BtnFE("Mostrar todo");
            btn.setTextColor(new Color(9, 132, 227));
            btn.setPadding(0,10,0,10);
            btn.setMargins(0,0,0,10,Color.white);
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    consulterTable.resetNSearch();
                }
            });

        return btn;
    }

    private reactiveSearchBar getSearchBar(){
        reactiveSearchBar searchBar = consulterTable.getReactiveSearchBar();
        searchBar.addInputManager(new stringInputManager(){
            @Override
            public void manageData(String tagName, String data) {
                tagsManager.removeAllTags();
            }
        });

        return searchBar;
    }
  
    private JPanel deployBodyContainer(){
        JPanel container = new  JPanel( new BorderLayout());
            container.add(deployTagsSearcher(),BorderLayout.NORTH);
            container.add(deployTableAndControls(),BorderLayout.CENTER);
            container.setBackground(new Color(20,240,240));

        return container;
    }
    
    private JPanel deployTagsSearcher(){
        Color backColor = new Color(247, 247, 247);
        JPanel tagsSearcher = new JPanel(new BorderLayout());
        JPanel titleLabelContainer = new JPanel(new GridLayout());
            titleLabelContainer.setBackground(backColor);
            JLabel titleLabel = new JLabel("  Tags a buscar:");
            titleLabel.setForeground(new Color(100,100,100));
            titleLabelContainer.add(titleLabel);

        tagsManager = new TagList(){
            
                @Override
                public void whenRemovesTag(String type){              
                    consulterTable.getDataBaseConsulter().removeSearch(type);
                }

                @Override
                public void whenChanges(){
                        consulterTable.updateSearch(); 
                }
        };
        tagsManager.setBackground(backColor);
        tagsManager.setTagMargin(10, 5, 10, 5);
        tagsManager.setTagPadding(2, 5, 2, 5);
        tagsManager.setTagForeground(new Color(231, 230, 255));
        tagsManager.setTagBackground(new Color(162, 155, 254));
        tagsManager.setDestroyButtonForeground(Color.white);
        tagsManager.setTagFont( new Font("Arial", Font.BOLD, 12));
        tagsManager.showTags();

        tagsSearcher.add(titleLabelContainer,BorderLayout.WEST);
        tagsSearcher.add(tagsManager,BorderLayout.CENTER);
        tagsSearcher.add(deployTagButtons(),BorderLayout.EAST);

        return tagsSearcher;
    }
    
    private JPanel deployTagButtons(){
        
        Color backColor = new Color(249, 249, 249);
        JPanel buttonsContainer = new JPanel(new GridLayout(1,2));
        buttonsContainer.setBackground(backColor); 
        
        BtnFE btnAddTag = new BtnFE("+Agregar Tag");
            btnAddTag.setBackground(backColor);
            btnAddTag.setTextColor(new Color(108, 92, 231));
            btnAddTag.setPadding(10, 15, 10, 15);
            btnAddTag.addMouseListener(
                new MouseAdapter(){

                    @Override
                    public void mousePressed(MouseEvent arg0){
                        Formulario tagForm = new FormWindow("Nuevo Tag");
                        ArrayList<String> columnsTofilter = viewSpecs.getTableTags();
                        ArrayList<String> keys = viewSpecs.getPrimaryskey();
                        if (keys.size() < 2){
                            String key = keys.get(0);
                            columnsTofilter.remove(key);
                        }

                        new TagFormBuilder(viewSpecs,columnsTofilter,tagForm,false);
                        tagForm.addDataManager(
                            new FormResponseManager(){
                                @Override
                                public void manageData(Formulario form) {
                                    Map<String,String> trueData = form.getData();
                                    Map<String,String> rawData = form.getGUIData();

                                    consulterTable.getDataBaseConsulter().addSearch(trueData);
                                    tagsManager.addTags(rawData);
                                    ((FormWindow)tagForm).getFrame().closeForm();
                                           }
                            }
                        );
                    }

                }
            );
        
        buttonsContainer.add(btnAddTag);
        return buttonsContainer; 
    }

    
        
    private editedScrollPanel deployTableAndControls(){
        editedScrollPanel scroll = new editedScrollPanel();
        JPanel container = new  JPanel( new BorderLayout());
            container.add(deployTableOptionsContainer(),BorderLayout.NORTH);
            container.add(deployTableContainer(),BorderLayout.CENTER);
            container.setBackground(new Color(20,240,240));
        scroll.setViewportView(container);
        container.setBackground(this.getBackground());
        container.setBorder(BorderFactory.createEmptyBorder(0,30,0,20));
        return scroll;
    }

    public JPanel deployTableOptionsContainer(){
        
         JPanel generalContainer = new JPanel(new BorderLayout());

        BtnFE addBtn = consulterTable.getInsertButton();
            addBtn.setPadding(5,10,5,10);
            addBtn.setMargins(15,5,0,0,generalContainer.getBackground());
            addBtn.setBackground(new Color(2, 152, 219));
            generalContainer.add(addBtn,BorderLayout.EAST);
            generalContainer.add(consulterTable.getInstructionsPanel(),BorderLayout.WEST);

        return generalContainer;
        
    }
        
    public JPanel deployTableContainer(){

        AdapTableFE outputTable  = consulterTable.getOutputTable();
            outputTable.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
            outputTable.addTitleStyle(new StyleRowModel(){
            @Override
                public RowsFactory.row setStyleModel(RowsFactory.row component) {
                    component.setBackground(Color.WHITE);
                    return component;
                }

            });

            outputTable.addRowStyle(new StyleRowModel(){
                @Override
                public RowsFactory.row setStyleModel(RowsFactory.row component) {
                    component.setBackground(Color.WHITE);
                    component.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240,240,240)));
                    return component;
                }

            });
            
            outputTable.addRowStyle(RowsStyles.iluminateOnOver(new Color(245,245,245)));
            outputTable.setBackground(Color.white);


        JPanel generalContainer = new JPanel(new GridLayout());
            generalContainer.add(outputTable);

        return generalContainer;
    }



}





