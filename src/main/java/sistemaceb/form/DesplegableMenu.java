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
    private JComboBox menu;

    public DesplegableMenu(String title){
        super(title,"");
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
        menu = new JComboBox();
        menu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                executeTrigerEvents();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
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
    public void setResponse(String txt) {
        super.setResponse(txt);
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
    
}


