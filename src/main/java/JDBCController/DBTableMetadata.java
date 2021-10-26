/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import sistemaceb.form.Global;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author escal
 */
public class DBTableMetadata{
    
        private DatabaseMetaData metadata;
        private String table;

        public DBTableMetadata(String table){
            super();
            this.table = table;

        }

    public ArrayList<String>  getColumnsMetadata(String popiertyToGet){
        makeMetadataConection();
                ArrayList<String> ColumnsList = new ArrayList();
        try {
            ResultSet columns = getMetadata().getColumns(Global.conectionData.currentDatabase,null,table,null);

            while(columns.next())
                ColumnsList.add(columns.getString(popiertyToGet));


        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConsulter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  ColumnsList;
    }

    public ArrayList<Integer>  getColumnsSize(){
        makeMetadataConection();
        ArrayList<Integer> columnsList = new ArrayList();
        try {
            ResultSet columns = getMetadata().getColumns(Global.conectionData.currentDatabase,null,table,null);

            while(columns.next())
                columnsList.add(columns.getInt("COLUMN_SIZE"));

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConsulter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return  columnsList;
    }

    public ArrayList<String> getAutoIncCols(){

        ArrayList<String> AutoIncCols = new ArrayList<>();
        ArrayList<String> cols = getColumnsMetadata("COLUMN_NAME");
        try {
            makeMetadataConection();
            ResultSet resultSet = Global.SQLConector.getMyStatment().executeQuery("SELECT * FROM " + table);
            ResultSetMetaData metadata = resultSet.getMetaData();
            int size = cols.size();
            for (int i = 1; i <= size;i++)
                if(metadata.isAutoIncrement(i))
                    AutoIncCols.add(cols.get(i-1));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return AutoIncCols;
    }
        
    public ArrayList<String> getPrimaryKeyColumn(){
        makeMetadataConection();

        ArrayList<String> columnNames = new ArrayList();
        try {
            ResultSet primaryKeyColumn = null;
             primaryKeyColumn = getMetadata().getPrimaryKeys(DBSTate.currentDatabase, DBSTate.currentDatabase, table);
             while(primaryKeyColumn.next()){
                 String columnName = primaryKeyColumn.getString("COLUMN_NAME");
                 columnNames.add(columnName);
             }

        } catch (SQLException ex) {
            Logger.getLogger(infoPackage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return columnNames;
    }
    
    public ArrayList<String> getAccesibleCols(String privilege){
        makeMetadataConection();

        ResultSet rs;
        ArrayList<String> columns = getColumnsMetadata("COLUMN_NAME");
        ArrayList<String> accessibleCols = new ArrayList();

            try {
                makeMetadataConection();
                for(String column: columns){
                    rs = getMetadata().getColumnPrivileges(Global.SQLConector.getMyCon().getCatalog(),null,table,column);

                while(rs.next()){
                    if (rs.getString("PRIVILEGE").equals(privilege))
                        accessibleCols.add(column);
                    
                }
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(DBTableMetadata.class.getName()).log(Level.SEVERE, null, ex);
            }
        return accessibleCols;
        
    }
    
    public ArrayList<String> getForeignKeysMetadata(String order){
        makeMetadataConection();
        ArrayList<String> importedKeys = new ArrayList();
        
            try {
                ResultSet meta = getMetadata().getImportedKeys(
                    DBSTate.currentDatabase,
                    DBSTate.currentDatabase,
                    table);

                while (meta.next())
                    importedKeys.add(meta.getString(order));
                
            } catch (SQLException ex) {
                Logger.getLogger(DBTableMetadata.class.getName()).log(Level.SEVERE, null, ex);
            }


            importedKeys = getUnique(importedKeys);
            return importedKeys;
        
    }

    private ArrayList<String> getUnique(ArrayList<String> array){
        ArrayList<String> newArray = new ArrayList<>();

        for (String val:array)
            if (!newArray.contains(val))
                newArray.add(val);

        return newArray;
    }

    private DatabaseMetaData getMetadata(){
        if (!Global.SQLConector.checkConection())
            makeMetadataConection();
        return metadata;

    }
    
    private void makeMetadataConection(){
        try {
            metadata = Global.SQLConector.getMyCon().getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(DBTableMetadata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
}
