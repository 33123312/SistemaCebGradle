package SpecificViews;

import JDBCController.*;
import sistemaceb.RegisterDetailView;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.genericEvents;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CellManager {


    public  String insertTable;
    public  String consultTable;

    public  String titleValue;
    public  String rowValue;

    private ArrayList<String> insertiveCols;
    private ArrayList<String> insertiveValues;
    private BuilderBackend backend;


    public CellManager(String titleCol,String rowCol,BuilderBackend backend){
        this.backend = backend;
        titleValue = titleCol;
        rowValue = rowCol;
        this.consultTable= backend.colsultTable;
        insertTable = backend.objTable;
        insertiveCols = new ArrayList<>();
            insertiveCols.add(backend.keyCol);
        insertiveValues = new ArrayList<>();
    }

    public  void insertTitle(){
        insertiveCols.add(backend.titleCol);
        insertiveValues.add(titleValue);

    }

    public  void insertRow(){
        insertiveCols.add(backend.rowCol);
        insertiveValues.add(rowValue);
    }


    private ArrayList<String> getInserccVal(String val){
            insertiveValues.add(0,val);

        return insertiveValues;
    }

    private ArrayList<String> getFullConditions(){
        ArrayList<String> conditions = new ArrayList<>(backend.getConditions());
            conditions.add(backend.titleCol);
            conditions.add(backend.rowCol);

        return conditions;
    }

    private ArrayList<String> getFullValues(){
        ArrayList<String> values = new ArrayList<>(backend.getValues());
            values.add(titleValue);
            values.add(rowValue);

        return values;

    }

    public void setEmpty(){
        deleteIfExists();
    }

    private boolean deleteIfExists(){
        Table response = getRegister();
        if(!response.isEmpty()){
            TableRegister res = response.getRegister(0);
            deleteInert(res.getValues());
            return true;
        }
        return false;
    }

    public void insertValues(String insertValue){
        ArrayList<String> insertVal = getInserccVal(insertValue);
            insertVal.addAll(backend.getValues());
            insertiveCols.addAll(backend.getConditions());
            deleteIfExists();
            try {
                new ViewSpecs(insertTable).getUpdater().insert(insertiveCols, insertVal);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        
    }

    private Table getRegister(){
        DataBaseConsulter cons = new DataBaseConsulter(consultTable);

        List<String> bring =insertiveCols;
        List<String> cond = getFullConditions();
        List<String> val = getFullValues();

        return cons.bringTable(
                bring.toArray(new String[bring.size()]),
                cond.toArray(new String[cond.size()]),
                val.toArray(new String[val.size()]));
    }

    private void delete(ArrayList<String> cols,ArrayList<String> values){
        try {
            new DataBaseUpdater(insertTable).delete(cols,values);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteInert(ArrayList<String> values){
        delete(insertiveCols,values);
    }
}
