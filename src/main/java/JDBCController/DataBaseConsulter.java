/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import Tables.AdapTableFE;
import sistemaceb.form.Global;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author escalcgcg
 */
public class DataBaseConsulter {

    protected final String table;
    
   public  DataBaseConsulter(String table){

        this.table = table;

    }

    public String getTable() {
        return table;
    }

    public Table bringTable(String query){
       return buildRegisters(query);
   }

    public Table bringTable(){

          return buildRegisters(buildSelect("*",""));
    }
    
    public Table bringTable(String[] columnsToSearch){
        
        return buildRegisters(buildSelect(buildCColumnQuery(columnsToSearch),""));
    }
    
    public Table bringTable(String[] columnsToSearch, String[] keyWords ){
            return buildRegisters(buildSelect("*",buildConditionalQuery(columnsToSearch,keyWords )));
          }

    public Table bringTable(String[] columnsToBring,String[] columnsToSearch,String[] keyWords ){
            return   buildRegisters(buildSelect(buildCColumnQuery(columnsToBring) ,buildConditionalQuery(columnsToSearch,keyWords)));
    }
    
    
    private String buildSelect(String columns,String conditions){

        return "select " + columns + " from " + table  + " " + conditions;
    }
    
    private String buildCColumnQuery(String[] columnsToInsert){
        int colNum = columnsToInsert.length;
        if (colNum > 0) {
            StringBuilder query = new StringBuilder();

            int iteratons = colNum - 1;

            for (int i = 0; i < iteratons; i++){
                query.append(columnsToInsert[i]);
                query.append(",");
            }

            query.append(columnsToInsert[iteratons]);

            return query.toString();
        }

        return "*";
    }

    private String buildConditionalQuery(String[] columnsToSearch,String[] keyWords){

        int colsNum = columnsToSearch.length;

        if (colsNum > 0){
            StringBuilder query = new StringBuilder("where BINARY ");
            int iteratons = colsNum-1;
            for (int i = 0; i < iteratons; i++){
                query.append(getEqualString(columnsToSearch[i],keyWords[i]));
                query.append(" and ");
            }

            query.append(getEqualString(columnsToSearch[iteratons],keyWords[iteratons]));

            return query.toString();
        }

        return "";
    }

    private String getEqualString(String cond,String val){
       StringBuilder res = new StringBuilder();
            res.append(cond);
            res.append(" = '");
            res.append(val);
            res.append("'");
        return res.toString();
    }

    public Table executeFunction(String function){
        
        return buildRegisters("Select " + function);
    } 
    
    public Table call(String procedure){
        
        return buildRegisters("call " +  procedure);
    }
   
    private Table  buildRegisters( String query){
        System.out.println(query);
        ResultSet response;

        ArrayList<ArrayList<String>> newTable = new ArrayList<>();
        ArrayList<String> columns = null;

        try{
            response = Global.SQLConector.getMyStatment().executeQuery(query);
            int columnsNumber =  response.getMetaData().getColumnCount();

            while (response.next()) {
                ArrayList<String> newRow =  new  ArrayList();
                for (int i=1; i <= columnsNumber; i++) {
                     String newCell = response.getString(i);
                     newRow.add(newCell);
                }

            newTable.add(newRow);
        } 
            columns = getColumns(response);

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        Table returnTable = new Table(columns,newTable);

        return  returnTable;

    }
    
    private ArrayList<String> getColumns(ResultSet response){
        ArrayList<String> columns = new ArrayList();
        try {
            ResultSetMetaData  meta = response.getMetaData();
            for(int i = 1; i <= meta.getColumnCount();i++){
                columns.add(meta.getColumnName(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConsulter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return  columns;
    }

    public ArrayList<String> getOptions(String optionsColumn, String[] columnsCondition,String[] conditions){
        
        String conditionToSend = "";

        if (conditions.length > 0)
            conditionToSend = buildConditionalQuery(columnsCondition, conditions);

        Table table = buildRegisters("select distinct "+ optionsColumn+ " from alumnosView " + conditionToSend);        

        return table.getColumn(0);
    }
    
    public ArrayList<String> getRelatedTables(){
        String query ="" +
                "SELECT\n" +
                "    TABLE_NAME\n" +
                "FROM\n" +
                "    INFORMATION_SCHEMA.KEY_COLUMN_USAGE\n" +
                "WHERE\n" +
                "     REFERENCED_TABLE_NAME = '" + table + "'";
               
        
        Table relatedTableInfo = bringTable(query);

        
        return relatedTableInfo.getColumn(0);
    }



}