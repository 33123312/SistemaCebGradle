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

    public void update (
            ArrayList<String>colToMod,
            ArrayList<String> newValues,
            ArrayList<String>colCondition,
            ArrayList<String> conditionValue
    ) throws SQLException {
            String primaryCol = specs.getCol(specs.getPrimarykey());

            if(colToMod.contains(primaryCol) && specs.getPrimaryskey().size() == 1){
                int keyIndex = colToMod.indexOf(primaryCol);
                String newPrimaryValue = newValues.get(keyIndex);
                String oldValue = conditionValue.get(0);
                if(!oldValue.equals(newPrimaryValue)) {
                        copyOldRegister(primaryCol, newPrimaryValue, oldValue);

                        updteRelatedTables(newPrimaryValue, oldValue);

                        deleteOldValue(primaryCol, oldValue);

                        conditionValue.remove(0);
                        conditionValue.add(newPrimaryValue);
                    }


                    colToMod.remove(primaryCol);
                    newValues.remove(newPrimaryValue);

            }

        if(!colToMod.isEmpty())
             updateRegister(colToMod,newValues,colCondition,conditionValue);
    }

    private void deleteOldValue(String primaryCol, String oldValue){

        ArrayList<String> condtoDelete = new ArrayList<>();
            condtoDelete.add(primaryCol);

       ArrayList<String> valueToDelete = new ArrayList<>();
        valueToDelete.add(oldValue);

        try {
            delete(condtoDelete,valueToDelete);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void copyOldRegister(String primaryKey,String newValue, String oldValue){
        DataBaseConsulter oldRegisterConsulter = new DataBaseConsulter(specs.getTable());

        String[] oldConditions = new String[]{primaryKey};

        String[] oldValues = new String[]{oldValue};

        TableRegister response = oldRegisterConsulter.bringTable(oldConditions,oldValues).getRegister(0);

        ArrayList<String> cols = response.getColumnTitles();
            cols.remove(primaryKey);
            cols.add(primaryKey);

        ArrayList<String> values = response.getValues();
            values.remove(oldValue);
            values.add(newValue);

        try {
            new DataBaseUpdater(specs.getTable()).insert(cols,values);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void updteRelatedTables(String newPrimaryValue,String oldValue) throws SQLException {
        ArrayList<String> relatedTables = specs.getRelatedTables();
        for(String table: relatedTables){
            ViewSpecs tableSpecs = new ViewSpecs(table);

            ArrayList<String>  foreignKey = new ArrayList<>();
                foreignKey.add(tableSpecs.getTagFromTable(specs.getTable()));

            ArrayList<String> newPrimaryValueArray = new ArrayList<>();
                newPrimaryValueArray.add(newPrimaryValue);

            ArrayList<String> currentPrimaryValueArray = new ArrayList<>();
                currentPrimaryValueArray.add(oldValue);

            foreignKey = new ViewSpecs(table).getCol(foreignKey);
                new DataBaseUpdater(table).update(foreignKey,newPrimaryValueArray,foreignKey,currentPrimaryValueArray);
        }

    }
    
   public void updateRegister (ArrayList<String>colToMod, ArrayList<String> newValues,ArrayList<String>colCondition,ArrayList<String>conditionValue) throws SQLException {
        String stringifiedNewValues = "";
        int i;
        int size = colToMod.size()-1;
        for(i = 0; i < size;i++)
             stringifiedNewValues +=  colToMod.get(i) + " = " + mergeBranches(newValues.get(i)) + ",";


        stringifiedNewValues +=  " " + colToMod.get(i)  + " = " + mergeBranches(newValues.get(i));


            
        String query = "update " + table + " set "  + stringifiedNewValues + " where " + stringifyConditions(colCondition,conditionValue);
        Global.SQLConector.getMyStatment().executeUpdate(query);

   }


   
   private String stringifyConditions(ArrayList<String> columnCondition,ArrayList<String> values){
            StringBuilder stringifiedConditions = new StringBuilder();

            int i;
            int size = columnCondition.size()-1;
            for(i = 0; i < size;i++){
                stringifiedConditions.append(columnCondition.get(i));
                stringifiedConditions.append(" = ");
                stringifiedConditions.append(mergeBranches(values.get(i)));
                stringifiedConditions.append( " and ");
            }

           stringifiedConditions.append(columnCondition.get(i));
           stringifiedConditions.append(" = ");
           stringifiedConditions.append(mergeBranches(values.get(i)));
            
            return stringifiedConditions.toString();
   }
    
    public void delete (ArrayList<String> columnCondition,ArrayList<String> values) throws SQLException {
        /*if(specs.getPrimaryskey().size() == 1){
            String primaryKey = DBU.getColumn(specs.getPrimarykey(),specs.getTable()) ;
            int keyIndex = columnCondition.indexOf(primaryKey);
            deleteRelatedTables(values.get(keyIndex));
        }
        */
        deleteRegister(columnCondition,values);
    }

    private void deleteRelatedTables(String value){
        ArrayList<String> relatedTables = specs.getRelatedTables();
        for(String table: relatedTables){
            ViewSpecs tableSpecs = new ViewSpecs(table);

            ArrayList<String> columns = tableSpecs.getForeignCols();
            ArrayList<String> tables = tableSpecs.getForeignColsTables();

            ArrayList<String>  foreignKey = new ArrayList<>();
                foreignKey.add(columns.get(tables.indexOf(specs.getTable())));

            ArrayList<String> valueArray = new ArrayList<>();
                valueArray.add(value);

        }

    }

    private void deleteRegister(ArrayList<String> columnCondition,ArrayList<String> values) throws SQLException {
        String query = "delete from " + table + " where "  + stringifyConditions(columnCondition,values);
        //System.out.println(query);
        Global.SQLConector.getMyStatment().executeUpdate(query);

    }

    public void insert(ArrayList<String> columns,ArrayList<String> values) throws SQLException {

       String query = "insert into " + table + "(" + stringifyColumns(columns) + ") values (" + stringifyValues(values) + ")";
       //System.out.println(query);
        Global.SQLConector.getMyStatment().executeUpdate(query);

    }
    
    private String stringifyColumns(ArrayList<String> columns){
         ArrayList<String> convertedColumns = columns;
        int i;
        StringBuilder stringyfiedColumns = new StringBuilder();
        int size = convertedColumns.size()-1;

        for(i = 0; i < size ;i++){
            stringyfiedColumns.append(convertedColumns.get(i));
            stringyfiedColumns.append(",");
        }

        
        stringyfiedColumns.append(convertedColumns.get(i));

        return stringyfiedColumns.toString();
    }
    
        private String stringifyValues(ArrayList<String> values){
        StringBuilder stringyfiedColumns = new StringBuilder();

        int i;
        int size = values.size()-1;
        for(i = 0;i < size; i++){
            stringyfiedColumns.append(mergeBranches(values.get(i)));
            stringyfiedColumns.append(",");
        }

        stringyfiedColumns.append(values.get(i));

        return stringyfiedColumns.toString();
    }

    private String mergeBranches(String val){
        if(val == null)
            val = "NULL";
        if (!val.equals("NULL"))
            val  ="'"+val+ "'";

        return val;
    }
    
    
}
