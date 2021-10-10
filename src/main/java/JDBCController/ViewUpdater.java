/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author escal
 */
public class ViewUpdater {
    
    private  final ArrayList<String> tableTags;
    private final ViewSpecs specs;
    
    
    public ViewUpdater(String view){
        specs = new ViewSpecs(view);
        tableTags = determinateTableTags();
    }
    
    public void insert(Map<String, String> data){
        ArrayList<String> responseTitles = new ArrayList(data.keySet());
        responseTitles = new ArrayList(specs.getCol(responseTitles));
        ArrayList<String> responseValues = new ArrayList(data.values());

        try {
            specs.getUpdater().insert(responseTitles, responseValues);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    
    public ArrayList<String> getTableTags(){

        return new ArrayList(tableTags);
    }
    
    
    private  ArrayList<String > determinateTableTags(){
        ArrayList<String> columns = specs.getInfo().getCols();
        ArrayList<String> foreignColumnsNames = specs.getInfo().getForeignRawCols();
        ArrayList<String> foreignColumnsTables = specs.getInfo().getTableCols();
        for(String col:foreignColumnsNames)
            columns.remove(col);
            
        
        columns = specs.getTag(columns);
        for(String foreignTable:foreignColumnsTables)
            columns.add(determinateHumanKey(foreignTable));
        return columns;
    }

    private String determinateHumanKey(String table){
        ViewSpecs specs = new ViewSpecs(table);

        return specs.getInfo().getHumanKey();
    }
    
}
