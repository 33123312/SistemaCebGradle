/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author escal
 */
public class TableRegister {
    ArrayList<String> columnTitles;
    ArrayList<String> values;
    
    public TableRegister(ArrayList<String> columnTitles,ArrayList<String> values){
        this.columnTitles = columnTitles;
        this.values = values;
    }

    public ArrayList<String> getColumnTitles() {
        return columnTitles;
    }

    public ArrayList<String> getValues() {
        return values;
    }
    
    public String get(String columnTitle){

        if(columnTitles.contains(columnTitle))
            return values.get(columnTitles.indexOf(columnTitle));
        else {
            System.err.println("El tag " + columnTitle + " no pertenece a la tabla, las opciones  son: " + columnTitles );
            return null;
        }
    }
    
    public String get(int index){
        
        return  values.get(index);
    }

    public Map<String,String> toMap(){
        Map<String,String> val = new HashMap<>();
        int size = columnTitles.size();
        for (int i = 0; i < size; i++)
            val.put(
                columnTitles.get(i),
                values.get(i)
            );
        return val;
    }

}
