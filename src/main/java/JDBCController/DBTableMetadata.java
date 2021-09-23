/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import sistemaceb.form.Global;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author escal
 */
public class DBTableMetadata{
    
        private DatabaseMetaData metadata;
        String table;

        public DBTableMetadata(String table){
            super();
            this.table = table;

        }
        
        public DatabaseMetaData getMetadata(){
            return metadata;
        }
        
        public ArrayList<String>  getColumnsMetadata(String popiertyToGet){
        makeMetadataConection();
                ArrayList<String> ColumnsList = new ArrayList();

        try {
            ResultSet columns = metadata.getColumns(null,null,table,null);

            while(columns.next())
                ColumnsList.add(columns.getString(popiertyToGet));

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConsulter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  ColumnsList;
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
             primaryKeyColumn = metadata.getPrimaryKeys(Global.SQLConector.getMyCon().getCatalog(), null, table);
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
                    rs = metadata.getColumnPrivileges(Global.SQLConector.getMyCon().getCatalog(),null,table,column);

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
                ResultSet meta = metadata.getImportedKeys(Global.SQLConector.getMyCon().getCatalog(), null, table);
                while (meta.next())
                    importedKeys.add(meta.getString(order));
                
            } catch (SQLException ex) {
                Logger.getLogger(DBTableMetadata.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return importedKeys;
        
    }
    
    private void makeMetadataConection(){
        try {
            metadata = Global.SQLConector.getMyCon().getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(DBTableMetadata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
}
