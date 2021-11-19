/*}}
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import JDBCController.Table;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author escal
 */
public class DesplegableMenu extends formElementWithOptions{
    private perCombo menu;

    public DesplegableMenu(String title){
        super(title);
        buildMenu();
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
        addOption(getDefaultValue());
        menu.setSelectedIndex(menu.getItemCount()-1);

    }

    private void buildMenu(){
        menu = new perCombo(){
            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
                if(hasBeenSelected())
                    executeTrigerEvents();

            }
        };

        menu.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        menu.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(isVisible())
                    menu.showPopup();

            }

        });

        menu.setForeground(new Color(100,100,100));
        currentElement = menu;
        addElement(menu);
    }

    @Override
    public void useDefval() {
        menu.setSelectedIndex(menu.getItemCount());
    }

    @Override
    public void setDefaultValue(String defaultValue){
        super.setDefaultValue(defaultValue);
    }

    @Override
    public void setResponse(String txt) {
        super.setResponse(txt);
       if(trueOptions.contains(txt))
           setSelection(GUIOptions.get(trueOptions.indexOf(txt)));
       else if(GUIOptions.contains(txt))
           setSelection(txt);
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
    public formElementWithOptions setOptions(Table options) {
        return super.setOptions(options);
    }

    @Override
    public void addOption(String option){
        menu.addItem(option);
    }

    @Override
    public void removeGUIOptions(){
         menu.removeAllItems();
    }

    @Override
    public void setSelection(String selection) {
        menu.setEnabled(true);
        menu.setSelectedIndex(GUIOptions.indexOf(selection));
    }

    public class perCombo extends JComboBox{
        private int selectionCount = 0;

        public perCombo(){
            super();
        }

        public boolean hasBeenSelected() {
            return selectionCount > 1;
        }

        @Override
        public void setSelectedItem(Object anObject) {
            super.setSelectedItem(anObject);
            if (!hasBeenSelected())
            selectionCount++;
        }
    }
    
}


