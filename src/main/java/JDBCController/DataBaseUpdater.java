/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import java.util.ArrayList;


/**
 *
 * @author escal
 */
public class DataBaseUpdater {
    
    String table;
    ViewSpecs specs;
    
    public DataBaseUpdater( String view){
        super();
        specs = new ViewSpecs(view);
      table = view;
        
    }


    



    private void deleteRelatedTables(String value){
        ArrayList<String> relatedTables = specs.getRelatedTables();
        for(String table: relatedTables){
            ViewSpecs tableSpecs = new ViewSpecs(table);

            ArrayList<String> columns = tableSpecs.getForeignTags();
            ArrayList<String> tables = tableSpecs.getInfo().getForeignTables();

            ArrayList<String>  foreignKey = new ArrayList<>();
                foreignKey.add(columns.get(tables.indexOf(specs.getTable())));

            ArrayList<String> valueArray = new ArrayList<>();
                valueArray.add(value);

        }

    }


    




    
    
}
