/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import JDBCController.CrudCerator;
import JDBCController.DBU;
import JDBCController.Table;
import JDBCController.TableRegister;
import sistemaceb.form.Global;
import sistemaceb.form.MenuListsContainer;

/**
 *
 * @author escal
 */
public class MainWindowMenu extends MenuListsContainer {
    

    public MainWindowMenu(){
        super();
        addTablas();
        addToolsSubMenu();
    }
    
    private void addTablas()  {
        ListButton list = addNewSubmenu("Tablas","images/tablasIcon.png");

        Table consulTableTables = Global.conectionData.getMainTables();

        for(TableRegister register:consulTableTables.getRegistersObject() ){
            String
                viewName = register.get("table_name"),
                viewAlias = register.get("alias");

            list.addButton(viewAlias, new CrudCerator() {
                @Override
                public CrudWindow create() {
                    return new CrudWindow(viewName);
                }
            });
        }
    }

    private void addToolsSubMenu()  {
        ListButton list = addNewSubmenu("Herramientas","images/tablasIcon.png");

        //list.addButton("Pasar de periodo", new SemestrePasador());

    }
    

    

    
}
