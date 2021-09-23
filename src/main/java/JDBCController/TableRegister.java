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
public class TableRegister {
    ArrayList<String> columnTitles;
    ArrayList<String> values;
    
    TableRegister(ArrayList<String> columnTitles,ArrayList<String> values){
        this.columnTitles = columnTitles;
        this.values = values;
    }

    public ArrayList<String> getColumnTitles() {
        return columnTitles;
    }

    public ArrayList<String> getValues() {
        return values;
    }
    
    public String  get(String columnTitle){

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
}
