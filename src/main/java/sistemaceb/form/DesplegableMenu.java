/*}}
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import JDBCController.Table;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author escal
 */
public class DesplegableMenu extends formElementWithOptions{
    private JComboBox menu;

    public DesplegableMenu(String title){
        super(title);
        buildMenu();
        setTrigerEvent();
    }

    @Override
    public void selectEmptyResponse() {
        menu.setSelectedIndex(menu.getItemCount()-1);
    }

    public JComboBox getMenu() {
        return menu;
    }

    @Override
    protected void buildGUIOptions() {
        super.buildGUIOptions();
        addOption("");
        menu.setSelectedIndex(menu.getItemCount()-1);
    }

    private void buildMenu(){
        menu = new JComboBox();
        menu.setForeground(new Color(100,100,100));
        addElement(menu);
        
    }

    @Override
    public void setResponse(String txt) {
       if(trueOptions.contains(txt))
           setSelection(GUIOptions.get(trueOptions.indexOf(txt)));
       else if(GUIOptions.contains(txt))
           setSelection(txt);
    }

    @Override
    protected boolean somethingIsSelected() {

        return menu.getSelectedIndex()!= GUIOptions.size()  ;
    }

    @Override
    public void setEnabled(boolean enabled){
        menu.setEnabled(enabled);
    }

    @Override
    public int getSelectedElementIndex(){

        return menu.getSelectedIndex();
    }

    @Override
    public void addOption(String option){
        menu.addItem(option);
    }

    @Override
    public void removeGUIOptions(){
         menu.removeAllItems();
    }

    ItemListener lis = new ItemListener(){

        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED)
                executeTrigerEvents();

        }
    };

    @Override
    public void setSelection(String selection) {
        menu.setEnabled(true);
        menu.setSelectedIndex(GUIOptions.indexOf(selection));
    }

    protected void setTrigerEvent(){
        menu.addItemListener(lis);
    }

    @Override
    protected void removetriggerEvent() {
        menu.removeItemListener(lis);
    }


    
}


