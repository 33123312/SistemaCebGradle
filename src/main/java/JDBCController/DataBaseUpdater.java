/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import sistemaceb.form.Global;

import javax.swing.text.View;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


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


    



    
    public void delete (ArrayList<String> columnCondition,ArrayList<String> values) throws SQLException {
        /*if(specs.getPrimaryskey().size() == 1){
            String primaryKey = DBU.getColumn(specs.getPrimarykey(),specs.getTable()) ;
            int keyIndex = columnCondition.indexOf(primaryKey);
            deleteRelatedTables(values.get(keyIndex));
        }
        */
        //deleteRegister(columnCondition,values);
    }

    private void deleteRelatedTables(String value){
        ArrayList<String> relatedTables = specs.getRelatedTables();
        for(String table: relatedTables){
            ViewSpecs tableSpecs = new ViewSpecs(table);

            ArrayList<String> columns = tableSpecs.getForeignCols();
            ArrayList<String> tables = tableSpecs.getInfo().getForeignTables();

            ArrayList<String>  foreignKey = new ArrayList<>();
                foreignKey.add(columns.get(tables.indexOf(specs.getTable())));

            ArrayList<String> valueArray = new ArrayList<>();
                valueArray.add(value);

        }

    }


    




    
    
}
