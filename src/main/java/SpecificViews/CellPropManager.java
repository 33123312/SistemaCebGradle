package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.DataBaseUpdater;
import JDBCController.Table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CellPropManager {
    public  String titleValue;
    public  String rowValue;

    private BuilderBackend2 back;

    private ArrayList<String> insertiveCols;
    private ArrayList<String> insertiveValues;


    public CellPropManager(String titleCol,String rowCol,BuilderBackend2 back){
        titleValue = titleCol;
        rowValue = rowCol;
        this.back = back;
        insertiveCols = new ArrayList<>();
        insertiveCols.add(back.insertCol);
        insertiveValues = new ArrayList<>();

    }

    public  void insertTitle(){
        insertiveCols.add(back.titleCol);
        insertiveValues.add(titleValue);

    }

    public  void insertRow(){
        insertiveCols.add(back.rowCol);
        insertiveValues.add(rowValue);
    }


    private ArrayList<String> getInserccVal(String val){
        insertiveValues.add(0,val);
        return insertiveValues;
    }

    private ArrayList<String> getFullConditions(){
        ArrayList<String> conditions = new ArrayList<>(back.conditions);
        conditions.add(back.titleCol);
        conditions.add(back.rowCol);

        return conditions;
    }

    private ArrayList<String> getFullValues(){
        ArrayList<String> values = new ArrayList<>(back.values);
        values.add(titleValue);
        values.add(rowValue);

        return values;
    }

    public void setEmpty(){
        Table response = getRegister();
        if(!response.isEmpty()){
            update(response.getRegister(0).getValues(),"NULL");
        }

    }

    public void insertValues(String insertValue) {
        Table response = getRegister();
        if(response.isEmpty()){
            ArrayList<String> insertVal = getInserccVal(getKey());
            try {
                new DataBaseUpdater(back.insertTable).insert(insertiveCols,insertVal);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            update(insertVal,insertValue);
        } else{
            update(response.getRegister(0).getValues(),insertValue);
        }
    }

    private String getKey() {
        String key = searchForKey();
        if(key == null)
            createKey();
        return searchForKey();
    }

    private void createKey(){
        ArrayList<String> cols = new ArrayList<>();
            cols.add(back.rowCol);
            cols.addAll(back.conditions);

        ArrayList<String> val = new ArrayList<>();
            val.add(rowValue);
            val.addAll(back.values);


        try {
            new DataBaseUpdater("calificaciones").insert(cols,val);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String searchForKey(){
        DataBaseConsulter cons = new DataBaseConsulter(back.parentTable);

        String[] colsToBring = new String[]{back.insertCol};

        List<String> cols = new ArrayList<>();
            cols.add(back.rowCol);
            cols.addAll(back.conditions);

        List<String> val = new ArrayList<>();
            val.add(rowValue);
            val.addAll(back.values);
        return cons.bringTable(colsToBring, cols.toArray(new String[cols.size()]), val.toArray(new String[val.size()])).getUniqueValue();
    }
    private void update(ArrayList<String> currentValues,String newValue){
        ArrayList<String> insertColA = new ArrayList<>();
            insertColA.add(back.keyCol);

        ArrayList<String> insertValA= new ArrayList<>();
            insertValA.add(newValue);

        try {
            new DataBaseUpdater(back.insertTable).update(insertColA,insertValA,insertiveCols,currentValues);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Table getRegister() {

        DataBaseConsulter cons = new DataBaseConsulter(back.consultTable);
        List<String> bring = insertiveCols;
        List<String> cond = getFullConditions();
        List<String> val = getFullValues();

        return cons.bringTable(bring.toArray(
                new String[bring.size()]),
                cond.toArray(new String[cond.size()]),
                val.toArray(new String[val.size()]));
    }




}
