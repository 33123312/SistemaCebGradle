/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.SearchBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author escal
 */
public class reactiveSearchBar extends JPanel {
    
    private JComboBox  SimpleSearchMenu;
    private SearchBar simpleSearchBar;
    protected final ArrayList<stringInputManager> inputManagers;
    
    public reactiveSearchBar(){
        inputManagers = new ArrayList();
        setLayout(new GridLayout(1,1));
        add(deployNavContainer());
        setOpaque(false);
    }
    
    protected void executeInputManagers(String tag,String value){
        for(stringInputManager manager:inputManagers)
            manager.manageData(tag, value);
    }

    private JPanel deployNavContainer(){
         simpleSearchBar = new SearchBar(){
            @Override
            public void manageInput(String input){
                String tag = SimpleSearchMenu.getSelectedItem().toString();
                executeInputManagers(tag,input);
            }
        };
            simpleSearchBar.setPreferredSize(new Dimension(250,30));
            simpleSearchBar.setButtonColor(new Color(87, 95, 207));
            simpleSearchBar.setTxtBorderColor(new Color(87, 95, 207));
            
         JComboBox menu = deployCriterioBusqueda();
            menu.setOpaque(false);
            menu.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
            
        JPanel buttonsContainer = new JPanel(new GridLayout(1,1));
            buttonsContainer.add(menu);
            buttonsContainer.setOpaque(false);
        
        JPanel generalContainer = new JPanel(new BorderLayout());
            generalContainer.add(simpleSearchBar,BorderLayout.CENTER);
            generalContainer.add(buttonsContainer,BorderLayout.WEST);
            generalContainer.setOpaque(false);

        return generalContainer;
    }
    
    public void addInputManager(stringInputManager manager){
        inputManagers.add(0,manager);
    }
    
    private JComboBox deployCriterioBusqueda(){
        
        SimpleSearchMenu = new JComboBox();
            SimpleSearchMenu.setForeground(new Color(100,100,100));
            SimpleSearchMenu.addItemListener(new ItemChangeListener());

        return SimpleSearchMenu;
    }
    
    public ArrayList<String> giveOptions(String selectedTag){
        
        
        return new ArrayList();
    }
    
    public void addItems(ArrayList<String> items){

        for(String item: items)
            SimpleSearchMenu.addItem(item);

    }
    
    private class ItemChangeListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
           if (event.getStateChange() == ItemEvent.SELECTED) {

              String selectedTag = event.getItem().toString();

              ArrayList<String> options = giveOptions(selectedTag);

                if(options.isEmpty())
                    simpleSearchBar.useTextField();
                else
                    simpleSearchBar.useComboBox(options);
           }
        }       
}
    
}
